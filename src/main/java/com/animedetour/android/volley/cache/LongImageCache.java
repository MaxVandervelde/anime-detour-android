/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.volley.cache;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Long term image cache
 *
 * This should be adjusted to store images long-term for the app
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class LongImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache
{
    public LongImageCache(int sizeInKiloBytes)
    {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value)
    {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url)
    {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        this.put(url, bitmap);
    }
}
