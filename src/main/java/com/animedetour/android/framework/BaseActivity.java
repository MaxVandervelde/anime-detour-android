/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import icepick.Icepick;
import monolog.Monolog;
import prism.framework.PrismFacade;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity
{
    @Inject
    Monolog logger;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PrismFacade.bootstrap(this);
        Icepick.restoreInstanceState(this, savedInstanceState);
        ButterKnife.bind(this);
        this.logger.trace(this);
    }


    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        Icepick.saveInstanceState(this, bundle);
    }
}
