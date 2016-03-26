/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015-2016 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.home;

import android.view.View;
import com.animedetour.android.model.Event;
import com.animedetour.android.view.scrim.ImageScrim;
import monolog.Monolog;
import rx.Observer;

import java.util.List;

/**
 * Updates the featured event view when new data about events is received.
 *
 * This will hide the view if a null event is received, otherwise it is
 * responsible for updating the contents of the view to match including
 * the image and the title text.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final class FeaturedUpdater implements Observer<List<Event>>
{
    final private Monolog logger;
    final private FeaturedControllerFactory controllerFactory;
    final private ImageScrim preview;
    final private ImageScrim preview2;

    public FeaturedUpdater(
        Monolog logger,
        FeaturedControllerFactory controllerFactory,
        ImageScrim preview,
        ImageScrim preview2
    ) {
        this.logger = logger;
        this.controllerFactory = controllerFactory;
        this.preview = preview;
        this.preview2 = preview2;
    }

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e)
    {
        this.logger.error(e);
    }

    @Override
    public void onNext(List<Event> events)
    {
        Event event = events.size() >= 1 ? events.get(0) : null;
        Event event2 = events.size() >= 2 ? events.get(1) : null;

        this.bind(event, this.preview);
        this.bind(event2, this.preview2);
    }

    private void bind(Event event, ImageScrim preview)
    {
        if (null == event) {
            preview.setVisibility(View.GONE);
            return;
        }

        FeaturedController controller = this.controllerFactory.create(event);

        preview.setVisibility(View.VISIBLE);
        preview.setTitle(event.getName());
        preview.setOnClickListener(controller);

        if (null == event.getBanner()) {
            return;
        }

        preview.setImage(null);
        preview.expandImage();
        preview.setImage(event.getBanner());
    }
}
