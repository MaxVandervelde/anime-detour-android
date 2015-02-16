/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event;

/**
 * Criteria for looking up a single event by tag.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class TypeCriteria
{
    /**
     * The type to search for events containing.
     */
    final private String type;

    /**
     * The offset of the event to find in the list of events.
     */
    final private long ordinal;

    /**
     * @param type type to search for events containing.
     * @param ordinal offset of the event to find in the list of events.
     */
    public TypeCriteria(String type, long ordinal)
    {
        this.ordinal = ordinal;
        this.type = type;
    }

    /**
     * @return The type to search for events containing.
     */
    final public String getType()
    {
        return type;
    }

    /**
     * @return The offset of the event to find in the list of events.
     */
    final public long getOrdinal()
    {
        return ordinal;
    }
}
