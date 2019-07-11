package com.example.mapsactivityapikey;

import androidx.fragment.app.FragmentActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //CLick sobre el mapa
        mMap.setOnMapClickListener(this);

        //CLICK SOBRE MARCADOR
        mMap.setOnMarkerClickListener(this);

        //DRAG MARCADOR
        mMap.setOnMarkerDragListener(this);

        LatLng sydney = new LatLng(-34, 151);
        Marker markerSidney = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker Sidney")
                .snippet("Ahora mismo no estamos aqui")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.position)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        //Aplicando estilo al mapa solo es posible con mapa normal y no vista satelital
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("MAP", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MAP", "Can't find style. Error: ", e);
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng)
                .title("Nueva MARCA")
        .draggable(true));

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
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
        LatLng position = marker.getPosition();
        marker.setSnippet(position.latitude + ", " + position.longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(position));
        marker.showInfoWindow();
    }
}
