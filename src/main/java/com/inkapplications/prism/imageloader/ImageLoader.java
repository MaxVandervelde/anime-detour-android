package com.inkapplications.prism.imageloader;

import android.widget.ImageView;

public interface ImageLoader
{
    public void load(String source, ImageView target);
    public void load(String source, ImageCallback callback);
}
