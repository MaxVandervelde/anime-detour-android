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
 * Service that holds onto a spinner's state.
 *
 * @todo Refactor this to handle more than just stringly typed data.
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public interface SpinnerOptionContainer
{
    /**
     * Get the currently selected item in the spinner.
     *
     * @return The item currently selected in the spinner, or null if none is
     *         available. Currently this is the exact string that is displayed
     *         to the user in the spinner.
     */
    public String getSpinnerSelection();
}
