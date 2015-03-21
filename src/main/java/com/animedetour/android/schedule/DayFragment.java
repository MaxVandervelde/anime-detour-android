/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.android.main.SpinnerOptionContainer;
import com.animedetour.android.main.SubNavigationSelectionChange;
import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.android.widget.listview.ItemAdapter;
import com.inkapplications.groundcontrol.SubscriptionManager;
import com.squareup.otto.Subscribe;
import icepick.Icicle;
import org.joda.time.DateTime;
import rx.Subscription;

import javax.inject.Inject;

/**
 * Day schedule fragment
 *
 * This fragment displays a list of the panels / events for a single day.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class DayFragment extends Fragment
{
    @Inject
    EventRepository eventData;

    @Inject
    SubscriptionManager subscriptionManager;

    @Inject
    EventSubscriberFactory subscriberFactory;

    @Inject
    EventViewBinder viewBinder;

    @InjectView(R.id.panel_list)
    ListView panelList;

    @InjectView(R.id.panel_empty_view)
    View panelEmptyView;

    @Icicle
    DateTime day;

    @Icicle
    int scrollPosition = 0;

    private EventUpdateSubscriber eventUpdateSubscriber;

    public DayFragment() {}

    public DayFragment(DateTime day)
    {
        this.day = day;
    }

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

        ItemAdapter<PanelView, Event> adapter = new ItemAdapter<>(this.viewBinder);
        this.panelList.setAdapter(adapter);
        this.eventUpdateSubscriber = this.subscriberFactory.create(this.panelList, adapter, this.panelEmptyView);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        this.initializeFilter();
        this.updateEvents();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.subscriptionManager.unsubscribeAll();
        this.scrollPosition = this.eventUpdateSubscriber.getScrollPosition();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        this.eventUpdateSubscriber.setScrollPosition(this.scrollPosition);
    }

    @Subscribe
    public void onFilterChange(SubNavigationSelectionChange event)
    {
        this.eventUpdateSubscriber.displayFiltered(event.getSelection());
    }

    /**
     * Set up the events to be filtered on a type that is specified by the
     * spinner selection in the main navigation.
     *
     * Assuming the attached activity has a spinner, this changes the displayed
     * filter to whatever is selected. It is intended to be called during the
     * start of the fragment so that upon initialization, rotation, or resume
     * of the activity, these filters remain in-tact.
     *
     * Since the selection might change while this fragment is paused, the
     * selection cannot be stored here. So, we store it in the parent activity
     * under a container interface.
     *
     * @todo Look into refactoring this so that we don't have to check/cast the activity.
     */
    private void initializeFilter()
    {
        if (this.getActivity() instanceof SpinnerOptionContainer) {
            SpinnerOptionContainer container = (SpinnerOptionContainer) this.getActivity();
            this.eventUpdateSubscriber.displayFiltered(container.getSpinnerSelection());
        }
    }

    /**
     * Fetch a new set of data to display in the list of events based on the
     * bound date of the fragment.
     */
    protected void updateEvents()
    {
        Subscription eventSubscription = this.eventData.findAllOnDay(this.day, this.eventUpdateSubscriber);
        this.subscriptionManager.add(eventSubscription);
    }
}
