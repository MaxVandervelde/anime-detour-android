package com.inkapplications.prism.imageloader.adapter.volley;

import android.content.res.Resources;
import android.widget.ImageView;
import com.inkapplications.prism.imageloader.BitmapDrawableFactory;
import com.inkapplications.prism.imageloader.ImageCallback;
import com.inkapplications.prism.imageloader.ImageLoader;
import com.inkapplications.prism.imageloader.ViewCallbackFactory;
import org.apache.commons.logging.Log;

public class VolleyLoader implements ImageLoader
{
    final private com.android.volley.toolbox.ImageLoader volleyLoader;
    final private VolleyCallbackFactory volleyCallbackFactory;
    final private ViewCallbackFactory viewCallbackFactory;

    public VolleyLoader(
        com.android.volley.toolbox.ImageLoader volleyLoader,
        VolleyCallbackFactory volleyCallbackFactory,
        ViewCallbackFactory viewCallbackFactory
    ) {
        this.volleyLoader = volleyLoader;
        this.volleyCallbackFactory = volleyCallbackFactory;
        this.viewCallbackFactory = viewCallbackFactory;
    }

    public VolleyLoader(
        com.android.volley.toolbox.ImageLoader volleyLoader,
        Resources resources,
        Log logger
    ) {
        this.volleyLoader = volleyLoader;
        BitmapDrawableFactory drawableFactory = new BitmapDrawableFactory(resources);
        this.volleyCallbackFactory = new VolleyCallbackFactory(drawableFactory);
        this.viewCallbackFactory = new ViewCallbackFactory(logger);
    }

    @Override
    public void load(String source, ImageView target)
    {
        ImageCallback imageCallback = this.viewCallbackFactory.create(target);

        this.load(source, imageCallback);
    }

    @Override
    public void load(String source, ImageCallback callback)
    {
        VolleyCallback volleyCallback = this.volleyCallbackFactory.create(callback);
        this.volleyLoader.get(source, volleyCallback);
    }
}
