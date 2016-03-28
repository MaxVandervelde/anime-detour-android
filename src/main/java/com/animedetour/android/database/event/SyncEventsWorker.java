/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015-2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event;

import com.animedetour.android.model.Event;
import com.animedetour.android.model.MetaData;
import com.animedetour.android.model.transformer.Transformer;
import com.animedetour.api.sched.ScheduleEndpoint;
import com.animedetour.api.sched.model.ApiEvent;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.inkapplications.groundcontrol.RemovableSyncWorker;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import monolog.Monolog;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controls Synchronizing between the local event database with the remote API.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
abstract public class SyncEventsWorker extends RemovableSyncWorker<List<Event>>
{
    /** A local DAO for storing events. */
    final private Dao<Event, String> localAccess;

    /** Local information about the event data. */
    final private Dao<MetaData, Integer> metaDataAccess;

    /** A remote endpoint for updating the local storage. */
    final private ScheduleEndpoint remoteAccess;

    /** Service for changing API events into local models. */
    final private Transformer<ApiEvent, Event> eventTransformer;

    /** Application logger for database errors. */
    final private Monolog logger;

    /**
     * @param localAccess A local DAO for storing events.
     * @param metaDataAccess Local information about the event data.
     * @param remoteAccess A remote endpoint for updating the local storage.
     * @param eventTransformer Service for changing API events into local models.
     * @param logger Application logger for database errors.
     */
    public SyncEventsWorker(
        Dao<Event, String> localAccess,
        Dao<MetaData, Integer> metaDataAccess,
        ScheduleEndpoint remoteAccess,
        Transformer<ApiEvent, Event> eventTransformer,
        Monolog logger
    ) {
        this.localAccess = localAccess;
        this.metaDataAccess = metaDataAccess;
        this.remoteAccess = remoteAccess;
        this.eventTransformer = eventTransformer;
        this.logger = logger;
    }

    @Override
    public List<Event> lookupRemote() throws SQLException
    {
        List<ApiEvent> events = this.remoteAccess.getSchedule();
        return this.eventTransformer.bulkTransform(events);
    }

    @Override
    public void saveLocal(final List<Event> events) throws SQLException
    {
        this.logger.info("Saving " + events.size() + " events");

        try {
            this.localAccess.callBatchTasks(new BatchEventSave(this.localAccess, events));
        } catch (Exception e) {
            this.logger.error("Failed saving events", e);
        }

        MetaData metaData = this.metaDataAccess.queryForId(MetaData.SINGLETON);
        metaData = null == metaData ? new MetaData() : metaData;
        metaData = metaData.withEventsFetched(new DateTime());
        this.metaDataAccess.createOrUpdate(metaData);
    }

    /**
     * Remove local events that are no longer needed.
     *
     * This is currently deleting any events that are not in the remote list
     * currently.
     *
     * @todo Clean this up, and make it more efficient.
     */
    @Override
    public void removeLocal(List<Event> deprecated) throws SQLException
    {
        List<Event> events = new ArrayList<>(this.lookupRemote());
        List<String> newIds = Lists.transform(events, new Function<Event, String>() {
            @Override public String apply(Event input) {
                return input.getId();
            }
        });

        DeleteBuilder<Event, String> builder = this.localAccess.deleteBuilder();
        builder.where().notIn("id", newIds);
        this.localAccess.delete(builder.prepare());
    }

    /**
     * Not used due to change in logic.
     *
     * @deprecated
     * @see #removeLocal
     */
    @Override
    @Deprecated
    public List<Event> lookupRemovedRemote() throws Exception
    {
        return Collections.emptyList();
    }

    @Override
    public boolean dataIsStale() throws SQLException
    {
        MetaData metaData = this.metaDataAccess.queryForId(MetaData.SINGLETON);

        if (null == metaData || null == metaData.getEventsFetched()) {
            return true;
        }

        DateTime cutoff = new DateTime().minusHours(1);
        if (metaData.getEventsFetched().isBefore(cutoff)) {
            return true;
        }

        return false;
    }
}
