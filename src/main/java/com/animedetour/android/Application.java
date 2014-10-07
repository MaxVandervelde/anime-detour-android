/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android;

import com.inkapplications.android.standard.BaseApplication;
import com.animedetour.android.dependencyinjection.module.ApplicationModule;

import java.util.List;

public class Application extends BaseApplication
{
    @Override
    public List<Object> getModules()
    {
        List<Object> modules = super.getModules();

        modules.add(new ApplicationModule());

        return modules;
    }
}
