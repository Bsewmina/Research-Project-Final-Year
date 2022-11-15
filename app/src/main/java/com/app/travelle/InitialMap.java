package com.app.travelle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import java.util.HashMap;
import java.util.Map;

public class InitialMap extends AppCompatActivity {

    private AutoCompleteTextView editText;

    private static final String[] CITIES = new String[]{
            "Viharamahadevi Park","House Of Fashion","Castle Street","Rajagiriya","HSBC Rajagiriya","Ethulkotte New","Parliament Junction","Battaramulla Junction","Ganahena","Koswatta","Kotte-Bope"
            ,"Thalahena Junction","Malabe","Fort_TR","Kompannavidiya_TR","Kollupitiya_TR","Bambalapitiya_TR","Wellawatte_TR","Dehiwala_TR"
    };
    //Initialize variable
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    GoogleMap map;
    TextView textView;
    Button findBtn;
    Double Longi;
    Double Lati;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_initial_map);
        pd = new ProgressDialog(this);

        //find button function
        findBtn = findViewById(R.id.findbus);


        //id selecting
        textView = findViewById(R.id.current_location);

        //Where to declaration
        editText = findViewById(R.id.where_to);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, CITIES);

        editText.setAdapter(adapter);



        //Assign variable

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_section);

        //Initialize Fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        //Check permission
        if (ActivityCompat.checkSelfPermission(InitialMap.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When granted
            //Call method
            getCurrentLocation();



        }
        else{
            //When denied request permission
            ActivityCompat.requestPermissions(InitialMap.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }


        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set title of progress dialog

                String value = editText.getText().toString();
                String valu2 = textView.getText().toString();


                String locationOne = textView.getText().toString();//start location
                String locationTwo = editText.getText().toString();//end location
                int travelTime = GetTravelTime(locationOne,locationTwo);
                int ArrivalValue = GetStationID(locationTwo);


                    startActivity(new Intent(InitialMap.this, RouteActivity.class)
                            .putExtra("key", 79.95430199947661)
                            .putExtra("rname", 6.903962700873259)
                            .putExtra("TimePrediction", travelTime)
                            .putExtra("EndLocation", ArrivalValue)

                    );
            }

        });



    }








    private void getCurrentLocation() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location !=null){
                    //Sync map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {   //Maps components customization


                            //Initialize lat lan
                            LatLng latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            Longi=location.getLongitude();
                            Lati=location.getLatitude();

                            System.out.println("Longi :- "+Longi +" Lat:- "+Lati);
                            textView.setText(""+GetCurrentLocationName(Longi,Lati));


                            //Create marker
                            MarkerOptions options = new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder__3_))
                                    .title("You are here");


                            //Zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                            //Add marker
                            googleMap.addMarker(options);

                            try {
                                // Customise the styling of the base map using a JSON object defined
                                // in a raw resource file.
                                boolean success = googleMap.setMapStyle(
                                        MapStyleOptions.loadRawResourceStyle(
                                                getApplicationContext(), R.raw.map_style));

                                if (!success) {
                                    Log.e("InitialMap", "Style parsing failed.");
                                }
                            } catch (Resources.NotFoundException e) {
                                Log.e("InitialMap", "Can't find style. Error: ", e);
                            }
                        }
                    });
                }

            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission granted
                getCurrentLocation();


            }
        }
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

    public  int GetStationID(String name){


        // Create a HashMap object called capitalCities
        HashMap<String, Integer> capitalCities = new HashMap<String, Integer>();

        // Add keys and values (name,value)
        capitalCities.put("Kollupitiya", 0);
        capitalCities.put("Viharamahadevi Park", 1);
        capitalCities.put("House Of Fashion", 2);
        capitalCities.put("Castle Street", 3);
        capitalCities.put("Rajagiriya", 4);
        capitalCities.put("HSBC Rajagiriya", 5);
        capitalCities.put("Ethulkotte New", 6);
        capitalCities.put("Parliament Junction", 7);
        capitalCities.put("Battaramulla Junction", 8);
        capitalCities.put("Ganahena", 9);
        capitalCities.put("Koswatta", 10);
        capitalCities.put("Kotte-Bope", 11);
        capitalCities.put("Thalahena Junction", 12);
        capitalCities.put("Malabe", 13);
        capitalCities.put("Fort_TR", 14);
        capitalCities.put("Kompannavidiya_TR", 15);
        capitalCities.put("Kollupitiya_TR", 16);
        capitalCities.put("Bambalapitiya_TR", 17);
        capitalCities.put("Wellawatte_TR", 18);
        capitalCities.put("Dehiwala_TR", 19);

        int val = capitalCities.get(name);

        return val;
    }

    public int GetTravelTime(String StartLocation,String EndLocation){
        int time=0;


        if(StartLocation.equals("Malabe")){

            if(EndLocation.equals("Koswatta")){
                time = 20;
            }
            else if(EndLocation.equals("Kollupitiya")){
                time = 58;

            }
            else if(EndLocation.equals("Viharamahadevi Park")){
                time = 56;
            }
            else if(EndLocation.equals("House Of Fashion")){
                time = 44;
            }
            else if(EndLocation.equals("Castle Street")){
                time = 41;
            }
            else if(EndLocation.equals("Rajagiriya")){
                time = 43;
            }
            else if(EndLocation.equals("HSBC Rajagiriya")){
                time = 42;
            }
            else if(EndLocation.equals("Ethulkotte New")){
                time = 34;
            }
            else if(EndLocation.equals("Parliaent Junction")){
                time = 31;
            }
            else if(EndLocation.equals("Battaramulla Junction")){
                time = 28;
            }
            else if(EndLocation.equals("Ganahena")){
                time = 26;
            }
            else if(EndLocation.equals("Kotte-Bope")){
                time = 20;
            }
            else if(EndLocation.equals("Thalahena Junction")){
                time = 15;
            }

        }else{

        }

        return time;

    }

}
