/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.activity;

import com.inkapplications.android.standard.activity.BaseActivity;
import com.animedetour.android.dependencyinjection.module.ActivityModule;

import java.util.List;

public class Activity extends BaseActivity
{
    @Override
    public List<Object> getModules()
    {
        List<Object> modules = super.getModules();

        modules.add(new ActivityModule());

        return modules;
    }
}
