/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database;

import com.animedetour.api.guest.GuestEndpoint;
import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.prism.SubscriptionManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.logging.Log;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class GuestRepository
{
    /** Used for tracing. */
    final private Log logger;

    final private ConnectionSource connectionSource;

    final private Dao<Category, Integer> localCategoryAccess;
    final private Dao<Guest, String> localGuestAccess;

    final private GuestEndpoint remoteAccess;

    /** Manage in-flight requests to async repos. */
    final private SubscriptionManager<Category> subscriptionManager;

    public GuestRepository(
        ConnectionSource connectionSource,
        Dao<Category, Integer> localCategoryAccess,
        Dao<Guest, String> localGuestAccess,
        GuestEndpoint remoteAccess,
        SubscriptionManager<Category> subscriptionManager,
        Log logger
    ) {
        this.connectionSource = connectionSource;
        this.localCategoryAccess = localCategoryAccess;
        this.localGuestAccess = localGuestAccess;
        this.remoteAccess = remoteAccess;
        this.logger = logger;
        this.subscriptionManager = subscriptionManager;
    }

    public Subscription findAllCategories(Observer<List<Category>> observer)
    {
        OnSubscribe<List<Category>> onSubscribe = new OnSubscribe<List<Category>>() {
            @Override public void call(final Subscriber<? super List<Category>> subscriber) {
                GuestRepository.this.loadAllCategories(subscriber);
            }
        };
        String key = "findAllCategories";

        return this.subscriptionManager.createCollectionSubscription(onSubscribe, observer, key);
    }

    public void loadAllCategories(final Subscriber<? super List<Category>> subscriber)
    {
        try {
            List<Category> currentCategories = this.getAllCategories();
            subscriber.onNext(currentCategories);

            List<Category> categories = this.remoteAccess.getAll();
            this.saveLocal(categories);

            List<Category> newCategories = this.getAllCategories();
            subscriber.onNext(newCategories);
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }

    public List<Category> getAllCategories() throws SQLException
    {
        QueryBuilder<Category, Integer> builder = this.localCategoryAccess.queryBuilder();

        PreparedQuery<Category> query = builder.prepare();
        List<Category> result = this.localCategoryAccess.query(query);

        return result;
    }

    public void saveLocal(List<Category> categories) throws SQLException
    {
        this.logger.debug("Dropping tables");
        TableUtils.dropTable(this.connectionSource, Guest.class, true);
        TableUtils.createTable(this.connectionSource, Guest.class);
        TableUtils.dropTable(this.connectionSource, Category.class, true);
        TableUtils.createTable(this.connectionSource, Category.class);

        this.logger.trace("Saving " + categories.size() + " Categories");
        for (Category category : categories) {
            this.saveLocal(category);
        }
    }

    public void saveLocal(Category category) throws SQLException
    {
        this.localCategoryAccess.create(category);

        this.logger.trace("Saving " + category.getGuests().size() + " Guests");
        for (Guest guest : category.getGuests()) {
            this.localGuestAccess.create(guest);
        }
    }
}
