/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.InjectView;
import com.animedetour.android.R;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.android.framework.Fragment;
import com.animedetour.android.view.scrim.ImageScrim;
import com.inkapplications.groundcontrol.SubscriptionManager;
import com.inkapplications.prism.analytics.ScreenName;
import org.apache.commons.logging.Log;
import rx.Subscription;

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
    EventRepository eventData;

    @Inject
    Log logger;

    @InjectView(R.id.event_banner)
    ImageScrim scrim;

    @InjectView(R.id.event_banner2)
    ImageScrim scrim2;

    @Inject
    SubscriptionManager subscriptionManager;

    @Inject
    FeaturedUpdaterFactory updaterFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.landing, container, false);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        this.loadBannerData(this.scrim, 1);
        this.loadBannerData(this.scrim2, 2);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        this.subscriptionManager.unsubscribeAll();
    }

    /**
     * Lookup a featured event and load the data into a specified banner.
     */
    private void loadBannerData(ImageScrim banner, int ordinal)
    {
        FeaturedUpdater updater = this.updaterFactory.create(banner);
        Subscription subscription = this.eventData.findFeatured(updater, ordinal);
        this.subscriptionManager.add(subscription);
    }
}
