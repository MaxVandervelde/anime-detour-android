/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Schedule Day
 *
 * A single day in the event schedule.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class Day
{
    /**
     * The name of the day. E.g. 'Monday' or 'Friday'
     */
    private String name;

    /**
     * The collection of schedule hours in the day
     */
    private List<Hour> hours;

    /**
     * The Date of the schedule day (no timestamp)
     */
    private DateTime dateTime;

    /**
     * @return The name of the day. E.g. 'Monday' or 'Friday'
     */
    final public String getName()
    {
        return name;
    }

    /**
     * @param name The name of the day. E.g. 'Monday' or 'Friday'
     */
    @JsonProperty("day")
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return The collection of schedule hours in the day
     */
    final public List<Hour> getHours()
    {
        return hours;
    }

    /**
     * @param hours The collection of schedule hours in the day
     */
    @JsonProperty("hours")
    public void setHours(List<Hour> hours)
    {
        this.hours = hours;
    }

    /**
     * Set The day's date as a Joda DateTime object
     *
     * @param dateTime The collection of schedule hours in the day
     */
    @JsonProperty("date")
    public void setDateTime(DateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    /**
     * Get The day's date as a Joda DateTime object
     *
     * @return The collection of schedule hours in the day
     */
    final public DateTime getDateTime()
    {
        return this.dateTime;
    }
}
