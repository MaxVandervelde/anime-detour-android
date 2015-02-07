/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import com.android.volley.toolbox.ImageLoader;
import com.animedetour.android.R;
import com.animedetour.android.database.EventRepository;
import com.animedetour.android.framework.Fragment;
import com.inkapplications.prism.analytics.ScreenName;
import com.animedetour.android.view.ImageScrim;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import org.apache.commons.logging.Log;

import javax.inject.Inject;

/**
 * Landing / Home fragment.
 *
 * This fragment is displayed by default when you open the app.
 * It displays a few upcoming events to the user.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@ScreenName("Home")
final public class HomeFragment extends Fragment
{
    @Inject
    Tracker tracker;

    @Inject
    EventRepository eventData;

    @Inject
    ImageLoader imageLoader;

    @Inject
    Log logger;

    @InjectView(R.id.event_banner)
    ImageScrim scrim;

    @InjectView(R.id.event_banner2)
    ImageScrim scrim2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.landing, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.tracker.setScreenName("Home");
        this.tracker.send(new HitBuilders.AppViewBuilder().build());
        this.eventData.findFeatured(
            new FeaturedEventUpdater(this.logger, this.imageLoader, this.scrim),
            1
        );
        this.eventData.findFeatured(
            new FeaturedEventUpdater(this.logger, this.imageLoader, this.scrim2),
            2
        );
    }
}
