/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

/**
 * Extra information about the data we're storing.
 *
 * This is intended to only be a single instance, which is why you cannot
 * set the ID. There is a constant for the ID that should exist in the database,
 * so that it may be created/read/updated from that.
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
@DatabaseTable
public class MetaData
{
    /**
     * The ID of the single row of information that should be in the database.
     */
    final static public Integer SINGLETON = 1;

    /**
     * Unique ID Hardcoded so that there is only one row of this information.
     */
    @DatabaseField(id = true)
    final private Integer id;

    /**
     * The timestamp that event information was last updated on.
     */
    @DatabaseField(dataType = DataType.DATE_TIME)
    final private DateTime eventsFetched;

    /**
     * The timestamp that guest information was last updated on.
     */
    @DatabaseField(dataType = DataType.DATE_TIME)
    final private DateTime guestsFetched;

    /**
     * @param eventsFetched The timestamp that event information was last updated on.
     */
    public MetaData(DateTime eventsFetched, DateTime guestsFetched)
    {
        this.id = SINGLETON;
        this.eventsFetched = eventsFetched;
        this.guestsFetched = guestsFetched;
    }

    public MetaData()
    {
        this.id = SINGLETON;
        this.guestsFetched = null;
        this.eventsFetched = null;
    }

    /**
     * @return The timestamp that event information was last updated on.
     */
    public DateTime getEventsFetched()
    {
        return this.eventsFetched;
    }

    /**
     * @return The timestamp that guest information was last updated on.
     */
    public DateTime getGuestsFetched() {
        return this.guestsFetched;
    }

    /**
     * @return Unique ID, Hardcoded so that there is only one row of this information.
     */
    public Integer getId()
    {
        return this.id;
    }

    /**
     * Creates a cloned object with a specified eventsFetched value.
     *
     * @param fetched The time that events were fetched.
     * @return A new object, not a mutated instance of the existing object.
     */
    public MetaData withEventsFetched(DateTime fetched)
    {
        return new MetaData(fetched, this.guestsFetched);
    }

    /**
     * Creates a cloned object with a specified guestsFetched value.
     *
     * @param fetched The time that guests were fetched.
     * @return A new object, not a mutated instance of the existing object.
     */
    public MetaData withGuestsFetched(DateTime fetched)
    {
        return new MetaData(this.eventsFetched, fetched);
    }
}
