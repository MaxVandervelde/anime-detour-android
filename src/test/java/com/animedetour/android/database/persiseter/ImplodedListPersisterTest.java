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

    /**
     * Make sure the perister can convert the comma-separated values as they
     * are in the database into a proper java list.
     */
    @Test
    public void testSqlArgToJava() throws Exception
    {

        List<String> result = (List<String>) this.subject.sqlArgToJava(null, "foo,bar,baz", 0);

        assertEquals(3, result.size());
        assertEquals("foo", result.get(0));
        assertEquals("bar", result.get(1));
        assertEquals("baz", result.get(2));
    }

    /**
     * Make sure the persister can convert a java list into a proper
     * comma-separated value string to be inserted into the database.
     */
    @Test
    public void testJavaToSqlArg() throws Exception
    {
        List<String> arguments = Arrays.asList("foo", "bar", "baz");
        String result = (String) this.subject.javaToSqlArg(null, arguments);

        assertEquals("foo,bar,baz", result);
    }

    /**
     * Objects that are already strings should be allowed to be passed directly
     * into the database unchanged.
     */
    @Test
    public void testSearchArgToSqlArg() throws Exception
    {
        String result = (String) this.subject.javaToSqlArg(null, "%test%");

        assertEquals("%test%", result);
    }
}
