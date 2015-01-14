/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.api.sched.api;

import com.animedetour.api.sched.api.model.Event;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

public interface ScheduleEndpoint
{
    @GET("/sched_events")
    public void getSchedule(Callback<List<Event>> callback);

    @GET("/sched_events")
    public List<Event> getSchedule();

    @GET("/sched_events")
    public List<Event> getSchedule(@Query("since") long sinceTimeStamp);
}
