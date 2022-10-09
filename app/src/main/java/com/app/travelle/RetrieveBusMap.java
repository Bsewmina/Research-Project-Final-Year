package com.app.travelle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.app.travelle.databinding.ActivityRetrieveBusMapBinding;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class RetrieveBusMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityRetrieveBusMapBinding binding;
    Marker  mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Started");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRetrieveBusMapBinding.inflate(getLayoutInflater());
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/");
        System.out.println("================================================") ;
        System.out.println(databaseReference);

        //Styling
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

        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("changing-----------------------------");
                String latitude = dataSnapshot.child("latitude").getValue(String.class);
                String longitude = dataSnapshot.child("longitude").getValue(String.class);

                System.out.println(latitude+longitude);
                LatLng location = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));



                if(mm != null){

                    mm.remove();
                }

                mm = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title("117")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon))

                );
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,17));
                mMap.getUiSettings().setMapToolbarEnabled(false);



            }
         
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}