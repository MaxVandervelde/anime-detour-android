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
public class ApiEventTransformer implements Transformer<ApiEvent, Event>
{
    @Inject
    public ApiEventTransformer() {}

    @Override
    public Event transform(ApiEvent data)
    {
        return new Event(
            data.id,
            data.name,
            new DateTime(data.start),
            new DateTime(data.end),
            data.category,
            data.tags,
            data.room,
            data.hosts,
            data.description,
            data.banner
        );
    }

    @Override
    public ApiEvent reverseTransform(Event data)
    {
        return new ApiEvent(
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
        );
    }

    @Override
    public List<Event> bulkTransform(List<ApiEvent> fromEvents)
    {
        ArrayList<Event> toEvents = new ArrayList<>(fromEvents.size());

        for (ApiEvent fromEvent : fromEvents) {
            toEvents.add(this.transform(fromEvent));
        }

        return toEvents;
    }

    @Override
    public List<ApiEvent> bulkReverseTransform(List<Event> fromEvents)
    {
        ArrayList<ApiEvent> toEvents = new ArrayList<>(fromEvents.size());

        for (Event fromEvent : fromEvents) {
            toEvents.add(this.reverseTransform(fromEvent));
        }

        return toEvents;
    }
}
