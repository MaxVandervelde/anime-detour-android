/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.guest;

import com.animedetour.api.guest.GuestEndpoint;
import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.groundcontrol.SyncWorker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Looks up all guests and returns them grouped by category.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class AllCategoriesWorker extends SyncWorker<List<Category>>
{
    final private ConnectionSource connectionSource;
    final private Dao<Category, Integer> localCategoryAccess;
    final private Dao<Guest, String> localGuestAccess;
    final private GuestEndpoint remoteAccess;

    public AllCategoriesWorker(
        ConnectionSource connectionSource,
        Dao<Category, Integer> localCategoryAccess,
        Dao<Guest, String> localGuestAccess,
        GuestEndpoint remoteAccess
    ) {
        this.connectionSource = connectionSource;
        this.localCategoryAccess = localCategoryAccess;
        this.localGuestAccess = localGuestAccess;
        this.remoteAccess = remoteAccess;
    }

    @Override
    public List<Category> lookupRemote() throws Exception
    {
        return this.remoteAccess.getAll();
    }

    @Override
    public List<Category> lookupLocal() throws SQLException
    {
        QueryBuilder<Category, Integer> builder = this.localCategoryAccess.queryBuilder();

        PreparedQuery<Category> query = builder.prepare();
        List<Category> result = this.localCategoryAccess.query(query);

        return result;
    }

    @Override
    public boolean dataIsStale() throws SQLException
    {
        return true;
    }

    @Override
    public void saveLocal(List<Category> categories) throws SQLException
    {
        TableUtils.dropTable(this.connectionSource, Guest.class, true);
        TableUtils.createTable(this.connectionSource, Guest.class);
        TableUtils.dropTable(this.connectionSource, Category.class, true);
        TableUtils.createTable(this.connectionSource, Category.class);

        for (Category category : categories) {
            this.saveLocal(category);
        }
    }

    private void saveLocal(Category category) throws SQLException
    {
        this.localCategoryAccess.create(category);

        for (Guest guest : category.getGuests()) {
            this.localGuestAccess.create(guest);
        }
    }
}
