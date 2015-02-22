/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.home;

import android.view.View;
import com.animedetour.android.view.scrim.ImageScrim;
import com.animedetour.android.view.scrim.ScrimLoader;
import com.animedetour.api.sched.api.model.Event;
import org.apache.commons.logging.Log;
import rx.Observer;

/**
 * Updates the featured event view when new data about events is received.
 *
 * This will hide the view if a null event is received, otherwise it is
 * responsible for updating the contents of the view to match including
 * the image and the title text.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final class FeaturedUpdater implements Observer<Event>
{
    final private Log logger;
    final private FeaturedControllerFactory controllerFactory;
    final private ImageScrim preview;
    final private ScrimLoader scrimLoader;

    public FeaturedUpdater(
        Log logger,
        FeaturedControllerFactory controllerFactory,
        ScrimLoader scrimLoader,
        ImageScrim preview
    ) {
        this.logger = logger;
        this.controllerFactory = controllerFactory;
        this.preview = preview;
        this.scrimLoader = scrimLoader;
    }

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e)
    {
        this.logger.error(e);
    }

    @Override
    public void onNext(Event event)
    {
        if (null == event) {
            this.preview.setVisibility(View.GONE);
            return;
        }

        FeaturedController controller = this.controllerFactory.create(this.preview, event);

        this.preview.setVisibility(View.VISIBLE);
        this.preview.setTitle(event.getName());
        this.preview.setOnClickListener(controller);

        if (null == event.getMediaUrl()) {
            return;
        }

        this.preview.setImage(null);
        this.preview.expandImage();
        this.scrimLoader.loadImage(this.preview, event.getMediaUrl());
    }
}
