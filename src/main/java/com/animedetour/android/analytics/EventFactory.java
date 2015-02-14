/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.analytics;

import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.prism.analytics.TrackedEvent;

/**
 * Generates new event tracking objects for consistent use throughout the
 * application.
 *
 * These tracked events are designed to be passed to a log for tracing.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class EventFactory
{
    public static TrackedEvent eventDetails(Event target)
    {
        return new TrackedEvent("Event", "View Details", target.getName());
    }

    public static TrackedEvent favoriteEvent(Event target)
    {
        return new TrackedEvent("Event", "Favorite", target.getName());
    }
}
