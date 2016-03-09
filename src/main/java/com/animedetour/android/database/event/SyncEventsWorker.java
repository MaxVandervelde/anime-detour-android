/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event;

import com.animedetour.android.model.Event;
import com.animedetour.android.model.transformer.Transformer;
import com.animedetour.api.sched.ScheduleEndpoint;
import com.animedetour.api.sched.model.ApiEvent;
import com.inkapplications.PairMerge;
import com.inkapplications.groundcontrol.RemovableSyncWorker;
import com.j256.ormlite.dao.Dao;
import org.javatuples.Pair;
import org.joda.time.DateTime;

import java.sql.SQLException;
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

    /** A remote endpoint for updating the local storage. */
    final private ScheduleEndpoint remoteAccess;

    final private Transformer<Pair<ApiEvent, DateTime>, Event> eventTransformer;

    /**
     * @param localAccess A local DAO for storing events.
     * @param remoteAccess A remote endpoint for updating the local storage.
     * @param eventTransformer
     */
    public SyncEventsWorker(
        Dao<Event, String> localAccess,
        ScheduleEndpoint remoteAccess,
        Transformer<Pair<ApiEvent, DateTime>, Event> eventTransformer
    ) {
        this.localAccess = localAccess;
        this.remoteAccess = remoteAccess;
        this.eventTransformer = eventTransformer;
    }

    @Override
    public List<Event> lookupRemote() throws SQLException
    {
        List<ApiEvent> events = this.remoteAccess.getSchedule();
        List<Pair<ApiEvent, DateTime>> fetchedEvents = PairMerge.mergeRight(events, new DateTime());


        return this.eventTransformer.bulkTransform(fetchedEvents);
    }

    @Override
    public void saveLocal(List<Event> events) throws SQLException
    {
        for (Event event : events) {
            this.localAccess.createOrUpdate(event);
        }
    }

    @Override
    public void removeLocal(List<Event> events) throws SQLException
    {
        for (Event event : events) {
            this.localAccess.deleteById(event.getId());
        }
    }

    /**
     * @todo The endpoint to check for deleted events was removed, if added back, implement here.
     */
    @Override
    public List<Event> lookupRemovedRemote() throws Exception
    {
        return Collections.emptyList();
    }

    @Override
    public boolean dataIsStale() throws SQLException
    {
        return true;
    }
}
