package com.animedetour.android.home;

import android.view.View;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.animedetour.android.view.ImageScrim;
import com.animedetour.api.sched.api.model.Event;
import org.apache.commons.logging.Log;
import rx.Observer;

final class FeaturedEventUpdater implements Observer<Event>
{
    final private Log logger;
    final private ImageScrim preview;
    final private ImageLoader imageLoader;

    public FeaturedEventUpdater(Log logger, ImageLoader imageLoader, ImageScrim preview)
    {
        this.logger = logger;
        this.preview = preview;
        this.imageLoader = imageLoader;
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

        this.preview.setVisibility(View.VISIBLE);
        this.preview.setTitle(event.getName());

        if (null == event.getMediaUrl()) {
            return;
        }

        this.preview.setImage(null);
        this.preview.expandImage();
        this.imageLoader.get(
            event.getMediaUrl(),
            new ImageLoader.ImageListener() {
                @Override public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    preview.setImage(imageContainer.getBitmap());
                }
                @Override public void onErrorResponse(VolleyError volleyError) {
                    logger.error("Error loading featured image", volleyError);
                }
            }
        );
    }
}
