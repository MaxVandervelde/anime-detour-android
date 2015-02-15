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
public class TagCriteria
{
    /**
     * The tag to search for an event containing.
     */
    final private String search;

    /**
     * The offset of the event to find in the list of events containing the
     * specified tag.
     */
    final private long ordinal;

    /**
     * @param search The tag to search for an event containing.
     * @param ordinal The offset of the event to find in the list of events
     *                containing the specified tag.
     */
    public TagCriteria(String search, long ordinal)
    {
        this.search = search;
        this.ordinal = ordinal;
    }

    /**
     * @return The tag to search for an event containing.
     */
    final public String getSearch()
    {
        return this.search;
    }

    /**
     * @return The offset of the event to find in the list of events containing
     *         the specified tag.
     */
    final public long getOrdinal()
    {
        return this.ordinal;
    }
}
