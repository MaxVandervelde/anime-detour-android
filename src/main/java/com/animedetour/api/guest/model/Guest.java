/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.api.guest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Model for a special guest of Detour's.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@DatabaseTable
public class Guest implements Serializable
{
    /**
     * Unique Identifier for the guest.
     */
    @DatabaseField(id = true)
    @JsonProperty("id")
    private String id;

    /**
     * The given name of the guest.
     */
    @DatabaseField
    @JsonProperty("FirstName")
    private String firstName;

    /**
     * The Family name of the guest.
     */
    @DatabaseField
    @JsonProperty("LastName")
    private String lastName;

    /**
     * A detailed html encoded biography describing the guest.
     */
    @DatabaseField
    @JsonProperty("Bio")
    private String bio;

    /**
     * An HTTP URI for an avatar of the guest.
     */
    @DatabaseField
    @JsonProperty("PhotoPath")
    private String photo;

    /**
     * The group of guests this person is in.
     */
    @DatabaseField(foreign = true)
    private Category category;

    /**
     * @return Unique Identifier for the guest.
     */
    final public String getId()
    {
        return this.id;
    }

    /**
     * @param id Unique Identifier for the guest.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return The given name of the guest.
     */
    final public String getFirstName()
    {
        return this.firstName;
    }

    /**
     * @param firstName The given name of the guest.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return The Family name of the guest.
     */
    final public String getLastName()
    {
        return this.lastName;
    }

    /**
     * @param lastName The Family name of the guest.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return A detailed html encoded biography describing the guest.
     */
    final public String getBio()
    {
        return this.bio;
    }

    /**
     * @param bio A detailed html encoded biography describing the guest.
     */
    public void setBio(String bio)
    {
        this.bio = bio;
    }

    /**
     * @return An HTTP URI for an avatar of the guest.
     */
    final public String getPhoto()
    {
        return this.photo;
    }

    /**
     * @param photo An HTTP URI for an avatar of the guest.
     */
    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    /**
     * @return The group of guests this person is in.
     */
    final public Category getCategory()
    {
        return category;
    }

    /**
     * @param category The group of guests this person is in.
     */
    public void setCategory(Category category)
    {
        this.category = category;
    }

    @Override
    public String toString()
    {
        return "Guest{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", bio='" + bio + '\'' +
            ", photo='" + photo + '\'' +
            '}';
    }
}
