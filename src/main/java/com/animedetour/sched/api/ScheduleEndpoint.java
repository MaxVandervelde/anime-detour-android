/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api;

import com.animedetour.sched.api.model.Event;
import retrofit.Callback;
import retrofit.http.GET;

import java.util.List;

public interface ScheduleEndpoint
{
    @GET("/sched_events")
    public void getSchedule(Callback<List<Event>> callback);

    @GET("/sched_events")
    public List<Event> getSchedule();
}
