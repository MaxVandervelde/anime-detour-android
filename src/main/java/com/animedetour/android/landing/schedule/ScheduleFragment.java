/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.landing.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.event.EventActivity;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.android.view.animator.SlideInLeftAnimator;
import com.animedetour.sched.api.model.Event;
import com.inkapplications.prism.widget.recyclerview.SimpleRecyclerView;
import com.inkapplications.prism.widget.recyclerview.ViewClickListener;
import icepick.Icicle;
import org.apache.commons.logging.Log;
import org.joda.time.DateTime;
import rx.Observable;
import rx.Subscription;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Schedule fragment
 *
 * This fragment displays a list of the panels / events.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ScheduleFragment extends Fragment implements ViewClickListener<PanelView, Event>
{
    @Inject
    EventRepository eventData;

    @Inject
    Log logger;

    @InjectView(R.id.panel_list)
    SimpleRecyclerView<PanelView, Event> panelList;

    @Icicle
    int scrollPosition = 0;

    @InjectView(R.id.panel_empty_view)
    View panelEmptyView;

    private Subscription eventUpdateSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        this.panelList.init(new ArrayList<Event>(), new EventViewBinder(this.getActivity(), this));
        this.panelList.setLayoutManager(layoutManager);
        this.panelList.setItemAnimator(new SlideInLeftAnimator(layoutManager));

        Observable<List<Event>> eventsRequest = this.eventData.findAllOnDay(new DateTime("2014-04-04"));
        this.eventUpdateSubscription = eventsRequest.subscribe(new EventUpdateSubscriber(this, this.panelEmptyView, this.logger));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.eventUpdateSubscription.unsubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        this.syncScrollPosition();
        super.onSaveInstanceState(outState);
    }

    public void updateEvents(List<Event> events)
    {
        if (this.panelList.getAdapter().getItemCount() != 0) {
            this.syncScrollPosition();
            this.panelList.getItemAdapter().setItems(events);
        } else {
            this.panelList.getItemAdapter().addItems(events);
        }

        this.panelList.setVerticalScrollbarPosition(this.scrollPosition);
    }

    public void syncScrollPosition()
    {
        this.scrollPosition = this.panelList.getVerticalScrollbarPosition();
    }

    @Override
    public void onViewClicked(Event selected, PanelView view)
    {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtra(EventActivity.EXTRA_EVENT, selected);
        startActivity(intent);
    }
}
