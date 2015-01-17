package com.animedetour.android.main;

import com.animedetour.android.guest.GuestIndexFragment;
import com.animedetour.android.home.HomeFragment;
import com.animedetour.android.schedule.DayFragment;
import com.animedetour.android.schedule.ScheduleFragment;
import dagger.Module;

@Module(
    injects = {
        MainActivity.class,

        HomeFragment.class,
        DayFragment.class,
        ScheduleFragment.class,
        GuestIndexFragment.class,
    },
    library = true,
    complete = false
)
final public class MainModule {}
