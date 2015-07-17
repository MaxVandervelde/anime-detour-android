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
import com.animedetour.api.guest.model.Guest;
import monolog.Monolog;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Creates new instances of the Guest widget's controller at runtime.
 *
 * Since the widget controller requires a view, these must be created at
 * runtime. This class creates and injects dependencies into new instances of
 * the controllers.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class GuestControllerFactory
{
    final private Activity context;
    final private Monolog logger;

    @Inject
    public GuestControllerFactory(Activity context, Monolog logger)
    {
        this.context = context;
        this.logger = logger;
    }

    /**
     * Create a new controller instance to use with a widget view.
     */
    public GuestWidgetController create(Guest guest)
    {
        return new GuestWidgetController(this.context, this.logger, guest);
    }
}
