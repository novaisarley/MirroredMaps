package com.br.arley.atvuberapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MotoristaActivity extends FragmentActivity implements OnMapReadyCallback {


    private FirebaseDatabase database;
    DatabaseReference myRef;
    private GoogleMap mMap;
    private Marker previousMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_motorista_map);
        mapFragment.getMapAsync(this);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("uber");
        // Read from the database

        setLastCoord();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                 myRef = database.getReference("uber");

                myRef.child("coordenadas").child("latlng0").child("lat").setValue(Double.toString(latLng.latitude));
                myRef.child("coordenadas").child("latlng0").child("lng").setValue(Double.toString(latLng.longitude));

                if (previousMarker != null){
                    previousMarker.remove();
                }

                previousMarker = mMap.addMarker(new MarkerOptions().position(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });
    }

    private void setLastCoord() {
        myRef = database.getReference("uber");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    double lat;
                    double lng;

                    if (previousMarker != null) {
                        previousMarker.remove();
                    }

                    lat = Double.parseDouble(snapshot.child("coordenadas").child("latlng0").child("lat").getValue(String.class));

                    lng = Double.parseDouble(snapshot.child("coordenadas").child("latlng0").child("lng").getValue(String.class));

                    LatLng latLng = new LatLng(lat, lng);

                    previousMarker = mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}