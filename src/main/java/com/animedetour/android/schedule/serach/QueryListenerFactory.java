/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.serach;

import android.support.v7.widget.SearchView;
import android.view.View;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.api.sched.model.Event;
import com.inkapplications.android.widget.listview.ItemAdapter;
import rx.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Creates instances of the query listener to be used for watching search
 * queries and looking up events based on the text entered that is injected
 * at runtime.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class QueryListenerFactory
{
    final private ResultObserverFactory resultObserverFactory;
    final private EventRepository eventData;

    @Inject
    public QueryListenerFactory(
        ResultObserverFactory resultObserverFactory,
        EventRepository eventData
    ) {
        this.resultObserverFactory = resultObserverFactory;
        this.eventData = eventData;
    }

    /**
     * @param adapter Adapter for displaying search result views.
     * @param emptyResultsView View to display when there are no results for
     *                         the search query.
     * @param results View that the search results will be displayed in.
     * @param emptySearchView View to display when the search query is empty.
     * @return Listener to be bound to the search box.
     */
    public SearchView.OnQueryTextListener create(
        ItemAdapter<?, Event> adapter,
        View emptyResultsView,
        View results,
        View emptySearchView
    ) {
        Observer<List<Event>> observer = this.resultObserverFactory.create(adapter, emptyResultsView);
        return new EventQueryListener(this.eventData, observer, results, emptySearchView);
    }
}
