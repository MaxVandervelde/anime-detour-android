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
import android.view.inputmethod.InputMethodManager;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.android.model.Event;
import rx.Observer;

import java.util.List;

/**
 * Listens for updates to the search query and searches the event data based on
 * what has been entered by the user.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventQueryListener implements SearchView.OnQueryTextListener
{
    final private InputMethodManager inputManager;
    final private EventRepository eventRepository;
    final private Observer<List<Event>> resultObserver;
    final private View searchBar;
    final private View results;
    final private View emptySearchView;

    /**
     * @param eventRepository Service to perform the search query with.
     * @param resultObserver Observer to inform of search results matching the query.
     * @param results The view that shows search results.
     * @param emptySearchView A view to display when the search query is empty.
     */
    public EventQueryListener(
        InputMethodManager inputManager,
        EventRepository eventRepository,
        Observer<List<Event>> resultObserver,
        View searchBar,
        View results,
        View emptySearchView
    ) {
        this.inputManager = inputManager;
        this.eventRepository = eventRepository;
        this.resultObserver = resultObserver;
        this.searchBar = searchBar;
        this.results = results;
        this.emptySearchView = emptySearchView;
    }

    @Override
    public boolean onQueryTextSubmit(String userQuery)
    {
        this.inputManager.hideSoftInputFromWindow(this.searchBar.getWindowToken(), 0);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String userQuery)
    {
        this.toggleVisibility(userQuery);
        this.eventRepository.findMatching(userQuery, this.resultObserver);

        return true;
    }

    /**
     * Displays the search results or the empty search view depending on whether
     * the user query is empty.
     *
     * @param userQuery The text the user has entered in the search bar.
     */
    private void toggleVisibility(String userQuery)
    {
        if (userQuery.trim().equals("")) {
            this.emptySearchView.setVisibility(View.VISIBLE);
            this.results.setVisibility(View.GONE);
            return;
        }

        this.emptySearchView.setVisibility(View.GONE);
        this.results.setVisibility(View.VISIBLE);
    }
}
