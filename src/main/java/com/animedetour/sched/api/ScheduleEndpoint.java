/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api;

import com.animedetour.sched.api.model.Schedule;
import retrofit.Callback;
import retrofit.http.GET;

public interface ScheduleEndpoint
{
    @GET("/sched_list")
    public void getSchedule(Callback<Schedule> callback);
}
