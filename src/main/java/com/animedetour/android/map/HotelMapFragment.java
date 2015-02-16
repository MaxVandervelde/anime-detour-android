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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.ui.IconGenerator;

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
        this.showFloor1();

        return view;
    }

    private void showFloor1()
    {
        this.showPanelRoom(PLAZA_1);
        this.showRoomLabel(PLAZA_1_CENTER, "Plaza 1");
        this.showPanelRoom(PLAZA_2);
        this.showRoomLabel(PLAZA_2_CENTER, "Plaza 2");
        this.showPanelRoom(PLAZA_3);
        this.showRoomLabel(PLAZA_3_CENTER, "Plaza 3");
        this.showPanelRoom(PLAZA_4);
        // WHERE'S PLAZA 5?!?!
        this.showPanelRoom(PLAZA_6);
    }

    private void hideMarkers()
    {
        for (Marker options : markers) {
            options.setVisible(false);
        }
    }

    private void showMarkers()
    {
        for (Marker options : markers) {
            options.setVisible(true);
        }
    }

    /**
     * Display a label on the map at a given position.
     *
     * The label will added to the list of markers to be hidden when the map is
     * zoomed out.
     *
     * @param position Point to center the label at.
     * @param label The text to display on the map.
     */
    private void showRoomLabel(LatLng position, String label)
    {
        IconGenerator iconGenerator = new IconGenerator(this.getActivity());
        GoogleMap map = this.getMap();

        iconGenerator.setTextAppearance(R.style.caption);
        iconGenerator.setBackground(null);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(label));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title(label);
        markerOptions.anchor(
            iconGenerator.getAnchorU(),
            iconGenerator.getAnchorV()
        );
        markerOptions.icon(icon);
        Marker marker = map.addMarker(markerOptions);
        this.markers.add(marker);
    }

    /**
     * Display a polygon as a "panel room" by drawing it's borders.
     *
     * @param points The points of the room's polygon to draw
     */
    private void showPanelRoom(LatLng[] points)
    {
        int primaryColor = this.getResources().getColor(R.color.primary_highlight);
        int strokeColor = this.getResources().getColor(R.color.primary_dim);
        GoogleMap map = this.getMap();

        PolygonOptions roomOptions = new PolygonOptions();
        roomOptions.add(points);
        roomOptions.strokeColor(strokeColor);
        roomOptions.strokeWidth(4);
        roomOptions.fillColor(primaryColor);
        map.addPolygon(roomOptions);
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

        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(
            new CameraPosition.Builder().target(HOTEL_CENTER).zoom(17.3f).tilt(0f).build()
        );
        map.moveCamera(camera);

        PolygonOptions buildingOptions = new PolygonOptions();
        buildingOptions.add(HOTEL_BASE);
        buildingOptions.strokeWidth(0);
        int baseColor = this.getResources().getColor(R.color.primary_highlight);
        buildingOptions.fillColor(baseColor);
        map.addPolygon(buildingOptions);

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override public void onCameraChange(CameraPosition cameraPosition) {
                if (cameraPosition.zoom > 18.5) {
                    showMarkers();
                } else {
                    hideMarkers();
                }
            }
        });
    }
}
