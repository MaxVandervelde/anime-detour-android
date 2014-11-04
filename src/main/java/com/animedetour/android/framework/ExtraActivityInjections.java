/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import butterknife.ButterKnife;
import icepick.Icepick;

public class ExtraActivityInjections implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle)
    {
        Icepick.restoreInstanceState(this, bundle);
        ButterKnife.inject(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle)
    {
        Icepick.saveInstanceState(this, bundle);
    }

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}
}
