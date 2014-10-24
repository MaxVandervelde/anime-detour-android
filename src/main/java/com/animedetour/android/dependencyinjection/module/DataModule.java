package com.animedetour.android.dependencyinjection.module;

import android.app.Activity;
import com.animedetour.android.database.event.EventDatabaseHelper;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.sched.api.ScheduleEndpoint;
import com.animedetour.sched.api.model.Event;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import dagger.Module;
import dagger.Provides;

import java.sql.SQLException;

@Module(library = true, complete = false)
public class DataModule
{
    @Provides EventRepository provideRepository(
        Activity activity,
        ScheduleEndpoint remote
    ) {
        EventDatabaseHelper helper = new EventDatabaseHelper(activity);
        ConnectionSource connectionSource = new AndroidConnectionSource(helper);

        try {
            Dao<Event, String> local = DaoManager.createDao(connectionSource, Event.class);
            return new EventRepository(local, remote);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
