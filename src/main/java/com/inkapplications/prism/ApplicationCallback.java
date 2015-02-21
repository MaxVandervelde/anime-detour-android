/*
 * Copyright (c) 2015 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism;

import android.app.Application;

/**
 * Defines hooks for to be invoked in the application lifecycle.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public interface ApplicationCallback
{
    /**
     * Called when an application is created.
     *
     * @param application The created application.
     */
    public void onCreate(Application application);
}
