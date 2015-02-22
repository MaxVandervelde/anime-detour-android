/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

/**
 * A transition drawable that fades from transparent to
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class FadeInDrawable extends TransitionDrawable
{
    public FadeInDrawable(Drawable source)
    {
        super(createComposite(source));
    }

    public FadeInDrawable(Resources resources, Bitmap source)
    {
        super(createComposite(resources, source));
    }

    protected static Drawable[] createComposite(Drawable source)
    {
        return new Drawable[] {
            getInitialDrawable(),
            source
        };
    }

    protected static Drawable[] createComposite(Resources resources, Bitmap source)
    {
        BitmapDrawable sourceDrawable = new BitmapDrawable(resources, source);
        return FadeInDrawable.createComposite(sourceDrawable);
    }

    private static Drawable getInitialDrawable()
    {
        return new ColorDrawable(Color.TRANSPARENT);
    }

    public void startDefaultTransition()
    {
        this.startTransition(250);
    }
}
