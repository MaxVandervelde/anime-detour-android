package com.animedetour.android.framework;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import org.apache.commons.logging.Log;

public class AnalyticsLogger implements Log
{
    private Tracker analyticsTracker;

    public AnalyticsLogger(Tracker analyticsTracker)
    {
        this.analyticsTracker = analyticsTracker;
    }

    @Override
    public boolean isDebugEnabled()
    {
        return false;
    }

    @Override
    public boolean isErrorEnabled()
    {
        return true;
    }

    @Override
    public boolean isFatalEnabled()
    {
        return true;
    }

    @Override
    public boolean isInfoEnabled()
    {
        return false;
    }

    @Override
    public boolean isTraceEnabled()
    {
        return false;
    }

    @Override
    public boolean isWarnEnabled()
    {
        return false;
    }

    @Override
    public void error(Object o) {
        HitBuilders.ExceptionBuilder builder = new HitBuilders.ExceptionBuilder();
        builder.setDescription(o.toString());
        builder.setFatal(false);

        this.analyticsTracker.send(builder.build());
    }

    @Override
    public void error(Object o, Throwable throwable)
    {
        HitBuilders.ExceptionBuilder builder = new HitBuilders.ExceptionBuilder();
        builder.setDescription(o.toString() + ":" + throwable.getMessage());
        builder.setFatal(false);

        this.analyticsTracker.send(builder.build());
    }

    @Override
    public void fatal(Object o)
    {
        HitBuilders.ExceptionBuilder builder = new HitBuilders.ExceptionBuilder();
        builder.setDescription(o.toString());
        builder.setFatal(true);

        this.analyticsTracker.send(builder.build());
    }

    @Override
    public void fatal(Object o, Throwable throwable)
    {
        HitBuilders.ExceptionBuilder builder = new HitBuilders.ExceptionBuilder();
        builder.setDescription(o.toString() + ":" + throwable.getMessage());
        builder.setFatal(true);

        this.analyticsTracker.send(builder.build());
    }

    @Override public void trace(Object o) {}
    @Override public void trace(Object o, Throwable throwable) {}
    @Override public void debug(Object o) {}
    @Override public void debug(Object o, Throwable throwable) {}
    @Override public void info(Object o) {}
    @Override public void info(Object o, Throwable throwable) {}
    @Override public void warn(Object o) {}
    @Override public void warn(Object o, Throwable throwable) {}

    protected void foo(Object o)
    {
    }
}
