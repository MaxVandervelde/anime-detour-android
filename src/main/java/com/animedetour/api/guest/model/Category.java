/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015-2016 Anime Twin Cities, Inc.
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
     * The name of the group of guests. ex. "Guests of Honor"
     */
    @DatabaseField(id = true)
    @JsonProperty("categoryname")
    private String name;

    @ForeignCollectionField(eager = true)
    @JsonProperty("guests")
    private Collection<Guest> guests;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (name != null ? !name.equals(category.name) : category.name != null)
            return false;
        return guests != null ? guests.equals(category.guests) : category.guests == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (guests != null ? guests.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
            "name='" + name + '\'' +
            ", guests=" + guests +
            '}';
    }
}
