/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.model.transformer;

import com.animedetour.android.model.Event;
import com.animedetour.api.sched.model.ApiEvent;
import org.javatuples.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapts API Events to local models..
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
@Singleton
public class ApiEventTransformer implements Transformer<Pair<ApiEvent, DateTime>, Event>
{
    @Inject
    public ApiEventTransformer() {}

    @Override
    public Event transform(Pair<ApiEvent, DateTime> data)
    {
        ApiEvent apiEvent = data.getValue0();
        DateTime fetched = data.getValue1();
        return new Event(
            apiEvent.id,
            apiEvent.name,
            new DateTime(apiEvent.start),
            new DateTime(apiEvent.end),
            apiEvent.category,
            apiEvent.tags,
            apiEvent.room,
            apiEvent.hosts,
            apiEvent.description,
            apiEvent.banner,
            fetched
        );
    }

    @Override
    public Pair<ApiEvent, DateTime> reverseTransform(Event data)
    {
        return new Pair<>(
            new ApiEvent(
                data.getId(),
                data.getName(),
                ISODateTimeFormat.dateTimeNoMillis().print(data.getStart()),
                ISODateTimeFormat.dateTimeNoMillis().print(data.getEnd()),
                data.getCategory(),
                data.getTags(),
                data.getRoom(),
                data.getHosts(),
                data.getDescription(),
                data.getBanner()
            ),
            data.getFetched()
        );
    }

    @Override
    public List<Event> bulkTransform(List<Pair<ApiEvent, DateTime>> fromEvents)
    {
        ArrayList<Event> toEvents = new ArrayList<>(fromEvents.size());

        for (Pair<ApiEvent, DateTime> fromEvent : fromEvents) {
            toEvents.add(this.transform(fromEvent));
        }

        return toEvents;
    }

    @Override
    public List<Pair<ApiEvent, DateTime>> bulkReverseTransform(List<Event> fromEvents)
    {
        ArrayList<Pair<ApiEvent, DateTime>> toEvents = new ArrayList<>(fromEvents.size());

        for (Event fromEvent : fromEvents) {
            toEvents.add(this.reverseTransform(fromEvent));
        }

        return toEvents;
    }
}
