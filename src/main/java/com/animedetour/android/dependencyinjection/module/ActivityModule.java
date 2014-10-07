/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.dependencyinjection.module;

import com.animedetour.android.fragment.LandingFragment;
import com.inkapplications.android.standard.dependencyinjection.module.StandardActivityModule;
import com.animedetour.android.activity.MainActivity;
import dagger.Module;

@Module(
    includes = {

    },
    injects = {
        MainActivity.class,

        LandingFragment.class,
    },
    addsTo = StandardActivityModule.class,
    complete = false,
    library = true
)
public class ActivityModule
{

}
