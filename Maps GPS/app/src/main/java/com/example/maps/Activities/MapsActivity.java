package com.example.maps.Activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(5);
        mMap.setMaxZoomPreference(18);

        // Add a marker in Sydney and move the camera
        LatLng playa = new LatLng(20.650373, -87.098374);
        mMap.addMarker(new MarkerOptions().position(playa).title("Ubicación").draggable(true));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(playa)
                .zoom(18) //Limit 21
                .bearing(90) //Limit 365
                .tilt(45).build(); //90
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(playa));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MapsActivity.this,"CLICK ON Lat" + latLng.latitude + "Long" + latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(MapsActivity.this,"Marker dragged on" + marker.getPosition().latitude + "Long" + marker.getPosition().longitude, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
