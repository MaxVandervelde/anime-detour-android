/*
 * Copyright (c) 2015 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism;

import android.app.Application;

/**
 * Empty implementation of the application callback that does nothing.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class EmptyApplicationCallback implements ApplicationCallback
{
    @Override
    public void onCreate(Application application) {}
}
