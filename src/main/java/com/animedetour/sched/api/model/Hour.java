/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Schedule Hour
 *
 * A single hour in the schedule
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class Hour
{
    /**
     * The name representation of the hour. E.g. '8 AM' or '2 PM'
     */
    private String name;

    /**
     * The collection of panels that take place in this scheduled hour
     */
    private List<Panel> panels;

    /**
     * @return The name representation of the hour. E.g. '8 AM' or '2 PM'
     */
    final public String getName()
    {
        return name;
    }

    /**
     * @param name The name representation of the hour. E.g. '8 AM' or '2 PM'
     */
    @JsonProperty("hour")
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return The collection of panels that take place in this scheduled hour
     */
    final public List<Panel> getPanels()
    {
        return panels;
    }

    /**
     * @param panels The collection of panels that take place in this scheduled hour
     */
    @JsonProperty("panels")
    public void setPanels(List<Panel> panels)
    {
        this.panels = panels;
    }
}
