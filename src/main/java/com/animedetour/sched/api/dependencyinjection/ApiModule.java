/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.sched.api.dependencyinjection;

import com.animedetour.sched.api.ScheduleEndpoint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

import javax.inject.Singleton;

@Module(complete = false, library = true)
public class ApiModule
{
    @Provides @Singleton ScheduleEndpoint provideScheduleEndpoint() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://animedetour.com");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        builder.setConverter(new JacksonConverter(mapper));
        RestAdapter adapter = builder.build();

        return adapter.create(ScheduleEndpoint.class);
    }
}
