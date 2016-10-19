/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.home;

import android.content.Intent;
import android.net.Uri;
import butterknife.OnClick;
import com.animedetour.android.R;
import com.animedetour.android.analytics.EventFactory;
import com.animedetour.android.framework.BaseFragment;
import monolog.LogName;
import monolog.Monolog;
import prism.framework.DisplayName;
import prism.framework.Layout;

import javax.inject.Inject;

/**
 * The off-season Landing / Home fragment.
 *
 * This fragment is displayed by default when you open the app ONLY IF:
 * the previous convention is over, AND the next year's schedule is not yet
 * available.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@DisplayName(R.string.home_title)
@LogName("Home")
@Layout(R.layout.landing_off_season)
final public class OffSeasonHomeFragment extends BaseFragment
{
    @Inject
    Monolog logger;

    @OnClick(R.id.register_next_year)
    public void registerAction()
    {
        this.logger.trace(EventFactory.registerClick());

        String url = "http://animedetour.com/register";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));
        startActivity(browserIntent);
    }

    @OnClick(R.id.more_events)
    public void moreEventsAction()
    {
        this.logger.trace(EventFactory.moreEventsClick());

        String url = "http://animetwincities.org/sos";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);

        browserIntent.setData(Uri.parse(url));
        startActivity(browserIntent);
    }
}
