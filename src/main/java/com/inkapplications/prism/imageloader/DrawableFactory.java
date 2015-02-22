package com.inkapplications.prism.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface DrawableFactory
{
    public Drawable create(Bitmap bitmap);
}
