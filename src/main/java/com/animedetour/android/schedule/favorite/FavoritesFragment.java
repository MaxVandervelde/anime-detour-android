/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.Bind;
import com.animedetour.android.R;
import com.animedetour.android.analytics.EventFactory;
import com.animedetour.android.database.favorite.FavoriteRepository;
import com.animedetour.android.framework.BaseFragment;
import com.animedetour.android.schedule.EventActivity;
import com.animedetour.android.schedule.EventPalette;
import com.animedetour.android.schedule.EventViewBinder;
import com.animedetour.android.schedule.PanelView;
import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.android.widget.listview.ItemAdapter;
import com.inkapplications.android.widget.recyclerview.ViewClickListener;
import com.inkapplications.groundcontrol.SubscriptionManager;
import icepick.Icicle;
import monolog.LogName;
import monolog.Monolog;
import prism.framework.DisplayName;
import rx.Subscription;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * List of "My Events" that have been favorited/starred.
 *
 * @todo We can probably extract some logic between this and the DayFragment.
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@DisplayName(R.string.favorites_title)
@LogName("Favorites")
final public class FavoritesFragment extends BaseFragment implements ViewClickListener<PanelView, Event>
{
    @Inject
    FavoriteRepository favoriteData;

    @Inject
    Monolog logger;

    @Bind(R.id.panel_list)
    ListView panelList;

    @Icicle
    int scrollPosition = 0;

    @Bind(R.id.panel_empty_view)
    View panelEmptyView;

    @Inject
    SubscriptionManager subscriptionManager;

    @Inject
    EventPalette palette;

    private ItemAdapter<PanelView, Favorite> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.schedule_day, container, false);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        this.setupPanelList();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.subscriptionManager.unsubscribeAll();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        this.syncScrollPosition();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewClicked(Event selected, PanelView view)
    {
        this.logger.trace(EventFactory.eventDetails(selected));

        Intent intent = EventActivity.createIntent(this.getActivity(), selected);
        this.startActivity(intent);
    }

    /**
     * Setup the panel list view handlers and data request/subscriptions.
     */
    protected void setupPanelList()
    {
        EventViewBinder eventViewBinder = new EventViewBinder(this.getActivity(), this.palette, this);
        FavoriteViewBinder favoriteViewBinder = new FavoriteViewBinder(eventViewBinder);
        this.adapter = new ItemAdapter<>(favoriteViewBinder);
        this.panelList.setAdapter(adapter);

        Subscription favoriteSubscription = this.favoriteData.findAll(
            new FavoriteUpdateObserver(this, this.panelEmptyView, this.logger)
        );
        this.subscriptionManager.add(favoriteSubscription);
    }

    /**
     * Update the list of events that is displayed.
     *
     * @param events The new event list to display.
     */
    public void updateEvents(List<Favorite> events)
    {
        List<Favorite> filtered =  new ArrayList<>();
        for (Favorite favorite : events) {
            if (null == favorite.getEvent()) {
                continue;
            }
            filtered.add(favorite);
        }

        if (this.panelList.getAdapter().getCount() != 0) {
            this.syncScrollPosition();
        }

        this.adapter.setItems(filtered);
        this.panelList.setVerticalScrollbarPosition(this.scrollPosition);
    }

    /**
     * Save the scroll position to memory so that it may be recalled later.
     */
    public void syncScrollPosition()
    {
        if (null == this.panelList) {
            this.scrollPosition = 0;
            return;
        }
        this.scrollPosition = this.panelList.getVerticalScrollbarPosition();
    }
}
