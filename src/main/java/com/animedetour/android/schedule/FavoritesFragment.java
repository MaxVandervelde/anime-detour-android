/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.analytics.EventFactory;
import com.animedetour.android.database.favorite.FavoriteRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.android.view.animator.SlideInLeftAnimator;
import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.prism.analytics.ScreenName;
import com.inkapplications.android.widget.recyclerview.SimpleRecyclerView;
import com.inkapplications.android.widget.recyclerview.ViewClickListener;
import icepick.Icicle;
import org.apache.commons.logging.Log;
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
@ScreenName("Favorites")
final public class FavoritesFragment extends Fragment implements ViewClickListener<PanelView, Event>
{
    @Inject
    FavoriteRepository favoriteData;

    @Inject
    Log logger;

    @InjectView(R.id.panel_list)
    SimpleRecyclerView<PanelView, Favorite> panelList;

    @Icicle
    int scrollPosition = 0;

    @InjectView(R.id.panel_empty_view)
    View panelEmptyView;

    private Subscription eventUpdateSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.schedule_day, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.setupPanelList();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if (null != this.eventUpdateSubscription) {
            this.eventUpdateSubscription.unsubscribe();
        }
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

        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtra(EventActivity.EXTRA_EVENT, selected);
        this.startActivity(intent);
    }

    /**
     * Setup the panel list view handlers and data request/subscriptions.
     */
    protected void setupPanelList()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        EventViewBinder eventViewBinder = new EventViewBinder(this.getActivity(), this);
        this.panelList.init(new ArrayList<Favorite>(), new FavoriteViewBinder(eventViewBinder));
        this.panelList.setLayoutManager(layoutManager);
        this.panelList.setItemAnimator(new SlideInLeftAnimator(layoutManager));

        this.eventUpdateSubscription = this.favoriteData.findAll(
            new FavoriteUpdateSubscriber(this, this.panelEmptyView, this.logger)
        );
    }

    /**
     * Update the list of events that is displayed.
     *
     * @param events The new event list to display.
     */
    public void updateEvents(List<Favorite> events)
    {
        if (this.panelList.getAdapter().getItemCount() != 0) {
            this.syncScrollPosition();
        }

        this.panelList.getItemAdapter().setItems(events);
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
