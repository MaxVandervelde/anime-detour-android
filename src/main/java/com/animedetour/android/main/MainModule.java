package com.animedetour.android.main;

import com.animedetour.android.main.home.HomeFragment;
import com.animedetour.android.main.schedule.DayFragment;
import com.animedetour.android.main.schedule.ScheduleFragment;
import dagger.Module;

@Module(
    injects = {
        MainActivity.class,

        HomeFragment.class,
        DayFragment.class,
        ScheduleFragment.class,
    },
    library = true,
    complete = false
)
public class MainModule {}
