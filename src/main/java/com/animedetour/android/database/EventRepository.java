/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.database;

import com.inkapplications.prism.SubscriptionManager;
import com.animedetour.api.sched.api.ScheduleEndpoint;
import com.animedetour.api.sched.api.model.Event;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.logging.Log;
import org.joda.time.DateTime;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides an API for querying Event objects.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventRepository
{
    /** Used for tracing. */
    final private Log logger;

    /** A local DAO for storing events. */
    final private Dao<Event, String> localAccess;

    /** A remote endpoint for updating the local storage. */
    final private ScheduleEndpoint remoteAccess;

    /** Manage in-flight requests to async repos. */
    final private SubscriptionManager<Event> subscriptionManager;

    /**
     * @param localAccess A local DAO for storing events
     * @param remoteAccess A remote endpoint for updating the local storage
     */
    public EventRepository(
        Dao<Event, String> localAccess,
        ScheduleEndpoint remoteAccess,
        SubscriptionManager<Event> subscriptionManager,
        Log logger
    ) {
        this.localAccess = localAccess;
        this.remoteAccess = remoteAccess;
        this.logger = logger;
        this.subscriptionManager = subscriptionManager;
    }

    /**
     * Find All Events.
     *
     * @return an observable that will update with events data
     */
    public Subscription findAll(Observer<List<Event>> observer)
    {
        OnSubscribe<List<Event>> onSubscribe = new OnSubscribe<List<Event>>() {
            @Override public void call(final Subscriber<? super List<Event>> subscriber) {
                EventRepository.this.loadAll(subscriber);
            }
        };
        String key = "findAll";

        return this.subscriptionManager.createCollectionSubscription(
            onSubscribe,
            observer,
            key
        );
    }

    /**
     * Find All events for a specified day.
     *
     * This is run by the START time of the event
     *
     * @param day The day to lookup events for
     * @return an observable that will update with events data
     */
    public Subscription findAllOnDay(final DateTime day, Observer<List<Event>> observer)
    {
        OnSubscribe<List<Event>> onSubscribe = new OnSubscribe<List<Event>>() {
            @Override public void call(final Subscriber<? super List<Event>> subscriber) {
                EventRepository.this.loadAllOnDay(subscriber, day);
            }
        };
        String key = "findAllOnDay:" + day.getDayOfYear();

        return this.subscriptionManager.createCollectionSubscription(
            onSubscribe,
            observer,
            key
        );
    }

    /**
     * Find all of the "featured" events to suggest to the user.
     */
    public Subscription findFeatured(Observer<Event> observer, long ordinal)
    {
        return this.findUpcomingByTag(
            "detour sponsored event",
            observer,
            ordinal
        );
    }

    /**
     * Finds all of the events containing a specific tag.
     *
     * @param tag The tag to search for events containing.
     */
    public Subscription findUpcomingByTag(final String tag, Observer<Event> observer, final long ordinal)
    {
        OnSubscribe<Event> onSubscribe = new OnSubscribe<Event>() {
            @Override public void call(final Subscriber<? super Event> subscriber) {
                EventRepository.this.loadUpcomingByTag(subscriber, tag, ordinal);
            }
        };

        String key = "findUpcomingByTag:" + tag + ":" + ordinal;
        return this.subscriptionManager.createSubscription(
            onSubscribe,
            observer,
            key
        );
    }

    /**
     * Get All local events.
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
     * Get All Events on a specific day.
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
     * Get a specific event by its ID.
     */
    public Event get(String id) throws SQLException
    {
        return this.localAccess.queryForId(id);
    }

    /**
     * Fetches the most recently updated event according to fetch time.
     */
    public Event getMostRecentUpdated() throws SQLException
    {
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        builder.orderBy("fetched", false);


        PreparedQuery<Event> query = builder.prepare();
        Event result = this.localAccess.queryForFirst(query);

        return result;
    }

    /**
     * Drops and re-creates the local table data
     */
    public void dropLocal() throws SQLException
    {
        this.logger.trace("Dropping Local access");
        TableUtils.dropTable(this.localAccess.getConnectionSource(), Event.class, true);
        TableUtils.createTable(this.localAccess.getConnectionSource(), Event.class);
    }

    /**
     * Save a list of events into the local data store.
     */
    public void saveLocal(List<Event> events) throws SQLException
    {
        this.logger.trace("Saving " + events.size() + " Events");
        for (Event event : events) {
            this.saveLocal(event);
        }
    }

    /**
     * Save a Single event into the local data store.
     */
    public void saveLocal(Event event) throws SQLException
    {
        this.localAccess.createOrUpdate(event);
    }

    /**
     * Updates the local data store.
     *
     * Updates a subscriber with changes in local data.
     * Will return existing data, then attempt to fetch data from the remote
     * and update the subscriber again.
     *
     * @param subscriber For listening to the event list updates
     */
    protected void loadAll(final Subscriber<? super List<Event>> subscriber)
    {
        try {
            List<Event> currentEvents = this.getAll();
            subscriber.onNext(currentEvents);

            Event mostRecent = this.getMostRecentUpdated();

            if (false == this.dataIsStale()) {
                return;
            }

            List<Event> events = this.remoteAccess.getSchedule(mostRecent.getFetched().getMillis());
            this.saveLocal(events);

            List<Event> newEvents = this.getAll();
            subscriber.onNext(newEvents);
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }

    /**
     * Updates the local data store for a specific day.
     *
     * @see #loadAll(rx.Subscriber)
     * @param subscriber For listening to the event list updates
     * @param day The day to lookup events for
     */
    protected void loadAllOnDay(final Subscriber<? super List<Event>> subscriber, final DateTime day)
    {
        try {
            List<Event> currentEvents = this.getAllOnDay(day);
            subscriber.onNext(currentEvents);

            if (false == this.dataIsStale()) {
                this.logger.debug("Most recent event within 10 minutes, not checking for update.");
                return;
            }

            this.logger.trace("Checking for update.");
            this.updateAll(new Observer<List<Event>>() {
                @Override public void onCompleted() {
                    try {
                        logger.trace("Update Complete");
                        List<Event> newEvents = EventRepository.this.getAllOnDay(day);
                        subscriber.onNext(newEvents);
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                }
                @Override public void onError(Throwable e) {
                    subscriber.onError(e);
                }
                @Override public void onNext(List<Event> events) { }
            });
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }

    public void loadUpcomingByTag(
        final Subscriber<? super Event> subscriber,
        final String tag,
        final long ordinal
    ) {
        try {
            Event currentEvents = this.getUpcomingByTag(tag, ordinal);
            subscriber.onNext(currentEvents);


            if (false == this.dataIsStale()) {
                this.logger.debug("Most recent event within 10 minutes, not checking for update.");
                return;
            }

            this.logger.trace("Checking for update.");
            this.updateAll(new Observer<List<Event>>(){
                @Override public void onCompleted() {
                    try {
                        logger.trace("Update Complete");
                        Event newEvents = EventRepository.this.getUpcomingByTag(tag, ordinal);
                        subscriber.onNext(newEvents);
                    } catch (SQLException e) {
                        subscriber.onError(e);
                    }
                }
                @Override public void onError(Throwable e) {
                    subscriber.onError(e);
                }
                @Override public void onNext(List<Event> events) { }
            });
        } catch (SQLException e) {
            subscriber.onError(e);
        }
    }

    /**
     * Get an upcoming event by tag and position.
     *
     * Searches for events containing the specified tag, orders them by their
     * start time excluding events that have already started, and returns a
     * single event of the specified position.
     *
     * @todo Re-Enable the start exclusion when we have future events in the database.
     * @param tag The tag to search for events containing.
     * @param ordinal The event's position in the upcoming list.
     * @return The upcoming event
     */
    public Event getUpcomingByTag(String tag, long ordinal) throws SQLException
    {
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        Where<Event, String> where = builder.where();
        where.like("tags", "%" + tag + "%");
//        where.and();
//        where.gt("start", new DateTime());
        builder.orderBy("start", true);
        builder.offset(ordinal - 1);
        builder.limit(1L);
        PreparedQuery<Event> prepared = builder.prepare();

        Event result = this.localAccess.queryForFirst(prepared);

        return result;
    }

    /**
     * Checks if the most recent record is before a threshold.
     *
     * Current threshold is set to an hour.
     *
     * @return true the data is considered too old.
     */
    final protected boolean dataIsStale() throws SQLException
    {
        Event mostRecent = this.getMostRecentUpdated();

        if (null == mostRecent) {
            return true;
        }

        DateTime earliest = new DateTime().minusHours(1);
        if (mostRecent.getFetched().isBefore(earliest)) {
            return true;
        }

        return false;
    }

    /**
     * Update all event records to be in sync with the API.
     *
     * This method will use the subscription manager to ensure that only one
     * update request is run at once.
     *
     * @param observer The observer to invoke on event list updates.
     * @return A subscription for the request that may be cancelled.
     */
    protected Subscription updateAll(Observer<List<Event>> observer)
    {
        OnSubscribe<List<Event>> onSubscribe = new OnSubscribe<List<Event>>() {
            @Override public void call(final Subscriber<? super List<Event>> subscriber) {
                EventRepository.this.updateAll(subscriber);
            }
        };
        String key = "updateAll";

        return this.subscriptionManager.createCollectionSubscription(
            onSubscribe,
            observer,
            key
        );
    }

    /**
     * Update all event records to be in sync with the API.
     *
     * This method will run the sql update and inform a subscriber of progress.
     * will check for updates since the most recently fetched record.
     *
     * @param subscriber The subscriber to keep informed on updates.
     */
    protected void updateAll(final Subscriber<? super List<Event>> subscriber)
    {
        this.logger.trace("Updating all");
        try {
            Event mostRecent = this.getMostRecentUpdated();
            long since;
            if (null == mostRecent) {
                since = 0;
            } else {
                since = mostRecent.getFetched().getMillis();
            }
            List<Event> events = this.updateAll(since);
            subscriber.onNext(events);
        } catch (SQLException e) {
            subscriber.onError(e);
        }

        subscriber.onCompleted();
    }

    /**
     * Update all event records to be in sync with the API.
     *
     * This method makes a direct call to the remote and saves the updates
     * to the local database.
     *
     * @todo this does not check for removed events currently.
     * @param since The timestamp to find changes since.
     * @return The list of updated events.
     */
    protected List<Event> updateAll(long since) throws SQLException
    {
        this.logger.trace("Updating all from remote");
        List<Event> schedule = this.remoteAccess.getSchedule(since);
        this.saveLocal(schedule);
        return schedule;
    }
}
