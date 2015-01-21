/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.api.guest;

import com.animedetour.api.guest.model.Category;
import retrofit.Callback;
import retrofit.http.GET;

import java.util.List;

public interface GuestEndpoint
{
    @GET("/guest_list/2")
    public void getAll(Callback<List<Category>> callback);

    @GET("/guest_list/2")
    public List<Category> getAll();
}
