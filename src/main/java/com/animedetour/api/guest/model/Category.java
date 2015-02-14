/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.api.guest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

/**
 * A named grouping of guests.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@DatabaseTable
public class Category implements Serializable
{
    /**
     * A locally generated ID by the database. This should not exist unless the
     * entity has been saved locally.
     */
    @DatabaseField(generatedId = true)
    private Integer id;

    /**
     * The name of the group of guests. ex. "Guests of Honor"
     */
    @DatabaseField
    @JsonProperty("categoryname")
    private String name;

    @ForeignCollectionField(eager = true)
    @JsonProperty("guests")
    private Collection<Guest> guests;

    /**
     * @return The locally generated ID by the database or null if not locally saved.
     */
    final public Integer getId()
    {
        return this.id;
    }

    /**
     * @param id The locally generated ID by the database.
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * @return The title of the grouping of guests. ex. "Guests of Honor"
     */
    final public String getName()
    {
        return this.name;
    }

    /**
     * @param name The title of the grouping of guests. ex. "Guests of Honor"
     */
    public void setName(String name)
    {
        this.name = name;
    }

    final public Collection<Guest> getGuests()
    {
        return this.guests;
    }

    public void setGuests(Collection<Guest> guests)
    {
        for (Guest guest : guests) {
            guest.setCategory(this);
        }
        this.guests = guests;
    }

    @Override
    public String toString()
    {
        return "Category{"
            + "id=" + id
            + ", name='" + name + '\''
            + '}';
    }
}
