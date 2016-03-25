/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014,2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.animedetour.android.R;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Adapter for the Schedule view pager
 *
 * Contains a list of days that the events span through and creates fragments
 * to be displayed for that day.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class DaysPagerAdapter extends FragmentPagerAdapter
{
    /** List of days to display in the pager */
    private List<DateTime> days;

    /** List of fragments managed by this adapter so far. */
    private List<DayFragment> fragments = new ArrayList<>();

    private Context context;

    /**
     * @param days List of days to display in the pager
     */
    public DaysPagerAdapter(Context context, FragmentManager fm, List<DateTime> days)
    {
        super(fm);
        this.days = days;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i)
    {
        DateTime day = this.days.get(i);
        DayFragment dayFragment = new DayFragment(day);
        this.fragments.add(dayFragment);

        return dayFragment;
    }

    @Override
    public int getCount()
    {
        return this.days.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String format = this.context.getString(R.string.schedule_day_headline);
        Date day = this.days.get(position).toDate();

        return String.format(format, day);
    }

    /**
     * @return A list of fragments currently managed by this adapter so far.
     */
    public List<DayFragment> getFragments()
    {
        return fragments;
    }
}
