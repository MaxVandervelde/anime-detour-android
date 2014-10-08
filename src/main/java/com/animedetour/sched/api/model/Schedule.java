/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Event Schedule
 *
 * Represents the container object for the entire schedule hierarchy of an event
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class Schedule
{
    /**
     * The days for the event schedule
     */
    private List<Day> days;

    /**
     * @return The days in schedule of event
     */
    public List<Day> getDays()
    {
        return days;
    }

    /**
     * @param days Set the days in schedule of event
     */
    @JsonProperty("days")
    public void setDays(List<Day> days)
    {
        this.days = days;
    }
}
