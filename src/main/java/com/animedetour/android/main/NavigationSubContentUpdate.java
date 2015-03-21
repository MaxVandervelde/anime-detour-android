/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.main;

import java.util.Set;

/**
 * Indicates a new set of data to display in the main navigation spinner as
 * subcontent of the main component.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class NavigationSubContentUpdate
{
    /**
     * The elements to display as navigation options to the user.
     */
    final private Set<String> options;

    /**
     * @param options Elements to display as navigation options to the user.
     */
    public NavigationSubContentUpdate(Set<String> options)
    {
        this.options = options;
    }

    /**
     * @return Elements to display as navigation options to the user.
     */
    public Set<String> getOptions()
    {
        return this.options;
    }

    /**
     * @return Elements to display as navigation options to the user.
     */
    public String[] getOptionsArray()
    {
        if (null == this.options) {
            return null;
        }

        return this.options.toArray(new String[this.options.size()]);
    }
}
