/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.animedetour.android.R;
import com.animedetour.android.framework.Fragment;
import com.animedetour.android.main.MainModule;
import prism.framework.Scope;

/**
 * Landing / Home fragment.
 *
 * This fragment is displayed by default when you open the app.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Scope(MainModule.class)
final public class HomeFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.landing, container, false);

        return view;
    }
}
