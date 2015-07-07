/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.serach;

import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.android.widget.listview.ItemAdapter;
import org.apache.commons.logging.Log;
import rx.Observer;

import java.util.List;

/**
 * Listens for updates to search result contents and updates an item adapter
 * in order to display the result set to the user.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventSearchResultObserver implements Observer<List<Event>>
{
    final private Log logger;
    final private ItemAdapter<?, Event> adapter;

    public EventSearchResultObserver(Log logger, ItemAdapter<?, Event> adapter) {
        this.logger = logger;
        this.adapter = adapter;
    }

    @Override public void onCompleted() {}

    @Override
    public void onError(Throwable e)
    {
        this.logger.error("Problem searching for event", e);
    }

    @Override
    public void onNext(List<Event> events)
    {
        this.adapter.setItems(events);
    }
}
