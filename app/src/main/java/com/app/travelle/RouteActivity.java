//package com.app.travelle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//
//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//import com.chaquo.python.android.AndroidPlatform;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//
//public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
//
//    private GoogleMap mMap;
//    private MarkerOptions place1, place2;
//    Button getDirection;
//    private Polyline currentPolyline;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.route_activity);
//        getDirection = findViewById(R.id.btnGetDirection);
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapsFrag);
//        mapFragment.getMapAsync(this);
//
//        //27.658143,85.3199503
//        //27.667491,85.3208583
//        place1 = new MarkerOptions().position(new LatLng(6.911025392217044, 79.84855826417153)).title("Location 1");
//        place2 = new MarkerOptions().position(new LatLng(6.903962700873259, 79.95430199947661)).title("Location 2");
//        String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
//        new FetchURL(RouteActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
//
//    }
//
//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        mMap = googleMap;
//        Log.d("mylog", "Added Markers");
//        mMap.addMarker(place1);
//        mMap.addMarker(place2);
//    }
//    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//        // Mode
//        String mode = "mode=" + directionMode;
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + mode;
//        // Output format
//        String output = "json";
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
//        return url;
//    }
//
//
//    @Override
//    public void onTaskDone(Object... values) {
//        if (currentPolyline != null)
//            currentPolyline.remove();
//        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
//    }
//}