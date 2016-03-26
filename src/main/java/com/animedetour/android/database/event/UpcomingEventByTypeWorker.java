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
import java.util.ArrayList;
import java.util.List;

/**
 * Looks up a single event of a specified type after synchronizing with
 * the remote API.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class UpcomingEventByTypeWorker extends SyncEventsWorker
{
    final private Dao<Event, String> localAccess;
    final private String criteria;

    public UpcomingEventByTypeWorker(
        Dao<Event, String> localAccess,
        Dao<MetaData, Integer> metaData,
        ScheduleEndpoint remoteAccess,
        Transformer<ApiEvent, Event> eventTransformer,
        Monolog logger,
        String type
    ) {
        super(localAccess, metaData, remoteAccess, eventTransformer, logger);

        this.localAccess = localAccess;
        this.criteria = type;
    }

    /**
     * Get an upcoming event by type and position.
     *
     * Searches for events of a specified type, orders them by their
     * start time excluding events that have already started, and returns a
     * single event of the specified position.
     *
     * @return The upcoming event
     */
    @Override
    public List<Event> lookupLocal() throws SQLException
    {
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        Where<Event, String> where = builder.where();
        where.eq("category", this.criteria);
        where.and();
        where.gt("start", new DateTime());
        builder.orderBy("start", true);
        PreparedQuery<Event> prepared = builder.prepare();

        Event result = this.localAccess.queryForFirst(prepared);
        List<Event> resultSet = new ArrayList<>();
        resultSet.add(result);

        return resultSet;
    }
}
