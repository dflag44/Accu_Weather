package com.example.accu_weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;
    public String lat = "11";
    public String lon = "11";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        TextView city = (TextView) findViewById(R.id.city);
        TextView temp = (TextView) findViewById(R.id.temp);

        btLocation =

                findViewById(R.id.get_location);
        //      textView1 = findViewById(R.id.textView1);
//        textView2 = findViewById(R.id.textView2);
        textView3 =

                findViewById(R.id.textView3);
//        textView4 = findViewById(R.id.textView4);
//        textView5 = findViewById(R.id.textView5);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        public void onClick (){
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        //Initialise location coordinates
                        Location location = task.getResult();
                        if (location != null) {
                            try {
                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                //textView3.setText(Html.fromHtml("<font color='#06200EE'><b> Latitude: </b><br></font>"+ addresses.get(0).getLatitude()));
                                // textView3.setText(Html.fromHtml("<font color='#06200EE'><b> Longitude: </b><br></font>"+ addresses.get(0).getLongitude()));
//                        textView3.setText(Html.fromHtml("<font color='#06200EE'><b> Country: </b><br></font>"+ addresses.get(0).getCountryName()));
//                        textView4.setText(Html.fromHtml("<font color='#06200EE'><b> city: </b><br></font>"+ addresses.get(0).getLocality()));
//                        textView5.setText(Html.fromHtml("<font color='#06200EE'><b> Address: </b><br></font>"+ addresses.get(0).getAddressLine(0)));
                                double x = addresses.get(0).getLatitude();

                                int tmpp = (int) x * 1000;

                                x = tmpp / 1000;

                                double y = addresses.get(0).getLongitude();

                                tmpp = (int) y * 1000;

                                y = tmpp / 1000;

//                        lat=String.valueOf(addresses.get(0).getLatitude());
//                        lon=String.valueOf(addresses.get(0).getLongitude());

                                lat = String.valueOf(x);
                                lon = String.valueOf(y);

                                textView3.setText(lat + "    " + lon);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

                getLocation();
                // if(lon.equals("11")){textView3.setText("hello ji");getLocation();}

                String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=d37c35cd6d7b1b8b45faddee3a3f7dbf";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject innerresponse = response.getJSONObject("main");
                                    Log.d("myapp", response.getString("name"));
                                    city.setText(response.getString("name"));
                                    double i = innerresponse.getInt("temp");
                                    i = i - 273.15;
                                    int tmp = (int) (i * 100);
                                    i = tmp / 100;
                                    temp.setText(String.valueOf(i + "'C"));
                                    textView3.setText(lat + lon);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Log.d("myapp", "Something went Wrong");

                            }
                        });


                requestQueue.add(jsonObjectRequest);


//                    textView1.setText("ALL is set well");
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }


    }


    public void getLocation(View view) {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialise location coordinates
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //textView3.setText(Html.fromHtml("<font color='#06200EE'><b> Latitude: </b><br></font>"+ addresses.get(0).getLatitude()));
                        // textView3.setText(Html.fromHtml("<font color='#06200EE'><b> Longitude: </b><br></font>"+ addresses.get(0).getLongitude()));
//                        textView3.setText(Html.fromHtml("<font color='#06200EE'><b> Country: </b><br></font>"+ addresses.get(0).getCountryName()));
//                        textView4.setText(Html.fromHtml("<font color='#06200EE'><b> city: </b><br></font>"+ addresses.get(0).getLocality()));
//                        textView5.setText(Html.fromHtml("<font color='#06200EE'><b> Address: </b><br></font>"+ addresses.get(0).getAddressLine(0)));
                        double x=addresses.get(0).getLatitude();

                        int tmpp=(int)x*1000;

                        x=tmpp/1000;

                        double y=addresses.get(0).getLongitude();

                        tmpp=(int)y*1000;

                        y=tmpp/1000;

//                        lat=String.valueOf(addresses.get(0).getLatitude());
//                        lon=String.valueOf(addresses.get(0).getLongitude());

                        lat=String.valueOf(x);
                        lon=String.valueOf(y);

                        textView3.setText(lat+"    "+lon);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


}