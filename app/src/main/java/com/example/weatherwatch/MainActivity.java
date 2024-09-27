package com.example.weatherwatch;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherwatch.Common.Common;
import com.example.weatherwatch.Helper.Helper;
import com.example.weatherwatch.Model.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView txtCity, txtLastUpdated, txtDescription, txtHumidity, txtSunset,txtSunrise, txtCelsious;
    TextView txtWind, txtFeelsLike;
    ImageView imageView;
    LinearLayout rootLayout;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    int MY_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Control
        txtCity = findViewById(R.id.txtcity);
        txtCelsious = findViewById(R.id.txtCelsius);
        txtDescription = findViewById(R.id.txtDiscription);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtLastUpdated = findViewById(R.id.txtLastUpdate);
        txtSunrise = findViewById(R.id.txtSunrise);
        txtSunset = findViewById(R.id.txtSunset);
        txtWind = findViewById(R.id.txtWind);
        txtFeelsLike = findViewById(R.id.txtFeelsLike);
        rootLayout = findViewById(R.id.main);



        imageView = findViewById(R.id.imageView);

        // Get Cordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG", "No Location");


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

//    private class GetWeather extends AsyncTask<String, Void, String> {
//
//        ProgressDialog pd = new ProgressDialog(MainActivity.this);
//
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pd.setTitle("Please wait...");
//            pd.show();
//        }
//
//        protected String doInBackground(String... params) {
//            String stream = null;
//            String urlString = params[0];
//
//            Helper http = new Helper();
//            stream = http.getHTTPData(urlString);
//            return stream;
//
//        }
//
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            if (s.contains("Error: Not found city")){
//                pd.dismiss();
//                return;
//            }
//            Gson gson = new Gson();
//            Type mtype = new TypeToken<OpenWeatherMap>(){}.getType();
//            openWeatherMap = gson.fromJson(s, mtype);
//            pd.dismiss();
//
//            txtCity.setText(String.format("%s, %s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
//            txtLastUpdated.setText(String.format("Last update: %s", Common.getDateNow()));
//            txtDescription.setText(String.format("%s",openWeatherMap.getWeatherList().get(0).getDescription()));
//            txtHumidity.setText(String.format("Humidity: %d",openWeatherMap.getMain().getHumidity()));
//            txtTime.setText(String.format("%s/%s",Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()),Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
//            txtCelsious.setText((String.format("%.2f°C",openWeatherMap.getMain().getTemp())));
//
//            Picasso.get()
//                    .load(Common.getImage(openWeatherMap.getWeatherList().get(0).getIcon()))
//                    .into(imageView);
//            }
//        }
private class GetWeather {

    ProgressDialog pd = new ProgressDialog(MainActivity.this);
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public void execute(String... params) {
        // Simulate AsyncTask's execute method
        onPreExecute();
        executorService.execute(() -> {
            String result = doInBackground(params);
            handler.post(() -> onPostExecute(result));
        });
    }

    protected void onPreExecute() {
        pd.setTitle("Please wait...");
        pd.show();
    }

    protected String doInBackground(String... params) {
        String stream = null;
        String urlString = params[0];

        Helper http = new Helper();
        stream = http.getHTTPData(urlString);
        return stream;
    }

    protected void onPostExecute(@NonNull String s) {
        if (s.contains("Error: Not found city")) {
            pd.dismiss();
            return;
        }
        Gson gson = new Gson();
        Type mtype = new TypeToken<OpenWeatherMap>(){}.getType();
        openWeatherMap = gson.fromJson(s, OpenWeatherMap.class);
        pd.dismiss();

        txtCity.setText(String.format("%s, %s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
        txtLastUpdated.setText(String.format("%s", Common.getDateNow()));
        txtHumidity.setText(String.format("%d%%", openWeatherMap.getMain().getHumidity()).toUpperCase());
        txtSunrise.setText(String.format("%s", Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise())));
        txtSunset.setText(String.format("%s", Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
        txtCelsious.setText(String.format("%.1f°", openWeatherMap.getMain().getTemp()));
        txtWind.setText(String.format("%.1f m/s", openWeatherMap.getWind().getSpeed()));
        txtFeelsLike.setText(String.format("%.1f°", openWeatherMap.getMain().getFeels_like()));


        if (openWeatherMap.getWeather() != null && !openWeatherMap.getWeather().isEmpty()) {
            txtDescription.setText(String.format("%s", openWeatherMap.getWeather().get(0).getDescription()).toUpperCase());
            Picasso.get()
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(imageView);
        } else {
            txtDescription.setText("No weather data available");
            // You might want to set a default image for imageView here as well
        }
        Log.d("WeatherApp", "Temp: " + openWeatherMap.getMain().getTemp());
        Log.d("WeatherApp", "Temp Min: " + openWeatherMap.getMain().getTemp_min());
        Log.d("WeatherApp", "Temp Max: " + openWeatherMap.getMain().getTemp_max());
    }
    }
}