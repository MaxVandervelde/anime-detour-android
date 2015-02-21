/*
 * Copyright (c) 2015 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism;

import android.app.Application;

import java.util.List;

/**
 * Used to invoke multiple callbacks as one.
 *
 * This contains a collection of application callback and is an application
 * callback itself. This proxies the application callbacks into each of the
 * callbacks inside the collection.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class CompositeApplicationCallback implements ApplicationCallback
{
    /**
     * Collection of callbacks to proxy to.
     */
    final private List<ApplicationCallback> callbacks;

    /**
     * @param callbacks Collection of callbacks to proxy to.
     */
    public CompositeApplicationCallback(List<ApplicationCallback> callbacks)
    {
        this.callbacks = callbacks;
    }

    @Override
    public void onCreate(Application application)
    {
        for (ApplicationCallback callback : this.callbacks) {
            callback.onCreate(application);
        }
    }
}
