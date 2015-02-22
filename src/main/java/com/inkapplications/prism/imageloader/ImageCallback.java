package com.inkapplications.prism.imageloader;

import android.graphics.Bitmap;

public interface ImageCallback
{
    public void onLoad(Bitmap drawable, LoadType loadType);
    public void onError(Exception e);
}
