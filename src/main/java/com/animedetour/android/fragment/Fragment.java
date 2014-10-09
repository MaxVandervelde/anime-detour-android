/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.fragment;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.inkapplications.android.standard.fragment.BaseFragment;
import icepick.Icepick;

public class Fragment extends BaseFragment
{
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.inject(this, this.getView());
    }

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this, outState);
    }
}
