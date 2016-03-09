/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.api.sched;

import com.animedetour.api.sched.model.ApiEvent;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

public interface ScheduleEndpoint
{
    @GET("/programming_events")
    public void getSchedule(Callback<List<ApiEvent>> callback);

    @GET("/programming_events")
    public List<ApiEvent> getSchedule();

    @GET("/programming_events")
    public List<ApiEvent> getSchedule(@Query("since") long sinceTimeStamp);
}
