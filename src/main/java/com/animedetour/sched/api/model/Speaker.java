/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Event / Panel Speaker
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class Speaker
{
    /**
     * A unique ID for the speaker
     */
    private String id;

    /**
     * The username of the speaker
     */
    private String username;

    /**
     * The real name of the speaker
     */
    private String name;

    /**
     * @return A unique ID for the speaker
     */
    final public String getId()
    {
        return id;
    }

    /**
     * @param id A unique ID for the speaker
     */
    @JsonProperty("id")
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return The username of the speaker
     */
    final public String getUsername()
    {
        return username;
    }

    /**
     * @param username The username of the speaker
     */
    @JsonProperty("username")
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return The real name of the speaker
     */
    final public String getName()
    {
        return name;
    }

    /**
     * @param name The real name of the speaker
     */
    @JsonProperty("name")
    public void setName(String name)
    {
        this.name = name;
    }
}
