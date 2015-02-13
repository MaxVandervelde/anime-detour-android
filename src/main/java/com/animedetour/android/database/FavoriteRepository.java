/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.database;

import com.animedetour.android.schedule.Favorite;
import com.animedetour.api.sched.api.model.Event;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides an API for querying for Events that the user has marked as favorited.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class FavoriteRepository
{
    /** Local favorite storage. */
    final private Dao<Favorite, Integer> localAccess;

    /**
     * @param localAccess Local favorite storage.
     */
    public FavoriteRepository(Dao<Favorite, Integer> localAccess)
    {
        this.localAccess = localAccess;
    }

    /**
     * Asynchronously find all of the Events that were favorited by the user.
     */
    public Subscription findAll(Observer<List<Favorite>> observer)
    {
        Observable<List<Favorite>> callback = Observable.create(new Observable.OnSubscribe<List<Favorite>>() {
            @Override public void call(Subscriber<? super List<Favorite>> subscriber) {
                try {
                    List<Favorite> favorites = FavoriteRepository.this.getAll();
                    subscriber.onNext(favorites);
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
        callback = callback.subscribeOn(Schedulers.io());
        callback = callback.observeOn(AndroidSchedulers.mainThread());
        callback = callback.cache();
        Subscription subscription = callback.subscribe(observer);

        return subscription;
    }

    /**
     * Look up if a specified event has been marked as a favorite by the user.
     *
     * @param event The event to find a favorite for.
     * @return Whether or not the user has favorited the event specified.
     */
    public boolean isFavorited(Event event) throws SQLException
    {
        QueryBuilder<Favorite, Integer> builder = this.localAccess.queryBuilder();
        builder.where().eq("event_id", event);
        long favorites = builder.countOf();

        return favorites > 0;
    }

    /**
     * @return All of the events that the user has marked as a favorite.
     */
    public List<Favorite> getAll() throws SQLException
    {
        QueryBuilder<Favorite, Integer> builder = this.localAccess.queryBuilder();

        PreparedQuery<Favorite> query = builder.prepare();
        List<Favorite> result = this.localAccess.query(query);

        return result;
    }

    /**
     * Remove an event that was previously favorited by the user.
     *
     * @param favorite The event to "un-favorite"
     */
    public void remove(Event favorite) throws SQLException
    {
        DeleteBuilder<Favorite, Integer> builder = this.localAccess.deleteBuilder();
        builder.where().eq("event_id", favorite);
        PreparedDelete<Favorite> deleteQuery = builder.prepare();

        this.localAccess.delete(deleteQuery);
    }

    /**
     * Create a new Favorite record.
     *
     * @param event The event being marked as a favorite.
     */
    public void create(Event event) throws SQLException
    {
        Favorite favorite = new Favorite();
        favorite.setEvent(event);

        this.save(favorite);
    }

    /**
     * Create or update a Favorite entity.
     *
     * If there is no matching ID for the favorite, or if the ID is null, this
     * will create a new record. Otherwise, it will update the matching record.
     *
     * @param favorite The entity to be saved to the database.
     */
    public void save(Favorite favorite) throws SQLException
    {
        this.localAccess.createOrUpdate(favorite);
    }
}
