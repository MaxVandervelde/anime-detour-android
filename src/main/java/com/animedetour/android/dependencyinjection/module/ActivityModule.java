package com.animedetour.android.dependencyinjection.module;

import com.inkapplications.android.standard.dependencyinjection.module.StandardActivityModule;
import com.animedetour.android.activity.MainActivity;
import dagger.Module;

@Module(
    includes = {

    },
    injects = {
        MainActivity.class
    },
    addsTo = StandardActivityModule.class,
    complete = false,
    library = true
)
public class ActivityModule
{

}
