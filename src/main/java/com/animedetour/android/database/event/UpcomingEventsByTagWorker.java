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
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import monolog.Monolog;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;

/**
 * Looks events by a specified tag criteria after synchronizing with
 * the remote API.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class UpcomingEventsByTagWorker extends SyncEventsWorker
{
    final private Dao<Event, String> localAccess;
    final private String criteria;

    public UpcomingEventsByTagWorker(
        Dao<Event, String> localAccess,
        Dao<MetaData, Integer> metaData,
        ScheduleEndpoint remoteAccess,
        Transformer<ApiEvent, Event> transformer,
        Monolog logger,
        String tag
    ) {
        super(localAccess, metaData, remoteAccess, transformer, logger);
        this.localAccess = localAccess;
        this.criteria = tag;
    }

    /**
     * Get an upcoming event by tag and position.
     *
     * Searches for events containing the specified tag, orders them by their
     * start time excluding events that have already started.
     *
     * @return The upcoming event
     */
    @Override
    public List<Event> lookupLocal() throws SQLException
    {
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        Where<Event, String> where = builder.where();
        where.like("tags", "%" + this.criteria + "%");
        where.and();
        where.gt("start", new DateTime());
        builder.orderBy("start", true);
        PreparedQuery<Event> prepared = builder.prepare();

        List<Event> result = this.localAccess.query(prepared);

        return result;
    }
}
