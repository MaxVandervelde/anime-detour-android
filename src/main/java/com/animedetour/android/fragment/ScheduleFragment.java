/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.activity.EventActivity;
import com.animedetour.android.adapter.PanelListAdapter;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.sched.api.model.Event;
import icepick.Icicle;
import org.apache.commons.logging.Log;
import org.joda.time.DateTime;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

/**
 * Schedule fragment
 *
 * This fragment displays a list of the panels / events.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ScheduleFragment extends Fragment
{
    @Inject
    EventRepository eventData;

    @Inject
    Log logger;

    @InjectView(R.id.panel_list)
    ListView panelList;

    @Icicle
    int scrollPosition = 0;

    private PanelListAdapter panelListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.schedule, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.panelListAdapter = new PanelListAdapter(this.getActivity());
        this.panelList.setAdapter(this.panelListAdapter);
        this.panelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event selected = ScheduleFragment.this.panelListAdapter.getItem(i);
                ScheduleFragment.this.selectEvent(selected);
            }
        });

        Observable<List<Event>> eventsRequest = this.eventData.findAllOnDay(new DateTime("2014-04-04"));
        eventsRequest.subscribe(new Subscriber<List<Event>>() {
            @Override public void onCompleted() { }
            @Override public void onError(Throwable e) {
                ScheduleFragment.this.logger.error("Error fetching schedule", e);
            }
            @Override public void onNext(List<Event> events) {
                ScheduleFragment.this.updateEvents(events);
            }
        });
    }

    public void selectEvent(Event selected)
    {
        Intent intent = new Intent(getActivity(), EventActivity.class);
        intent.putExtra(EventActivity.EXTRA_EVENT, selected);
        startActivity(intent);
    }

    public void updateEvents(List<Event> events)
    {
        if (this.panelListAdapter.getCount() != 0) {
            this.syncScrollPosition();
        }
        this.panelListAdapter.setEvents(events);
        this.panelList.setSelectionFromTop(this.scrollPosition, 0);
    }

    public void syncScrollPosition()
    {
        this.scrollPosition = this.panelList.getFirstVisiblePosition();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        this.syncScrollPosition();
        super.onSaveInstanceState(outState);
    }
}
