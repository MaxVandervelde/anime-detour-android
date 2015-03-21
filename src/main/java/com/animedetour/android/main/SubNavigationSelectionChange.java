/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.main;

/**
 * Indicates that the sub-navigation of the application has been changed.
 *
 * This event will be dispatched every time the user changes the option
 * in the actionbar spinner. Eg. when the user changes a filter on the event
 * list, for instance.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class SubNavigationSelectionChange
{
    /** The sub navigation item that the user has navigated to. */
    final public String selection;

    /**
     * @param selection The sub navigation item that the user has navigated to.
     */
    public SubNavigationSelectionChange(String selection)
    {
        this.selection = selection;
    }

    /**
     * @return The sub navigation item that the user has navigated to.
     */
    public String getSelection()
    {
        return selection;
    }
}
