/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Hotel Polygons & Marker Points Storage.
 *
 * These were taken from points in Google's MapEngine and converted. Ideally
 * we could use the XML straight from Google MapsEngine and parse it, but
 * there's currently no good API for that. Since the building doesn't change
 * often, this should be fine.
 *
 * @todo This needs to be completed when we have more of the map finalized.
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final class HotelMapPoints
{
    final static LatLng HOTEL_CENTER = new LatLng(44.8619752, -93.3530438);
}
