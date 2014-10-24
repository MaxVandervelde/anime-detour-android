/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.database.event;

import com.animedetour.sched.api.ScheduleEndpoint;
import com.animedetour.sched.api.model.Event;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import org.joda.time.DateTime;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.sql.SQLException;
import java.util.List;

/**
 * Event Data Repository
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventRepository
{
    /**
     *  A local DAO for storing events
     */
    final private Dao<Event, String> localAccess;

    /**
     * A remote endpoint for updating the local storage
     */
    final private ScheduleEndpoint remoteAccess;

    /**
     * @param localAccess A local DAO for storing events
     * @param remoteAccess A remote endpoint for updating the local storage
     */
    public EventRepository(Dao<Event, String> localAccess, ScheduleEndpoint remoteAccess)
    {
        this.localAccess = localAccess;
        this.remoteAccess = remoteAccess;
    }

    /**
     * Find All Events
     *
     * @return an observable that will update with events data
     */
    public Observable<List<Event>> findAll()
    {
        Observable<List<Event>> callback = Observable.create(new OnSubscribe<List<Event>>() {
            @Override public void call(final Subscriber<? super List<Event>> subscriber) {
                EventRepository.this.updateAll(subscriber);
            }
        });
        callback = callback.subscribeOn(Schedulers.io());
        callback = callback.observeOn(AndroidSchedulers.mainThread());

        return callback;
    }

    /**
     * Find All events for a specified day
     *
     * This is run by the START time of the event
     *
     * @param day The day to lookup events for
     * @return an observable that will update with events data
     */
    public Observable<List<Event>> findAllOnDay(final DateTime day)
    {
        Observable<List<Event>> callback = Observable.create(new OnSubscribe<List<Event>>() {
            @Override public void call(final Subscriber<? super List<Event>> subscriber) {
                EventRepository.this.updateAllOnDay(subscriber, day);
            }
        });
        callback = callback.subscribeOn(Schedulers.io());
        callback = callback.observeOn(AndroidSchedulers.mainThread());

        return callback;
    }

    /**
     * Get All local events
     *
     * @return A list of every event in the local data store
     */
    public List<Event> getAll() throws SQLException
    {
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        builder.orderBy("start", true);

        PreparedQuery<Event> query = builder.prepare();
        List<Event> result = this.localAccess.query(query);

        return result;
    }

    /**
     * Updates the local data store for a specific day
     *
     * @see #updateAll(rx.Subscriber)
     * @param subscriber For listening to the event list updates
     * @param day The day to lookup events for
     */
    public void updateAllOnDay(final Subscriber<? super List<Event>> subscriber, DateTime day)
    {
        try {
            List<Event> currentEvents = this.getAllOnDay(day);
            subscriber.onNext(currentEvents);

            List<Event> events = this.remoteAccess.getSchedule();
            this.dropLocal();
            this.saveLocal(events);

            List<Event> newEvents = this.getAllOnDay(day);
            subscriber.onNext(newEvents);
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }

    /**
     * Get All Events on a specific day
     *
     * @return Events that occur on that day (by start time)
     */
    public List<Event> getAllOnDay(DateTime day) throws SQLException
    {
        DateTime start = day.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        DateTime end = day.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        builder.orderBy("start", true);
        builder.where().between("start", start, end);

        PreparedQuery<Event> query = builder.prepare();
        List<Event> result = this.localAccess.query(query);

        return result;
    }

    /**
     * Updates the local data store
     *
     * Updates a subscriber with changes in local data.
     * Will return existing data, then attempt to fetch data from the remote
     * and update the subscriber again.
     *
     * @param subscriber For listening to the event list updates
     */
    protected void updateAll(final Subscriber<? super List<Event>> subscriber)
    {
        try {
            List<Event> currentEvents = this.getAll();
            subscriber.onNext(currentEvents);

            List<Event> events = this.remoteAccess.getSchedule();
            this.dropLocal();
            this.saveLocal(events);

            List<Event> newEvents = this.getAll();
            subscriber.onNext(newEvents);
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }

    /**
     * Drops and re-creates the local table data
     */
    public void dropLocal() throws SQLException
    {
        TableUtils.dropTable(this.localAccess.getConnectionSource(), Event.class, true);
        TableUtils.createTable(this.localAccess.getConnectionSource(), Event.class);
    }

    /**
     * Save a list of events into the local data store
     */
    public void saveLocal(List<Event> events) throws SQLException
    {
        for (Event event : events) {
            this.saveLocal(event);
        }
    }

    /**
     * Save a Single event into the local data store
     */
    public void saveLocal(Event event) throws SQLException
    {
        this.localAccess.create(event);
    }
}
