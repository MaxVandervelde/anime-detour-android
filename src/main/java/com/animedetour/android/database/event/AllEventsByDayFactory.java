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
import com.inkapplications.groundcontrol.CriteriaWorkerFactory;
import com.inkapplications.groundcontrol.Worker;
import com.j256.ormlite.dao.Dao;
import org.javatuples.Pair;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Creates new workers to lookup events by day so that we can pass criteria to it.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class AllEventsByDayFactory implements CriteriaWorkerFactory<List<Event>, DateTime>
{
    final private Dao<Event, String> localAccess;
    final private ScheduleEndpoint remoteAccess;
    final private Transformer<Pair<ApiEvent, DateTime>, Event> eventTransformer;

    public AllEventsByDayFactory(
        Dao<Event, String> localAccess,
        ScheduleEndpoint remoteAccess,
        Transformer<Pair<ApiEvent, DateTime>, Event> eventTransformer
    ) {
        this.localAccess = localAccess;
        this.remoteAccess = remoteAccess;
        this.eventTransformer = eventTransformer;
    }

    public Worker<List<Event>> createWorker(DateTime criteria)
    {
        return new AllEventsByDayWorker(
            this.localAccess,
            this.remoteAccess,
            this.eventTransformer,
            criteria
        );
    }
}
