/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.animedetour.android.analytics.EventFactory;
import com.animedetour.api.guest.model.Guest;
import org.apache.commons.logging.Log;

/**
 * Handles events on the guest widgets that are displayed in the index.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class GuestWidgetController implements View.OnClickListener
{
    final private Activity context;
    final private Log logger;

    /**
     * Guest model that's bound to the widget view.
     */
    final private Guest guest;

    /**
     * @param guest Guest model that's bound to the widget view.
     */
    public GuestWidgetController(Activity context, Log logger, Guest guest)
    {
        this.context = context;
        this.logger = logger;
        this.guest = guest;
    }

    @Override
    public void onClick(View view)
    {
        this.logger.trace(EventFactory.guestDetails(this.guest));
        Intent intent = GuestDetailActivity.createIntent(this.context, this.guest);
        this.context.startActivity(intent);
    }
}
