/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.animedetour.android.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import static com.animedetour.android.map.HotelMapPoints.*;

/**
 * Google Maps display with Hotel points overlayed.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final public class HotelMapFragment extends SupportMapFragment
{
    private ArrayList<Marker> markers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.resetMap();

        return view;
    }

    /**
     * Clear the map and re-draw the base hotel.
     */
    private void resetMap()
    {
        GoogleMap map = this.getMap();
        map.clear();
        map.setBuildingsEnabled(true);
        map.setIndoorEnabled(false);
        map.getUiSettings().setTiltGesturesEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setCompassEnabled(false);

        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(
            new CameraPosition.Builder().target(HOTEL_CENTER).zoom(18F).tilt(0).bearing(180).build()
        );
        map.moveCamera(camera);

        map.addGroundOverlay(
            new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map_floor_1))
                .position(HOTEL_CENTER, 160)
                .bearing(180)
        );
    }
}
