package com.inkapplications.prism.imageloader;

import android.widget.ImageView;
import org.apache.commons.logging.Log;

public class ViewCallbackFactory
{
    final private Log logger;

    public ViewCallbackFactory(Log logger)
    {
        this.logger = logger;
    }

    public ImageCallback create(ImageView target)
    {
        return new ViewCallbackAdapter(this.logger, target);
    }
}
