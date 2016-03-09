/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import butterknife.Bind;
import com.animedetour.android.R;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.android.framework.BaseFragment;
import com.animedetour.android.model.Event;
import com.animedetour.android.schedule.serach.EventSearchActivity;
import com.inkapplications.android.widget.listview.ItemAdapter;
import com.inkapplications.groundcontrol.SubscriptionManager;
import icepick.State;
import org.joda.time.DateTime;
import prism.framework.Layout;
import rx.Subscription;

import javax.inject.Inject;

/**
 * Day schedule fragment
 *
 * This fragment displays a list of the panels / events for a single day.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Layout(R.layout.schedule_day)
final public class DayFragment extends BaseFragment
{
    @Inject
    EventRepository eventData;

    @Inject
    SubscriptionManager subscriptionManager;

    @Inject
    EventObserverFactory subscriberFactory;

    @Inject
    EventViewBinder viewBinder;

    @Bind(R.id.panel_list)
    ListView panelList;

    @Bind(R.id.panel_empty_view)
    View panelEmptyView;

    @State
    DateTime day;

    @State
    int scrollPosition = 0;

    private EventUpdateObserver eventUpdateObserver;

    public DayFragment() {}

    public DayFragment(DateTime day)
    {
        this.day = day;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ItemAdapter<PanelView, Event> adapter = new ItemAdapter<>(this.viewBinder);
        this.panelList.setAdapter(adapter);
        this.eventUpdateObserver = this.subscriberFactory.create(this.panelList, adapter, this.panelEmptyView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.event_actions, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.updateEvents();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.subscriptionManager.unsubscribeAll();
        this.scrollPosition = this.eventUpdateObserver.getScrollPosition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.event_actions_search:
                this.startActivity(new Intent(getActivity(), EventSearchActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        this.eventUpdateObserver.setScrollPosition(this.scrollPosition);
    }

    /**
     * Fetch a new set of data to display in the list of events based on the
     * bound date of the fragment.
     */
    protected void updateEvents()
    {
        Subscription eventSubscription = this.eventData.findAllOnDay(this.day, this.eventUpdateObserver);
        this.subscriptionManager.add(eventSubscription);
    }
}
