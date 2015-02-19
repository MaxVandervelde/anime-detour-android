/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.analytics.EventFactory;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.android.widget.recyclerview.SimpleRecyclerView;
import com.inkapplications.android.widget.recyclerview.ViewClickListener;
import com.inkapplications.groundcontrol.SubscriptionManager;
import icepick.Icicle;
import org.apache.commons.logging.Log;
import org.joda.time.DateTime;
import rx.Subscription;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Day schedule fragment
 *
 * This fragment displays a list of the panels / events for a single day.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class DayFragment extends Fragment implements ViewClickListener<PanelView, Event>
{
    @Inject
    EventRepository eventData;

    @Inject
    Log logger;

    @InjectView(R.id.panel_list)
    SimpleRecyclerView<PanelView, Event> panelList;

    @Icicle
    DateTime day;

    @Icicle
    int scrollPosition = 0;

    @InjectView(R.id.panel_empty_view)
    View panelEmptyView;

    @Inject
    SubscriptionManager subscriptionManager;

    @Inject
    EventSubscriberFactory subscriberFactory;

    private EventUpdateSubscriber eventUpdateSubscriber;

    public DayFragment()
    {
        super();
    }

    public DayFragment(DateTime day)
    {
        super();

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

        this.panelList.init(
            new ArrayList<Event>(),
            new EventViewBinder(this.getActivity(), this)
        );
        this.eventUpdateSubscriber = this.subscriberFactory.create(this.panelList, this.panelEmptyView);
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
        this.scrollPosition = this.eventUpdateSubscriber.getScrollPosition();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        this.eventUpdateSubscriber.setScrollPosition(this.scrollPosition);
    }

    @Override
    public void onViewClicked(Event selected, PanelView view)
    {
        this.logger.trace(EventFactory.eventDetails(selected));

        Intent intent = EventActivity.createIntent(this.getActivity(), selected);
        this.startActivity(intent);
    }

    protected void updateEvents()
    {
        Subscription eventSubscription = this.eventData.findAllOnDay(this.day, this.eventUpdateSubscriber);
        this.subscriptionManager.add(eventSubscription);
    }
}
