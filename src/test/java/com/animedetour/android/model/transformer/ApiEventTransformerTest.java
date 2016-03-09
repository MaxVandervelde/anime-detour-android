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
import com.inkapplications.PairMerge;
import org.javatuples.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ApiEventTransformerTest
{
    private final ApiEventTransformer transformer = new ApiEventTransformer();

    /**
     * Ensure that data is transformed into the local model in-tact.
     */
    @Test
    public void testTransformTo()
    {
        ApiEvent before = new ApiEvent(
            "id",
            "name",
            "2016-04-21T16:15:14-06:00",
            "2017-05-23T17:16:15+02:00",
            "category",
            Arrays.asList("tag", "tag2"),
            "room",
            Arrays.asList("host 1", "host 2"),
            "description",
            "banner"
        );
        Pair<ApiEvent, DateTime> beforeFetched = new Pair<>(before, new DateTime("2018-06-24T18:17:16-06:00"));

        Event after = this.transformer.transform(beforeFetched);

        assertEquals("id", after.getId());
        assertEquals("name", after.getName());

        DateTime start = after.getStart().withZone(DateTimeZone.UTC);
        assertEquals(2016, start.getYear());
        assertEquals(4, start.getMonthOfYear());
        assertEquals(21, start.getDayOfMonth());
        assertEquals(16 + 6, start.getHourOfDay());
        assertEquals(15, start.getMinuteOfHour());
        assertEquals(14, start.getSecondOfMinute());

        DateTime end = after.getEnd().withZone(DateTimeZone.UTC);
        assertEquals(2017, end.getYear());
        assertEquals(5, end.getMonthOfYear());
        assertEquals(23, end.getDayOfMonth());
        assertEquals(17 - 2, end.getHourOfDay());
        assertEquals(16, end.getMinuteOfHour());
        assertEquals(15, end.getSecondOfMinute());

        assertEquals(new DateTime("2018-06-24T18:17:16-06:00"), after.getFetched());
    }

    /**
     * Ensure that data is transformed into the external api model in tact.
     *
     * In this case, the fetched field is fragmented off of the event by using
     * a pair. We need to make sure that stays in-tact as well, but the format
     * will not change.
     */
    @Test
    public void testTransformFrom()
    {
        Event before = new Event(
            "id",
            "name",
            new DateTime("2016-04-21T16:15:14-06:00").withZone(DateTimeZone.forOffsetHours(-6)),
            new DateTime("2017-05-23T17:16:15-02:00").withZone(DateTimeZone.forOffsetHours(-2)),
            "category",
            Arrays.asList("tag", "tag2"),
            "room",
            Arrays.asList("host 1", "host 2"),
            "description",
            "banner",
            new DateTime("2018-06-24T18:17:16-06:00").withZone(DateTimeZone.forOffsetHours(-6))
        );

        Pair<ApiEvent, DateTime> result = this.transformer.reverseTransform(before);
        ApiEvent after = result.getValue0();

        assertEquals("2016-04-21T16:15:14-06:00", after.start);
        assertEquals("2017-05-23T17:16:15-02:00", after.end);
        assertEquals("id", after.id);
        assertEquals("name", after.name);
        assertEquals("category", after.category);
        assertEquals("tag", after.tags.get(0));
        assertEquals("tag2", after.tags.get(1));
        assertEquals("room", after.room);
        assertEquals("host 1", after.hosts.get(0));
        assertEquals("host 2", after.hosts.get(1));
        assertEquals("description", after.description);
        assertEquals("banner", after.banner);
        assertEquals(new DateTime("2018-06-24T18:17:16-06:00").withZone(DateTimeZone.forOffsetHours(-6)), result.getValue1());
    }

    /**
     * Ensure that multiple transforms also work.
     *
     * Don't really need to check everything again here, just making sure that
     * the right objects come out on the other end is adequate.
     */
    @Test
    public void testBulkTransformTo()
    {
        ApiEvent first = new ApiEvent("id-1", null, null, null, null, null, null, null, null, null);
        ApiEvent second = new ApiEvent("id-2", null, null, null, null, null, null, null, null, null);

        List<ApiEvent> events = Arrays.asList(first, second);
        List<Pair<ApiEvent, DateTime>> fetchedEvents = PairMerge.mergeRight(events, null);

        List<Event> result = this.transformer.bulkTransform(fetchedEvents);

        assertEquals("id-1", result.get(0).getId());
        assertEquals("id-2", result.get(1).getId());
    }

    /**
     * Ensure that multiple transforms also work.
     *
     * Don't really need to check everything again here, just making sure that
     * the right objects come out on the other end is adequate.
     */
    @Test
    public void testBulkTransformFrom()
    {
        Event first = new Event("id-1", null, null, null, null, null, null, null, null, null, null);
        Event second = new Event("id-2", null, null, null, null, null, null, null, null, null, null);

        List<Event> events = Arrays.asList(first, second);

        List<Pair<ApiEvent, DateTime>> result = this.transformer.bulkReverseTransform(events);

        assertEquals("id-1", result.get(0).getValue0().id);
        assertEquals("id-2", result.get(1).getValue0().id);
    }

    /**
     * Ensure that transforming back and forth produces an identical object.
     */
    @Test
    public void testBijectivity()
    {
        Event before = new Event(
            "id",
            "name",
            new DateTime("2016-04-21T16:15:14-00:00"),
            new DateTime("2017-05-23T17:16:15-00:00"),
            "category",
            Arrays.asList("tag", "tag2"),
            "room",
            Arrays.asList("host 1", "host 2"),
            "description",
            "banner",
            new DateTime("2018-06-24T18:17:16-00:00")
        );

        Pair<ApiEvent, DateTime> forwards = this.transformer.reverseTransform(before);
        Event after = this.transformer.transform(forwards);

        assertEquals(before, after);
        assertEquals(before.hashCode(), after.hashCode());
        assertEquals(before.toString(), after.toString());
    }
}
