package com.kevin.edrive;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class UserHomeActivity extends AppCompatActivity {

    private static final String TAG = "UserHomeActivity";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private MapView mapView;
    private GoogleMap gMap;
    ImageView img03, img04, img05, img06;
    Button btnMap;

    double latitude = 6.9271;  // Default to Colombo
    double longitude = 79.8612;
    String title = "Colombo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);

        // Retrieve data from Intent if passed
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", latitude);
        longitude = intent.getDoubleExtra("longitude", longitude);
        title = intent.getStringExtra("title");
        if (title == null) title = "Location";

        // Initialize MapView
        mapView = findViewById(R.id.mapView01);
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Maps SDK", e);
        }

        if (mapView == null) {
            Toast.makeText(this, "MapView not found", Toast.LENGTH_LONG).show();
            return;
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                gMap = googleMap;
                enableMyLocation();

                gMap.getUiSettings().setZoomControlsEnabled(true);

                LatLng location = new LatLng(latitude, longitude);
                gMap.addMarker(new MarkerOptions().position(location).title(title));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
            }
        });

        Button btnMap02 = findViewById(R.id.btnMap02);

        btnMap02.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (locationManager == null) {
                    Toast.makeText(this, "LocationManager not available.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    String geoUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(My+Location)";
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        Toast.makeText(this, "Google Maps is not installed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Unable to retrieve location.", Toast.LENGTH_SHORT).show();
                }

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        });



        // Navigation buttons
        img03 = findViewById(R.id.img03);
        img04 = findViewById(R.id.img04);
        img05 = findViewById(R.id.img05);
        img06 = findViewById(R.id.img06);
        btnMap = findViewById(R.id.btnMap);

        img03.setOnClickListener(v -> startActivity(new Intent(this, ChargingStationsActivity.class)));
        img04.setOnClickListener(v -> startActivity(new Intent(this, SparePartsActivity.class)));
        img05.setOnClickListener(v -> startActivity(new Intent(this, ServiceStationsActivity.class)));
        img06.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        btnMap.setOnClickListener(v -> startActivity(new Intent(this, DirectionsActivity.class)));
    }

    private void enableMyLocation() {
        if (gMap != null &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            try {
                gMap.setMyLocationEnabled(true);
                gMap.getUiSettings().setMyLocationButtonEnabled(true);
            } catch (SecurityException e) {
                Log.e(TAG, "Permission error enabling MyLocation", e);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // MapView lifecycle methods
    @Override protected void onStart() { super.onStart(); mapView.onStart(); }
    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause() { mapView.onPause(); super.onPause(); }
    @Override protected void onStop() { mapView.onStop(); super.onStop(); }
    @Override protected void onDestroy() { mapView.onDestroy(); super.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }
}
