/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.persiseter;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ImplodedListPersisterTest
{
    private final ImplodedListPersister subject = new ImplodedListPersister();

    @Test
    public void testSqlArgToJava() throws Exception
    {

        List<String> result = (List<String>) this.subject.sqlArgToJava(null, "foo,bar,baz", 0);

        assertEquals(3, result.size());
        assertEquals("foo", result.get(0));
        assertEquals("bar", result.get(1));
        assertEquals("baz", result.get(2));
    }

    @Test
    public void testJavaToSqlArg() throws Exception
    {
        List<String> arguments = Arrays.asList("foo", "bar", "baz");
        String result = (String) this.subject.javaToSqlArg(null, arguments);

        assertEquals("foo,bar,baz", result);
    }
}
