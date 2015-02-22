package com.inkapplications.prism.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

final public class BitmapDrawableFactory implements DrawableFactory
{
    final private Resources resources;

    public BitmapDrawableFactory(Resources resources)
    {
        this.resources = resources;
    }

    @Override
    public Drawable create(Bitmap bitmap)
    {
        return new BitmapDrawable(this.resources, bitmap);
    }
}
