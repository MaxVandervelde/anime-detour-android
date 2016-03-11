/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class EventTest
{
    @Test
    public void testDataIntegrity()
    {
        Event test = new Event(
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

        assertEquals("id", test.getId());
        assertEquals("name", test.getName());
        assertEquals(new DateTime("2016-04-21T16:15:14-06:00").withZone(DateTimeZone.forOffsetHours(-6)), test.getStart());
        assertEquals(new DateTime("2017-05-23T17:16:15-02:00").withZone(DateTimeZone.forOffsetHours(-2)), test.getEnd());
        assertEquals("category", test.getCategory());
        assertEquals(Arrays.asList("tag", "tag2"), test.getTags());
        assertEquals("room", test.getRoom());
        assertEquals(Arrays.asList("host 1", "host 2"), test.getHosts());
        assertEquals("description", test.getDescription());
        assertEquals("banner", test.getBanner());
        assertEquals(new DateTime("2018-06-24T18:17:16-06:00").withZone(DateTimeZone.forOffsetHours(-6)), test.getFetched());
    }

    @Test
    public void testImmutability()
    {
        Event first = new Event();
        Event second = first.withFetched(new DateTime());

        assertNull(first.getFetched());
        assertNotNull(second.getFetched());
        assertNotEquals(first, second);
        assertNotSame(first, second);
    }
}
