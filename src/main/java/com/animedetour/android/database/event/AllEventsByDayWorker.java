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
import com.animedetour.android.model.transformer.Transformer;
import com.animedetour.api.sched.ScheduleEndpoint;
import com.animedetour.api.sched.model.ApiEvent;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import org.javatuples.Pair;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;

/**
 * Looks up a list of all locally stored events by a specified date after
 * syncing with the API.
 *
 * Events will be any that *start* or continue through the specified date, and
 * will be ordered by their start time.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class AllEventsByDayWorker extends SyncEventsWorker
{
    final private Dao<Event, String> localAccess;
    final private Pair<DateTime, Boolean> criteria;

    public AllEventsByDayWorker(
        Dao<Event, String> localAccess,
        ScheduleEndpoint remoteAccess,
        Transformer<Pair<ApiEvent, DateTime>, Event> eventTransformer,
        Pair<DateTime, Boolean> criteria
    ) {
        super(localAccess, remoteAccess, eventTransformer);

        this.localAccess = localAccess;
        this.criteria = criteria;
    }

    /**
     * Look up local events for a single day.
     *
     * If we're including past events, search for events that:
     *  - Start during the specified day
     *  - Starts before the day and ends after the start of the day.
     *    (Note: This isn't ends on the day intentionally in order to account
     *    for events that go all the way through the day)
     *
     * If we're not including past events, search for events that:
     *  - have started today, but end sometime after the current time
     *  - events that are between now and the end of the day
     *  - events that started before today, and end after today
     */
    @Override
    public List<Event> lookupLocal() throws SQLException
    {
        DateTime dayCriteria = this.criteria.getValue0();
        Boolean includePast = this.criteria.getValue1();
        DateTime now = new DateTime();

        DateTime start = dayCriteria.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        DateTime end = dayCriteria.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        QueryBuilder<Event, String> builder = this.localAccess.queryBuilder();
        builder.orderBy("start", true);
        builder.orderBy("name", true);
        if (false == includePast) {
            Where<Event, String> where = builder.where();
            Where<Event, String> today = where.between("start", start, end);
            Where<Event, String> endingLater = where.gt("end", now);
            Where<Event, String> startedNotFinished = where.and(today, endingLater);
            Where<Event, String> afterNow = where.between("start", now, end);
            Where<Event, String> startsBefore = where.lt("start", start);
            Where<Event, String> endsAfter = where.gt("end", end);
            Where<Event, String> notOver = where.gt("end", now);
            Where<Event, String> allDay = where.and(startsBefore, endsAfter, notOver);
            where.or(startedNotFinished, afterNow, allDay);
        } else {
            Where<Event, String> where = builder.where();
            Where<Event, String> duringToday = where.between("start", start, end);
            Where<Event, String> startsBefore = where.lt("start", start);
            Where<Event, String> afterStart = where.gt("end", start);
            Where<Event, String> throughToday = where.and(startsBefore, afterStart);
            where.or(duringToday, throughToday);
        }

        PreparedQuery<Event> query = builder.prepare();
        List<Event> result = this.localAccess.query(query);

        return result;
    }
}
