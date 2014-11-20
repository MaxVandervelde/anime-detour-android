/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.main.schedule;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.framework.Fragment;
import com.animedetour.android.main.MainModule;
import org.joda.time.DateTime;
import prism.framework.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Schedule fragment
 *
 * This fragment displays a pager of days, one for each day in the schedule
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Scope(MainModule.class)
public class ScheduleFragment extends Fragment
{
    @InjectView(R.id.event_days)
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.schedule_pager, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        DaysPagerAdapter pagerAdapter = new DaysPagerAdapter(
            this.getActivity(),
            this.getChildFragmentManager(),
            this.getDays()
        );
        this.pager.setAdapter(pagerAdapter);
    }

    /**
     * Get a list of days, one for each day in the schedule.
     *
     * @todo query the database here instead of hardcoding the days
     * @return A list of days in the schedule
     */
    final protected List<DateTime> getDays()
    {
        ArrayList<DateTime> days = new ArrayList<DateTime>();
        days.add(new DateTime("2014-04-04"));
        days.add(new DateTime("2014-04-05"));
        days.add(new DateTime("2014-04-06"));

        return days;
    }
}
