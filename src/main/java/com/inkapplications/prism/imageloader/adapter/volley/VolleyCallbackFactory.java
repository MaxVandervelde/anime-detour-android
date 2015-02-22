package com.inkapplications.prism.imageloader.adapter.volley;

import com.inkapplications.prism.imageloader.DrawableFactory;
import com.inkapplications.prism.imageloader.ImageCallback;

public class VolleyCallbackFactory
{
    final DrawableFactory drawableFactory;

    public VolleyCallbackFactory(DrawableFactory drawableFactory)
    {
        this.drawableFactory = drawableFactory;
    }

    public VolleyCallback create(ImageCallback callback)
    {
        return new VolleyCallback(callback);
    }
}
