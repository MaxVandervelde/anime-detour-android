package com.animedetour.android.landing.schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Adapter for the Schedule view pager
 *
 * Contains a list of days that the events span through and creates fragments
 * to be displayed for that day.
 *
 * @todo Change the days list to hold date objects instead of strings
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
class DaysPagerAdapter extends FragmentPagerAdapter
{
    /** List of days to display in the pager */
    private List<String> days;

    /**
     * @param days List of days to display in the pager
     */
    public DaysPagerAdapter(FragmentManager fm, List<String> days)
    {
        super(fm);
        this.days = days;
    }

    @Override
    public Fragment getItem(int i)
    {
        String day = this.days.get(i);
        return new DayFragment(day);
    }

    @Override
    public int getCount()
    {
        return this.days.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.days.get(position);
    }
}
