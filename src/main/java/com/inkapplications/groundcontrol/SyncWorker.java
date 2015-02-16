/*
 * Copyright (c) 2015 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.groundcontrol;

import rx.Subscriber;

import java.sql.SQLException;

/**
 * A worker that synchronizes a remote data source with a local one.
 *
 * Implements the reactive callback in three phases:
 *  - Look up local data and inform subscriber
 *  - fetch remote data and save it locally
 *  - Look up local data again and inform subscriber
 *
 *  This will complete the subscriber when finished.
 *
 * @param <YIELD> The type of data that the worker will lookup and return.
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
abstract public class SyncWorker<YIELD> implements Worker<YIELD>
{
    @Override
    public void call(Subscriber<? super YIELD> subscriber)
    {
        try {
            this.lookup(subscriber);
        } catch (Exception e) {
            subscriber.onError(e);
        }

        subscriber.onCompleted();
    }

    /**
     * Look up local data and sync remote data if needed.
     *
     * This will first lookup the local data and inform the subscriber.
     * If the local data is stale, it will look up the remote data and save it.
     * After saving remote data, it will do another local lookup.
     */
    private void lookup(Subscriber<? super YIELD> subscriber) throws Exception
    {
        YIELD currentEvents = this.lookupLocal();
        subscriber.onNext(currentEvents);

        if (false == this.dataIsStale()) {
            return;
        }

        YIELD events = lookupRemote();
        this.saveLocal(events);

        YIELD newEvents = this.lookupLocal();
        subscriber.onNext(newEvents);
    }

    abstract public boolean dataIsStale() throws SQLException;
    abstract public YIELD lookupRemote() throws Exception;
    abstract public void saveLocal(YIELD yield) throws SQLException;
}
