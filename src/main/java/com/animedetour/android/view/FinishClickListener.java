/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view;

import android.app.Activity;
import android.view.View;

/**
 * Simple Click listener to finish the activity when something is clicked.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class FinishClickListener implements View.OnClickListener
{
    final Activity activity;

    /**
     * @param activity Activity to be finished when the item is clicked.
     */
    public FinishClickListener(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onClick(View view)
    {
        this.activity.finish();
    }
}
