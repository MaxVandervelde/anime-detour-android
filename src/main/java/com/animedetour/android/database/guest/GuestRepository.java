/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.database.guest;

import com.animedetour.api.guest.model.Category;
import com.inkapplications.groundcontrol.SubscriptionFactory;
import rx.Observer;
import rx.Subscription;

import java.util.List;

/**
 * Provides an API for looking up the guest list for the convention.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class GuestRepository
{
    /** Manage in-flight requests to async repos. */
    final private SubscriptionFactory<Category> subscriptionFactory;

    /** Worker for looking up a list of all guest categories. */
    final private AllCategoriesWorker allCategoriesWorker;

    /**
     * @param subscriptionFactory Manage in-flight requests to async repos.
     * @param allCategoriesWorker Worker for looking up a list of all guest categories.
     */
    public GuestRepository(
        SubscriptionFactory<Category> subscriptionFactory,
        AllCategoriesWorker allCategoriesWorker
    ) {
        this.subscriptionFactory = subscriptionFactory;
        this.allCategoriesWorker = allCategoriesWorker;
    }

    /**
     * Find all guests grouped into categories.
     */
    public Subscription findAllCategories(Observer<List<Category>> observer)
    {
        return this.subscriptionFactory.createCollectionSubscription(
            this.allCategoriesWorker,
            observer,
            "findAllCategories"
        );
    }
}
