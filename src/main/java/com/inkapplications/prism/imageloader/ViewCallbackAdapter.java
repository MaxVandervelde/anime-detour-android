package com.inkapplications.prism.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;
import org.apache.commons.logging.Log;

public class ViewCallbackAdapter implements ImageCallback
{
    final private ImageView target;
    final private Log logger;

    public ViewCallbackAdapter(Log logger, ImageView target)
    {
        this.target = target;
        this.logger = logger;
    }

    @Override
    public void onLoad(Bitmap source, LoadType loadType)
    {
        this.target.setImageBitmap(source);
    }

    @Override
    public void onError(Exception e)
    {
        this.logger.error(e);
    }
}
