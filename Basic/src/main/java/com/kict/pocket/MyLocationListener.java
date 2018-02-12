package com.kict.pocket;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Fitri San on 11/25/2017.
 */

public class MyLocationListener implements LocationListener{

    public String get_lat;
    public String get_long;

    @Override
    public void onLocationChanged(Location location) {
        get_lat = String.valueOf(location.getLatitude());
        get_long = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
//        Toast.makeText(this, "GPS enabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
//        Toast.makeText(this, "GPS disabled", Toast.LENGTH_LONG).show();
    }
}
