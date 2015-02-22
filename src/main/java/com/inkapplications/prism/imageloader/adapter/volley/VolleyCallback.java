package com.inkapplications.prism.imageloader.adapter.volley;

import android.graphics.Bitmap;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.inkapplications.prism.imageloader.ImageCallback;
import com.inkapplications.prism.imageloader.LoadType;

public class VolleyCallback implements ImageLoader.ImageListener
{
    final private ImageCallback callback;

    public VolleyCallback(ImageCallback callback)
    {
        this.callback = callback;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean isImmediate)
    {
        Bitmap source = imageContainer.getBitmap();

        if (null == source) {
            return;
        }
        LoadType loadType = isImmediate ? LoadType.MEMORY : LoadType.NETWORK;
        this.callback.onLoad(imageContainer.getBitmap(), loadType);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError)
    {
        this.callback.onError(volleyError);
    }
}
