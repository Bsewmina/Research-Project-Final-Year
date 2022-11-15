package com.app.travelle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.common.base.CharMatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import io.grpc.internal.JsonParser;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;
    Marker mm;
    Bundle bundle;
    TextView timeDisplay,fireDisply,TrackerLoca;
    String[] Route;
    ProgressDialog pd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_activity);
        //requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        pd = new ProgressDialog(this);

        timeDisplay = findViewById(R.id.time_pred);
        fireDisply = findViewById(R.id.fair_calc);
        TrackerLoca = findViewById(R.id.TrackerL);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapsFrag);
        mapFragment.getMapAsync(this);

        //27.658143,85.3199503
        //27.667491,85.3208583
        Intent intent = getIntent();
        bundle = intent.getExtras();

        int ArrivalID = bundle.getInt("EndLocation");

        Route = findRoute(1,13,ArrivalID);


        int Routlength = Route.length - 1;

        place1 = new MarkerOptions().position(new LatLng(Double.valueOf(Route[1]), Double.valueOf(Route[2]))).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(Double.valueOf(Route[Routlength-2]), Double.valueOf(Route[Routlength-1]))).title("Location 2");
        String url = getUrl(place1.getPosition(), place2.getPosition(), "transit");
        new FetchURL(RouteActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

        pd.setTitle("Travelle Fetching..");
        pd.show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(place1);
        mMap.addMarker(place2);

        // Firebase data come from here
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/");
        System.out.println("================================================") ;
        System.out.println(databaseReference);


        ValueEventListener listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                System.out.println("changing-----------------------------");
                String latitude = dataSnapshot.child("latitude").getValue(String.class);
                String longitude = dataSnapshot.child("longitude").getValue(String.class);

                System.out.println(latitude+longitude);
                LatLng location = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                //Toast.makeText(RouteActivity.this, "This is data :-"+latitude+" Longi :-"+longitude, Toast.LENGTH_SHORT).show();

                //Set the location
                String NearestValue =  GetCurrentLocationName(Double.parseDouble(longitude),Double.parseDouble(latitude));
                TrackerLoca.setText(NearestValue);


                if(mm != null){

                    mm.remove();
                }

                mm = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title("117")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon))

                );



                //bus stop marker here
                LatLng BStopLocation = new LatLng(6.913578717566548,79.99873889604599);

                mMap.addMarker(new MarkerOptions()
                        .position(BStopLocation)
                        .title("BusStop1")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.transit))


                );
                //bus stop marker end here
                int Routlength = Route.length - 1;
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(Double.valueOf(Route[1]), Double.valueOf(Route[2])));
                builder.include(new LatLng(Double.valueOf(Route[Routlength-2]), Double.valueOf(Route[Routlength-1])));
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 190));
                pd.dismiss();


                mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {




                    }
                });


                int TravelTime = bundle.getInt("TimePrediction");
                timeDisplay.setText(""+TravelTime+"min");
                fireDisply.setText("LKR "+Route[0]+".00");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }


    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    public String[] findRoute(int map,int start, int destination){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("AL");
        PyObject obj = pyobj.callAttr("get_route",map,start,destination);



        System.out.println("-----------ssssssssssssssssssssssss-------------------");



        String testValue = obj.toString();
        String valueRE = testValue.replace("[(","");
        String valueRE2 = valueRE.replace(")]","");
        String valueRE3 = valueRE2.replace(")","");
        String valueRE4 = valueRE3.replace("(","");
        String valueRE5 = valueRE4.replace("[","");


        int occurance = CharMatcher.is(',').countIn(valueRE5);


        String[] arrOfStr = valueRE5.split(",", occurance);


        for (String a : arrOfStr)
            System.out.println(a);

        return arrOfStr;








    }

    public String GetCurrentLocationName(Double longi,Double lat){

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("AL");
        PyObject obj = pyobj.callAttr("get_nearest_station",lat,longi);

        PyObject obj2 = pyobj.callAttr("get_route",1,2,3);

        //semina function call and get the current location name
        return obj.toString();

    }


}