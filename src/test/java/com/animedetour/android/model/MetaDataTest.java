package com.animedetour.android.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class MetaDataTest
{
    @Test
    public void testDataValidity()
    {
        MetaData test = new MetaData(new DateTime("2014-04-19T15:31:11Z"), new DateTime("2016-04-19T15:31:11Z"));

        assertEquals(new DateTime("2014-04-19T15:31:11Z"), test.getEventsFetched());
        assertEquals(new DateTime("2016-04-19T15:31:11Z"), test.getGuestsFetched());
        assertEquals(Integer.valueOf(1), test.getId());
    }

    @Test
    public void testDefaults()
    {
        MetaData test = new MetaData();

        assertNull(test.getEventsFetched());
        assertNull(test.getGuestsFetched());
        assertEquals(Integer.valueOf(1), test.getId());
    }
}
