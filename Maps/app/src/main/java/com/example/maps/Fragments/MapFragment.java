package com.example.maps.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maps.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener, View.OnClickListener {

    private View rootview;
    private MapView mapView;
    private GoogleMap gmap;

    private List<Address> addresses;
    private Geocoder geocoder;

    private MarkerOptions marker;

    private FloatingActionButton fab;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_map, container, false);

        fab = rootview.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootview.findViewById(R.id.map);
        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        LatLng playa = new LatLng(20.650373, -87.098374);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        marker = new MarkerOptions();
        marker.position(playa);
        marker.title("MI Marcador");
        marker.draggable(true);
        marker.snippet("Esto es una caja de texto donde modificar los datos");
        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));

        gmap.addMarker(marker);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(playa));
        gmap.animateCamera(zoom);

        gmap.setOnMarkerDragListener(this);

        geocoder = new Geocoder(getContext(), Locale.getDefault());


    }

    private void checkGPSEnable(){
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if(gpsSignal == 0){
                showInfoAlert();
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showInfoAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("GPS Signal")
                .setMessage("You don´t have gps Signal enabled")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", null).show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;

        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String cp = addresses.get(0).getPostalCode();

        marker.setSnippet("Adress:" + address + "\n" +
                "City:" + city + "\n" +
                "State:" + state + "\n" +
                "Country:" + country + "\n" +
                "CP:" + cp + "\n");
        marker.showInfoWindow();
        /*Toast.makeText(getContext(), "Adress:" + address + "\n" +
                                    "City:" + city + "\n" +
                                    "State:" + state + "\n" +
                                    "Country:" + country + "\n" +
                                    "CP:" + cp + "\n"
                , Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onClick(View view) {
        this.checkGPSEnable();
    }
}
