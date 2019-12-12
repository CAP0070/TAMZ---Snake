package com.example.snake;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class Geolocator implements LocationListener {
    private LocationManager locationManager;
    private String provider = "";
    String city = "";

    void setGeo() {
        locationManager = (LocationManager) SnakeActivity.getInstance().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (SnakeActivity.getInstance().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                SnakeActivity.getInstance().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(SnakeActivity.getInstance(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(SnakeActivity.getInstance(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(SnakeActivity.getInstance(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
            if (ContextCompat.checkSelfPermission(SnakeActivity.getInstance(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(SnakeActivity.getInstance(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {

                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(SnakeActivity.getInstance(),
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
            Log.d(TAG, "setGeo: kurva");
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
//You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        try {
            Geocoder gcd = new Geocoder(SnakeActivity.getInstance(), Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Log.d(TAG, "setGeo: " + addresses.get(0).getLocality());
                city = addresses.get(0).getLocality();
            }
            else {
                // do your stuff
            }
            Log.d(TAG, "onLocationChanged LAT: " + lat);
            Log.d(TAG, "onLocationChanged LNG: " + lng);
//            Log.d(TAG, "onLocationChanged CITY: " + fnialAddress);

        } catch (Exception e) {
            System.out.println(e);
        }

        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {

        }


    }

    @Override
    public void onLocationChanged(Location location) {
        //You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(SnakeActivity.getInstance(), Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            Geocoder gcd = new Geocoder(SnakeActivity.getInstance(), Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                city = addresses.get(0).getLocality();
            }
            else {
                // do your stuff
            }
            Log.d(TAG, "onLocationChanged LAT: " + lat);
            Log.d(TAG, "onLocationChanged LNG: " + lng);
//            Log.d(TAG, "onLocationChanged CITY: " + fnialAddress);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled: " + provider);
    }

}
