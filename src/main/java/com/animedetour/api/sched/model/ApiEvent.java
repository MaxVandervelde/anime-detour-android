/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.api.sched.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * The most basic object representation of the json API for an event.
 *
 * This object doesn't use getters, since it should not ever manipulate the data
 * and should only represent exactly what was given from the API.
 *
 * Don't use this model in application code. Instead, adapt it to a local model
 * with custom business logic.
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
public class ApiEvent implements Serializable
{
    final public String id;
    final public String name;
    final public String start;
    final public String end;
    final public String category;
    final public List<String> tags;
    final public String room;
    final public List<String> hosts;
    final public String description;
    final public String banner;

    @JsonCreator
    public ApiEvent(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("start") String start,
        @JsonProperty("end") String end,
        @JsonProperty("category") String category,
        @JsonProperty("tags") List<String> tags,
        @JsonProperty("room") String room,
        @JsonProperty("hosts") List<String> hosts,
        @JsonProperty("description") String description,
        @JsonProperty("banner") String banner
    ) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.category = category;
        this.tags = tags;
        this.room = room;
        this.hosts = hosts;
        this.description = description;
        this.banner = banner;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiEvent apiEvent = (ApiEvent) o;

        if (id != null ? !id.equals(apiEvent.id) : apiEvent.id != null)
            return false;
        if (name != null ? !name.equals(apiEvent.name) : apiEvent.name != null)
            return false;
        if (start != null ? !start.equals(apiEvent.start) : apiEvent.start != null)
            return false;
        if (end != null ? !end.equals(apiEvent.end) : apiEvent.end != null)
            return false;
        if (category != null ? !category.equals(apiEvent.category) : apiEvent.category != null)
            return false;
        if (tags != null ? !tags.equals(apiEvent.tags) : apiEvent.tags != null)
            return false;
        if (room != null ? !room.equals(apiEvent.room) : apiEvent.room != null)
            return false;
        if (hosts != null ? !hosts.equals(apiEvent.hosts) : apiEvent.hosts != null)
            return false;
        if (description != null ? !description.equals(apiEvent.description) : apiEvent.description != null)
            return false;
        return banner != null ? banner.equals(apiEvent.banner) : apiEvent.banner == null;

    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (hosts != null ? hosts.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (banner != null ? banner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "ApiEvent{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", start='" + start + '\'' +
            ", end='" + end + '\'' +
            ", category='" + category + '\'' +
            ", tags=" + tags +
            ", room='" + room + '\'' +
            ", hosts=" + hosts +
            ", description='" + description + '\'' +
            ", banner='" + banner + '\'' +
            '}';
    }
}
