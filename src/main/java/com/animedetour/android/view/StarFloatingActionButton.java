/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import com.animedetour.android.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Floating action button that toggles a "favorite" as starred/unstarred.
 *
 * A simple view API to let you toggle the floating action button between
 * starred and un-starred as necessary automatically.
 * Defaults to un-starred state.
 *
 * This class overrides some of the functionality of the Floating Action Button
 * library in its construction, and can therefore not be further extended.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class StarFloatingActionButton extends FloatingActionButton
{
    final private Resources resources;
    private boolean starred;
    private int iconColor;

    public StarFloatingActionButton(Context context)
    {
        this(context, null, 0);
    }

    public StarFloatingActionButton(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public StarFloatingActionButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.resources = context.getResources();

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.StarFloatingActionButton, 0, 0);
        int defaultColor = this.resources.getColor(android.R.color.white);

        this.iconColor = attr.getColor(R.styleable.StarFloatingActionButton_iconColor, defaultColor);
        this.setIcon(R.drawable.unstarred);

        attr.recycle();
    }

    @Override
    public void setIcon(@DrawableRes int icon)
    {
        Drawable drawable = this.resources.getDrawable(icon);
        this.setIconDrawable(drawable);
    }

    @Override
    public void setIconDrawable(@NonNull Drawable icon)
    {
        icon.setColorFilter(this.iconColor, PorterDuff.Mode.SRC_ATOP);
        super.setIconDrawable(icon);
    }

    /**
     * Update the view to a specified starred state.
     *
     * @param starred Whether the view should display as starred or un-starred.
     */
    public void setStarred(boolean starred)
    {
        if (starred) {
            this.setIcon(R.drawable.starred);
            this.starred = true;
        } else {
            this.setIcon(R.drawable.unstarred);
            this.starred = false;
        }
    }

    /**
     * Change the starred state of the view to the opposite of what it is
     * currently.
     */
    public void toggle()
    {
        if (this.starred) {
            this.setStarred(false);
        } else {
            this.setStarred(true);
        }
    }

    /**
     * @return Whether the view is currently displayed as starred.
     */
    public boolean isStarred()
    {
        return this.starred;
    }

    /**
     * Change the color of the star icon in the view.
     *
     * @param color The android color resource ID.
     */
    public void setIconColor(@ColorRes int color)
    {
        this.iconColor = this.resources.getColor(color);
    }
}
