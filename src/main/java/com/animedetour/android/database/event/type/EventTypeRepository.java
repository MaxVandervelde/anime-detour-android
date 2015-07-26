/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.event.type;

import com.inkapplications.groundcontrol.SubscriptionFactory;
import com.inkapplications.groundcontrol.Worker;
import rx.Observer;
import rx.Subscription;

import java.util.List;

/**
 * Looks up information about the type field on the event object.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventTypeRepository
{
    final private SubscriptionFactory<String> categorySubscriptionFactory;
    final private Worker<List<String>> allCategoriesWorker;

    public EventTypeRepository(
        SubscriptionFactory<String> categorySubscriptionFactory,
        Worker<List<String>> allCategoriesWorker
    ) {
        this.categorySubscriptionFactory = categorySubscriptionFactory;
        this.allCategoriesWorker = allCategoriesWorker;
    }

    /**
     * Look up all of the different event types on events saved in the local database.
     */
    public Subscription findAllCategories(Observer<List<String>> observer)
    {
        return this.categorySubscriptionFactory.createCollectionSubscription(
            this.allCategoriesWorker,
            observer,
            "findAllCategories"
        );
    }
}
