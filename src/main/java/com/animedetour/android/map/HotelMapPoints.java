/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
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
    final static LatLng HOTEL_CENTER = new LatLng(44.8618352, -93.3530438);

    final static LatLng[] HOTEL_BASE = new LatLng[] {
        new LatLng(44.86135970000001, -93.3533487),
        new LatLng(44.8612057, -93.3533487),
        new LatLng(44.8612057, -93.3529758),
        new LatLng(44.8613616, -93.3529758),
        new LatLng(44.8613616, -93.3521095),
        new LatLng(44.8619187, -93.3521095),
        new LatLng(44.8619187, -93.3529785),
        new LatLng(44.862177200000005, -93.3529785),
        new LatLng(44.8621782, -93.35243),
        new LatLng(44.8624928, -93.3524287),
        new LatLng(44.86249279999999, -93.3533701),
        new LatLng(44.8619187, -93.3533701),
        new LatLng(44.8619187, -93.3539039),
        new LatLng(44.861354, -93.3539039),
        new LatLng(44.86135970000001, -93.3533487)
    };

    final static LatLng[] PLAZA_1 = new LatLng[] {
        new LatLng(44.8621782, -93.3529195),
        new LatLng(44.862277, -93.3529195),
        new LatLng(44.86227699999999, -93.353067),
        new LatLng(44.8621791, -93.353067),
        new LatLng(44.8621782, -93.3529195),
    };
    final static LatLng PLAZA_1_CENTER = new LatLng(44.8622276, -93.3529946);

    final static LatLng[]  PLAZA_2 = new LatLng[] {
        new LatLng(44.8621782, -93.3527572),
        new LatLng(44.862277, -93.3527572),
        new LatLng(44.862277, -93.3529195),
        new LatLng(44.8621782, -93.3529195),
        new LatLng(44.8621782, -93.3527572),
    };
    final static LatLng PLAZA_2_CENTER = new LatLng(44.8622257, -93.3528391);

    final static LatLng[] PLAZA_3 = new LatLng[] {
        new LatLng(44.8621791, -93.3525963),
        new LatLng(44.862277, -93.3525963),
        new LatLng(44.862277, -93.3527572),
        new LatLng(44.8621782, -93.3527572),
        new LatLng(44.8621791, -93.3525963),
    };
    final static LatLng PLAZA_3_CENTER = new LatLng(44.8622257, -93.3526768);

    final static LatLng[] PLAZA_4 = new LatLng[] {
        new LatLng(44.8624928, -93.3524287),
        new LatLng(44.862492800000005, -93.3525789),
        new LatLng(44.86241390000001, -93.3525789),
        new LatLng(44.8624139, -93.35243),
        new LatLng(44.8624928, -93.3524287),
    };

    final static LatLng[] PLAZA_6 = new LatLng[] {
        new LatLng(44.8624938, -93.3531958),
        new LatLng(44.8623987, -93.3531958),
        new LatLng(44.8623987, -93.3526982),
        new LatLng(44.8624938, -93.3526982),
        new LatLng(44.8624938, -93.3531958),
    };

    final static LatLng[] F1_WOMENS_RESTROOM = new LatLng[] {
        new LatLng(44.862302699999994, -93.3533688),
        new LatLng(44.86220949999999, -93.3533701),
        new LatLng(44.8622095, -93.3532856),
        new LatLng(44.8623027, -93.3532856),
        new LatLng(44.862302699999994, -93.3533688),
    };
    final static LatLng F1_WOMENS_RESTROROM_CENTER = new LatLng(44.8622561, -93.3533299);

    final static LatLng[] F1_MENS_RESTROOM = new LatLng[] {
        new LatLng(44.86220949999999, -93.3533701),
        new LatLng(44.8621049, -93.3533701),
        new LatLng(44.8621054, -93.3533279),
        new LatLng(44.8621049, -93.3532869),
        new LatLng(44.8622095, -93.3532856),
        new LatLng(44.86220949999999, -93.3533701),
    };
    final static LatLng F1_MENS_RESTROROM_CENTER = new LatLng(44.86215680000001, -93.3533286);
}
