/*
 * Copyright (c) 2014 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.inkapplications.prism;

import org.apache.commons.logging.Log;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.HashMap;
import java.util.List;

/**
 * Manages adding observers to subscriptions to prevent duplicate requests.
 *
 * Keeps a collection of requests that are run and will join an observer
 * to the previous subscription if one is "in flight" at the time.
 * This is intended to be used in a repository when looking up remote data.
 * Observers are run on Android's main thread, and background is run on IO.
 *
 * @param <TYPE> The entity that this repository represents.
 */
public class SubscriptionManager<TYPE>
{
    final private Log logger;

    /** Stores in-flight requests for events. */
    final private HashMap<String, Observable<TYPE>> requests = new HashMap<>();

    /** Stores in-flight requests for events when fetching a collection. */
    final private HashMap<String, Observable<List<TYPE>>> collectionRequests = new HashMap<>();

    public SubscriptionManager(Log logger)
    {
        this.logger = logger;
    }

    /**
     * Create or join with previous subscription for a collection of the entity.
     *
     * Ensures that the request logic is only run once and additional calls made
     * before completion will be added as a subscriber causing the observer
     * callbacks to be invoked, but not the on-subscribe logic.
     *
     * @param onSubscribe Logic to run for the request.
     * @param observer Callback to invoke on request events.
     * @param key A unique key to identify this request type seperate from others.
     * @return A subscription for the observer that may be unsubscribed if necessary.
     */
    final public Subscription createCollectionSubscription(
        OnSubscribe<List<TYPE>> onSubscribe,
        Observer<List<TYPE>> observer,
        String key
    ) {
        this.logger.trace("Creating collection subscription.");
        Observable<List<TYPE>> callback = Observable.create(onSubscribe);
        callback = callback.subscribeOn(Schedulers.io());
        callback = callback.observeOn(AndroidSchedulers.mainThread());
        callback = callback.cache();

        Observable<List<TYPE>> previousRequest = this.collectionRequests.get(key);
        Subscription subscription;
        if (null == previousRequest) {
            this.logger.debug("No previous request to join.");
            this.collectionRequests.put(key, callback);
            subscription = callback.subscribe(observer);
        } else {
            this.logger.debug("Joining with previous request.");
            subscription = previousRequest.subscribe(observer);
        }

        return subscription;
    }

    /**
     * Create or join with previous subscription for an entity.
     *
     * Ensures that the request logic is only run once and additional calls made
     * before completion will be added as a subscriber causing the observer
     * callbacks to be invoked, but not the on-subscribe logic.
     *
     * @param onSubscribe Logic to run for the request.
     * @param observer Callback to invoke on request events.
     * @param key A unique key to identify this request type seperate from others.
     * @return A subscription for the observer that may be unsubscribed if necessary.
     */
    final public Subscription createSubscription(
        OnSubscribe<TYPE> onSubscribe,
        Observer<TYPE> observer,
        String key
    ) {
        this.logger.trace("Creating subscription.");
        Observable<TYPE> callback = Observable.create(onSubscribe);
        callback = callback.subscribeOn(Schedulers.io());
        callback = callback.observeOn(AndroidSchedulers.mainThread());
        callback = callback.cache();

        Observable<TYPE> previousRequest = this.requests.get(key);
        Subscription subscription;
        if (null == previousRequest) {
            this.logger.debug("No previous request to join.");
            this.requests.put(key, callback);
            subscription = callback.subscribe(observer);
        } else {
            this.logger.debug("Joining with previous request.");
            subscription = previousRequest.subscribe(observer);
        }

        return subscription;
    }

    /**
     * Clears out all "in-flight" requests managed by this service.
     */
    public void emptySubscriptions()
    {
        this.collectionRequests.clear();
        this.requests.clear();
    }
}
