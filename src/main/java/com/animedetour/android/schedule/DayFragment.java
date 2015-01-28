/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
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
import com.animedetour.android.database.EventRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.android.view.animator.SlideInLeftAnimator;
import com.animedetour.api.sched.api.model.Event;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.inkapplications.prism.widget.recyclerview.SimpleRecyclerView;
import com.inkapplications.prism.widget.recyclerview.ViewClickListener;
import icepick.Icicle;
import org.apache.commons.logging.Log;
import org.joda.time.DateTime;
import rx.Subscription;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

    @Inject
    Tracker tracker;

    @InjectView(R.id.panel_list)
    SimpleRecyclerView<PanelView, Event> panelList;

    @Icicle
    DateTime day;

    @Icicle
    int scrollPosition = 0;

    @InjectView(R.id.panel_empty_view)
    View panelEmptyView;

    private Subscription eventUpdateSubscription;

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
        HitBuilders.EventBuilder event = new HitBuilders.EventBuilder();
        event.setCategory("Event");
        event.setAction("View Details");
        event.setLabel(selected.getName());
        this.tracker.send(event.build());

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

        this.panelList.init(new ArrayList<Event>(), new EventViewBinder(this.getActivity(), this));
        this.panelList.setLayoutManager(layoutManager);
        this.panelList.setItemAnimator(new SlideInLeftAnimator(layoutManager));

        if (null == this.day) {
            return;
        }
        this.eventUpdateSubscription = this.eventData.findAllOnDay(
            this.day,
            new EventUpdateSubscriber(this, this.panelEmptyView, this.logger)
        );
    }

    /**
     * Update the list of events that is displayed.
     *
     * @param events The new event list to display.
     */
    public void updateEvents(List<Event> events)
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
