/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.animator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Animator to slide items in from the Left of the screen.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class SlideInLeftAnimator extends DefaultItemAnimator
{
    private final Interpolator interpolator = new AccelerateInterpolator();
    private final LinearLayoutManager layoutManager;

    public SlideInLeftAnimator(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        int width = getWidth(holder);
        ViewCompat.animate(view).cancel();
        float interpolation = interpolator.getInterpolation((float) view.getTop() / layoutManager.getHeight());

        ViewCompat.animate(view)
            .setStartDelay((long) (500 * interpolation))
            .translationX(width)
            .alpha(0)
            .setInterpolator(new DecelerateInterpolator());
        return true;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        int width = getWidth(holder);
        ViewCompat.animate(view).cancel();
        ViewCompat.setTranslationX(holder.itemView, (float) -width);
        float interpolation = interpolator.getInterpolation((float) view.getTop() / layoutManager.getHeight());
        long startDelay = (long) (500 * interpolation);
        ViewCompat.animate(view)
            .setStartDelay(Math.max(0, startDelay))
            .translationX(0)
            .setInterpolator(new AccelerateInterpolator());
        return true;
    }

    public int getWidth(RecyclerView.ViewHolder holder) {
        return getWidth(holder.itemView);
    }

    public int getWidth(View itemView) {
        return itemView.getMeasuredWidth() + itemView.getPaddingRight() + ((RecyclerView.LayoutParams) itemView.getLayoutParams()).rightMargin;
    }
}
