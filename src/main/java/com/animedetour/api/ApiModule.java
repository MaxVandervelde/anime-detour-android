/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.api;

import com.animedetour.api.guest.GuestEndpoint;
import com.animedetour.api.sched.api.ScheduleEndpoint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import org.apache.commons.logging.Log;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

import javax.inject.Singleton;

@Module(complete = false, library = true)
@SuppressWarnings("UnusedDeclaration")
final public class ApiModule
{
    @Provides @Singleton ScheduleEndpoint provideScheduleEndpoint(
        RestAdapter adapter
    ) {
        return adapter.create(ScheduleEndpoint.class);
    }

    @Provides @Singleton GuestEndpoint provideGuestEndpoint(
        RestAdapter adapter
    ) {
        return adapter.create(GuestEndpoint.class);
    }

    @Provides @Singleton RestAdapter provideRestAdapter(
        final Log logger,
        OkHttpClient client,
        ObjectMapper mapper
    ) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://animedetour.com");

        builder.setConverter(new JacksonConverter(mapper));

        builder.setLog(
            new RestAdapter.Log() {
                @Override public void log(String message) {
                    logger.debug(message);
                }
            }
        );
        builder.setLogLevel(RestAdapter.LogLevel.BASIC);

        builder.setClient(new OkClient(client));

        RestAdapter adapter = builder.build();

        return adapter;
    }

    @Provides @Singleton ObjectMapper provideObjectMapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }
}
