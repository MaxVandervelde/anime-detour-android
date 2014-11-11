/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.landing.schedule;

import android.view.View;
import com.animedetour.sched.api.model.Event;
import org.apache.commons.logging.Log;
import rx.Subscriber;

import java.util.List;

/**
 * Subscriber used to listen for updates to the event list.
 *
 * When an update to the events list is detected, this will notify the fragment
 * of the new data and handle any errors.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
class EventUpdateSubscriber extends Subscriber<List<Event>>
{
    private DayFragment fragment;
    private View emptyView;
    private Log logger;

    public EventUpdateSubscriber(DayFragment fragment, View emptyView, Log logger)
    {
        this.fragment = fragment;
        this.emptyView = emptyView;
        this.logger = logger;
    }

    @Override
    public void onCompleted()
    {
    }

    @Override
    public void onError(Throwable e)
    {
        this.logger.error("Error fetching schedule", e);
    }

    @Override
    public void onNext(List<Event> events)
    {
        if (events.size() == 0) {
            this.emptyView.setVisibility(View.VISIBLE);
        } else {
            this.emptyView.setVisibility(View.GONE);
        }

        this.fragment.updateEvents(events);
    }
}
