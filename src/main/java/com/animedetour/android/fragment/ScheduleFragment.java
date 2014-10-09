/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.adapter.PanelListAdapter;
import com.animedetour.sched.api.ScheduleEndpoint;
import com.animedetour.sched.api.model.Day;
import com.animedetour.sched.api.model.Hour;
import com.animedetour.sched.api.model.Panel;
import com.animedetour.sched.api.model.Schedule;
import icepick.Icicle;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Schedule fragment
 *
 * This fragment displays a list of the panels / events.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ScheduleFragment extends Fragment implements Callback<Schedule>
{
    @Inject
    ScheduleEndpoint endpoint;

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

        this.endpoint.getSchedule(this);
    }

    @Override
    public void success(Schedule schedule, Response response)
    {
        ArrayList<Panel> panels = new ArrayList<Panel>();

        for (Day day : schedule.getDays()) {
            for (Hour hour : day.getHours()) {
                panels.addAll(hour.getPanels());
            }
        }

        this.panelListAdapter.setPanels(panels);
        this.panelList.setSelectionFromTop(this.scrollPosition, 0);
    }

    @Override
    public void failure(RetrofitError error)
    {
        Log.e("schedule", error.getMessage());
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        this.scrollPosition = this.panelList.getFirstVisiblePosition();
        super.onSaveInstanceState(outState);
    }
}
