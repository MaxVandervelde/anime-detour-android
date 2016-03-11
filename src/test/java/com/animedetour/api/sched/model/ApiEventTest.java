/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.api.sched.model;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class ApiEventTest extends TestCase
{
    /** Ensure that data comes out exactly as it goes in. */
    @Test
    public void testDataIntegrity()
    {
        ApiEvent event = new ApiEvent(
            "id",
            "name",
            "start",
            "end",
            "category",
            Arrays.asList("tag", "tag2"),
            "room",
            Arrays.asList("host 1", "host 2"),
            "description",
            "banner"
        );

        assertEquals("id", event.id);
        assertEquals("name", event.name);
        assertEquals("start", event.start);
        assertEquals("end", event.end);
        assertEquals("category", event.category);
        assertEquals("tag", event.tags.get(0));
        assertEquals("tag2", event.tags.get(1));
        assertEquals("room", event.room);
        assertEquals("description", event.description);
        assertEquals("banner", event.banner);
    }
}
