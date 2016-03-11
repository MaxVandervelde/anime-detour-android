/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database;

import com.animedetour.android.database.event.AllEventsByDayFactory;
import com.animedetour.android.database.event.AllEventsMatchingFactory;
import com.animedetour.android.database.event.AllEventsWorker;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.android.database.event.UpcomingEventByTypeFactory;
import com.animedetour.android.database.event.UpcomingEventsByTagFactory;
import com.animedetour.android.database.event.type.AllEventTypesWorker;
import com.animedetour.android.database.event.type.EventTypeRepository;
import com.animedetour.android.database.favorite.FavoriteRepository;
import com.animedetour.android.database.favorite.GetAllFavoritesWorker;
import com.animedetour.android.database.guest.AllCategoriesWorker;
import com.animedetour.android.database.guest.GuestRepository;
import com.animedetour.android.model.Event;
import com.animedetour.android.model.transformer.Transformer;
import com.animedetour.android.schedule.favorite.Favorite;
import com.animedetour.api.guest.GuestEndpoint;
import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.animedetour.api.sched.ScheduleEndpoint;
import com.animedetour.api.sched.model.ApiEvent;
import com.inkapplications.groundcontrol.SubscriptionFactory;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import dagger.Module;
import dagger.Provides;
import monolog.Monolog;
import org.javatuples.Pair;
import org.joda.time.DateTime;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Singleton;
import java.sql.SQLException;

@Module(library = true, complete = false)
@SuppressWarnings("UnusedDeclaration")
final public class DataModule
{
    @Provides
    @Singleton
    public EventRepository eventRepository(
        ConnectionSource connectionSource,
        ScheduleEndpoint remote,
        Transformer<Pair<ApiEvent, DateTime>, Event> apiEventTransformer,
        Monolog logger
    ) {
        Scheduler main = AndroidSchedulers.mainThread();
        Scheduler io = Schedulers.io();
        SubscriptionFactory<Event> subscriptionFactory = new SubscriptionFactory<>(io, main);

        try {
            Dao<Event, String> local = DaoManager.createDao(connectionSource, Event.class);

            return new EventRepository(
                subscriptionFactory,
                local,
                new AllEventsWorker(local, remote, apiEventTransformer),
                new AllEventsByDayFactory(local, remote, apiEventTransformer),
                new UpcomingEventsByTagFactory(local, remote, apiEventTransformer),
                new UpcomingEventByTypeFactory(local, remote, apiEventTransformer),
                new AllEventsMatchingFactory(local, remote, apiEventTransformer)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    public GuestRepository guestRepository(
        ConnectionSource connectionSource,
        GuestEndpoint remote,
        Monolog logger
    ) {
        Scheduler main = AndroidSchedulers.mainThread();
        Scheduler io = Schedulers.io();
        SubscriptionFactory<Category> subscriptionFactory = new SubscriptionFactory<>(io, main);

        try {
            Dao<Category, Integer> localCategory = DaoManager.createDao(connectionSource, Category.class);
            Dao<Guest, String> localGuest = DaoManager.createDao(connectionSource, Guest.class);
            return new GuestRepository(
                subscriptionFactory,
                new AllCategoriesWorker(connectionSource, localCategory, localGuest, remote)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    public FavoriteRepository favoriteRepository(ConnectionSource connectionSource)
    {
        try {
            Dao<Favorite, Integer> local = DaoManager.createDao(connectionSource, Favorite.class);
            Dao<Event, Integer> eventLocal = DaoManager.createDao(connectionSource, Event.class);
            GetAllFavoritesWorker collectionWorker = new GetAllFavoritesWorker(local, eventLocal);

            return new FavoriteRepository(local, collectionWorker);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    public EventTypeRepository eventTypeRepository(ConnectionSource connectionSource)
    {
        Scheduler main = AndroidSchedulers.mainThread();
        Scheduler io = Schedulers.io();
        SubscriptionFactory<String> subscriptionFactory = new SubscriptionFactory<>(io, main);

        try {
            Dao<Event, String> local = DaoManager.createDao(connectionSource, Event.class);
            AllEventTypesWorker worker = new AllEventTypesWorker(local);

            return new EventTypeRepository(subscriptionFactory, worker);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    public ConnectionSource connectionSource(DetourDatabaseHelper helper)
    {
        return new AndroidConnectionSource(helper);
    }
}
