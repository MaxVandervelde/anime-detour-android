/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.fader;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import com.animedetour.android.R;

/**
 * Fades a toolbar from 0% opacity to 100% on scroll.
 *
 * This is designed to fade a toolbar in when it reaches the bottom of a
 * reference view, typically a header image.
 *
 * This fader, once created, should be bound to the scroll listener of the
 * scrolling view.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ToolbarFader implements ViewTreeObserver.OnScrollChangedListener
{
    final private Resources resources;
    final private int baseColor;
    final private View reference;
    final private ScrollView container;
    final private View target;

    /**
     * @param resources System resources for looking up colors and dimensions.
     * @param baseColor The color to make the toolbar at 100%.
     * @param reference The view to use as a reference height to scroll down to.
     * @param container The scrolling container to monitor.
     * @param target The target Toolbar to be changing the color of.
     */
    public ToolbarFader(
        Resources resources,
        int baseColor,
        View reference,
        ScrollView container,
        View target
    ) {
        this.resources = resources;
        this.baseColor = baseColor;
        this.reference = reference;
        this.container = container;
        this.target = target;
    }

    @Override
    public void onScrollChanged()
    {
        int referenceHeight = this.reference.getMeasuredHeight();
        float offset = this.resources.getDimension(R.dimen.abc_action_bar_default_height_material);
        float fadeHeight = referenceHeight - offset;
        float scrollY = this.container.getScrollY();
        float fade = this.getAlphaForView(scrollY, fadeHeight);
        int baseColor = this.resources.getColor(this.baseColor);
        int color = this.adjustAlpha(baseColor, fade);
        this.target.setBackgroundColor(color);
    }

    /**
     * Get the transparency based on the scroll position.
     *
     * Transparency will approach 100% as the scroll reaches the height of the
     * referenced view.
     *
     * @param position the scrollview's current scroll position
     * @param referenceHeight the height of the reference image to compare to.
     * @return the transparency to use for the toolbar.
     */
    private float getAlphaForView(float position, float referenceHeight) {
        if (position < 0) {
            return 0;
        }

        if (position > referenceHeight) {
            return 1;
        }
        return position / referenceHeight;
    }

    /**
     * Sets the alpha layer on a color.
     *
     * @param color The rgb color to modify.
     * @param factor the alpha percentage to apply to the color.
     * @return The specified color with the specified transparency.
     */
    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
