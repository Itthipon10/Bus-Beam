package com.example.itthipon.test3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Itthipon on 7/14/2017.
 */

public class GpsTracker extends Manage implements LocationListener {

    Context context;

    public GpsTracker(Context context) {
        super();
        this.context = context;

    }

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            Log.e("fist","error");
            return null;
        }
        try {
            LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000,10,this);
                Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return loc;
            }else{
                Log.e("sec","errpr");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onLocationChanged(Location location) {
//        t = new Timestamp(System.currentTimeMillis());
        long time = System.currentTimeMillis();
        location.getSpeed();
        location.getAltitude();
        Log.d("current time",String.valueOf(time));
        int horiAcc=(int)(location.getAccuracy());
        int hd = (int) (horiAcc/5);
//        Log.d("current location",String.valueOf(location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
