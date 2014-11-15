package com.animedetour.android.landing.schedule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.animedetour.android.R;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

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
class DaysPagerAdapter extends FragmentPagerAdapter
{
    /** List of days to display in the pager */
    private List<DateTime> days;

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
        String format = this.context.getString(R.string.schedule_day_headline);
        Date day = this.days.get(position).toDate();

        return String.format(format, day);
    }
}
