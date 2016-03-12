/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.model;

import com.animedetour.android.database.persiseter.ImplodedListPersister;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A local representation of the Event data we use in the app.
 *
 * This model should not change with API changes until we need those features.
 * The idea is that this is separate from the API model so that we don't need
 * to update the application code when the API changes, we can just update
 * the transformer that creates these objects instead.
 *
 * @author Maxwell Vandervelde <Max@MaxVandervelde.com>
 */
@DatabaseTable
public class Event implements Serializable
{
    /** Globally Unique ID for the event. */
    @DatabaseField(id = true)
    final private String id;

    /** A short title/name of the event to be referenced as througout the app. */
    @DatabaseField(index = true)
    final private String name;

    /** The time that the event starts. */
    @DatabaseField(index = true, dataType = DataType.DATE_TIME)
    final private DateTime start;

    /** The time that the event will end. */
    @DatabaseField(index = true, dataType = DataType.DATE_TIME)
    final private DateTime end;

    /** The Type specified for the event. */
    @DatabaseField(index = true)
    final private String category;

    /**
     * Additional meta-information about the event that can be used to
     * categorize or group events.
     *
     * This is internally stored as an ArrayList so that it is serializable.
     *
     * These tags will be lowercase slugs, and will not contain spaces.
     * This array can be empty, but never null.
     * This list will not be modifyable through getters.
     */
    @DatabaseField(persisterClass = ImplodedListPersister.class)
    final private List<String> tags;

    /** The name of the room / venue that the event is being held in. */
    @DatabaseField
    final private String room;

    /**
     * A list of the people who will be running or speaking at the event.
     *
     * This is internally stored as an ArrayList so that it is serializable.
     *
     * This list can be empty, but never null.
     * This list will not be modifyable through getters.
     */
    @DatabaseField(persisterClass = ImplodedListPersister.class)
    final private List<String> hosts;

    /** Detailed information about the event content. */
    @DatabaseField
    final private String description;

    /** An optional media image to decorate the event with. */
    @DatabaseField
    final private String banner;

    /** The timestamp that this event data was retrieved from the remote API. */
    @DatabaseField(index = true, dataType = DataType.DATE_TIME)
    final private DateTime fetched;

    /**
     * Create a default object with allowed nulls/default values.
     */
    public Event()
    {
        this.id = null;
        this.name = "";
        this.start = null;
        this.end = null;
        this.category = null;
        this.tags = new ArrayList<>();
        this.room = null;
        this.hosts = new ArrayList<>();
        this.description = null;
        this.banner = null;
        this.fetched = null;
    }

    /**
     * Create a fully specified instance of this object.
     *
     * @param id Globally Unique ID for the event.
     * @param name A short title for the event to be used throughout the app.
     * @param start The time that the event starts.
     * @param end The time that the event ends.
     * @param category The type specified for this event.
     * @param tags Additional meta-information about the event.
     * @param room The name of the venue that the event is being held in.
     * @param hosts A list of people running/speaking at the event.
     * @param description Detailed information about the event content.
     * @param banner Optional media image to decorate the event with.
     * @param fetched Timestamp that the event data was retrieved from the API.
     */
    public Event(
        String id,
        String name,
        DateTime start,
        DateTime end,
        String category,
        List<String> tags,
        String room,
        List<String> hosts,
        String description,
        String banner,
        DateTime fetched
    ) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.category = category;
        this.tags = tags == null ? new ArrayList<String>() : new ArrayList<>(tags);
        this.room = room;
        this.hosts = hosts == null ? new ArrayList<String>() : new ArrayList<>(hosts);
        this.description = description;
        this.banner = banner;
        this.fetched = fetched;
    }

    /**
     * @return Globally Unique ID for the event.
     */
    final public String getId()
    {
        return this.id;
    }

    /**
     * @return A short title for the event to be used throughout the app.
     */
    final public String getName()
    {
        return this.name;
    }

    /**
     * @return The time that the event starts.
     */
    final public DateTime getStart()
    {
        return this.start;
    }

    /**
     * @return The time that the event ends.
     */
    final public DateTime getEnd()
    {
        return this.end;
    }

    /**
     * @return The type specified for this event.
     */
    final public String getCategory()
    {
        return this.category;
    }

    /**
     * @return Additional meta-information about the event.
     */
    final public List<String> getTags()
    {
        return Collections.unmodifiableList(this.tags);
    }

    /**
     * @return The name of the venue that the event is being held in.
     */
    final public String getRoom()
    {
        return this.room;
    }

    /**
     * @return A list of people running/speaking at the event.
     */
    final public List<String> getHosts()
    {
        return Collections.unmodifiableList(this.hosts);
    }

    /**
     * @return Detailed information about the event content.
     */
    final public String getDescription()
    {
        return this.description;
    }

    /**
     * @return Optional media image to decorate the event with.
     */
    final public String getBanner()
    {
        return this.banner;
    }

    /**
     * @return Timestamp that the event data was retrieved from the API.
     */
    final public DateTime getFetched()
    {
        return this.fetched;
    }

    /**
     * Create a new Event object with a different fetched time.
     *
     * @param newFetched The fetched time to change to.
     * @return A new object with the different fetched time.
     */
    public Event withFetched(DateTime newFetched)
    {
        return new Event(id, name, start, end, category, tags, room, hosts, description, banner, newFetched);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null)
            return false;
        if (start != null ? !start.equals(event.start) : event.start != null)
            return false;
        if (end != null ? !end.equals(event.end) : event.end != null)
            return false;
        if (category != null ? !category.equals(event.category) : event.category != null)
            return false;
        if (!tags.equals(event.tags)) return false;
        if (room != null ? !room.equals(event.room) : event.room != null)
            return false;
        if (!hosts.equals(event.hosts)) return false;
        if (description != null ? !description.equals(event.description) : event.description != null)
            return false;
        if (banner != null ? !banner.equals(event.banner) : event.banner != null)
            return false;
        return fetched != null ? fetched.equals(event.fetched) : event.fetched == null;

    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + tags.hashCode();
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + hosts.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (banner != null ? banner.hashCode() : 0);
        result = 31 * result + (fetched != null ? fetched.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Event{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", start=" + start +
            ", end=" + end +
            ", category='" + category + '\'' +
            ", tags=" + tags +
            ", room='" + room + '\'' +
            ", hosts=" + hosts +
            ", description='" + description + '\'' +
            ", banner='" + banner + '\'' +
            ", fetched=" + fetched +
            '}';
    }
}
