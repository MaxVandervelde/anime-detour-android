/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.serach;

import android.view.View;
import com.inkapplications.android.widget.listview.ItemAdapter;
import monolog.Monolog;
import rx.Observer;

import java.util.List;

/**
 * Listens for updates to the Event types and updates an adapter used for
 * filtering events by that type when new data is received.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventTypeObserver implements Observer<List<String>>
{
    final private Monolog logger;
    final private View emptyResultsView;
    final private ItemAdapter<FilterItemView, String> filterAdapter;

    public EventTypeObserver(
        Monolog logger,
        View emptyResultsView,
        ItemAdapter<FilterItemView, String> filterAdapter
    ) {
        this.logger = logger;
        this.emptyResultsView = emptyResultsView;
        this.filterAdapter = filterAdapter;
    }

    @Override public void onCompleted() {}

    @Override
    public void onError(Throwable e)
    {
        this.logger.error(e);
    }

    @Override
    public void onNext(List<String> strings)
    {
        this.emptyResultsView.setVisibility(View.GONE);
        this.filterAdapter.setItems(strings);
    }
}
