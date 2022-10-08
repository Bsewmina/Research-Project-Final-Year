package com.app.travelle;

import androidx.fragment.app.FragmentActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.app.travelle.databinding.ActivityHomeMapBinding;

public class HomeMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityHomeMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding = ActivityHomeMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e("HomeMap", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("HomeMap", "Can't find style. Error: ", e);
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(6.914833286829916, 79.97314407363564);
        mMap.addMarker(new MarkerOptions().position(sydney).title("You are Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
        mMap.getUiSettings().setMapToolbarEnabled(false);

    }
}
