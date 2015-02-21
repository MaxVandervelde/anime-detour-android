/*
 * Copyright (c) 2015 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.inkapplications.prism.ApplicationCallback;

/**
 * Prism application callback to enable the Facebook Stetho debugger.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class StethoCallback implements ApplicationCallback
{
    @Override
    public void onCreate(Application application)
    {
        Stetho.InitializerBuilder builder = Stetho.newInitializerBuilder(application);
        builder.enableDumpapp(Stetho.defaultDumperPluginsProvider(application));
        builder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(application));
        Stetho.initialize(builder.build());
    }
}
