package com.example.itthipon.test3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    APIInterface apiInterface;
    Switch switch_status;
    Boolean switch_status2 = false;
    private LocationManager locationManager;
    TextView tv_id;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 1, this);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        switch_status = (Switch) findViewById(R.id.switch_status);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_id.setText(getID());

        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        switch_status.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch_status2 = switch_status.isChecked();
            }
        });
    }

    public void onStart(){
        super.onStart();
        switch_status.setChecked(switch_status2);
        switch_status2 = switch_status.isChecked();

    }

    public void onResume(){
        super.onResume();
//        switch_status.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                switch_status2 = switch_status.isChecked();
//            }
//        });
    }

    private void updateLocationToServer(Location location) {
        Call<UpdateLocationRespond> call = apiInterface.updateLocation(
                99999,
                location.getLatitude(),
                location.getLongitude(),
                System.currentTimeMillis(),
                getHDOP(location),
                location.getAltitude(),
                location.getSpeed()
        );
        call.enqueue(new Callback<UpdateLocationRespond>() {
            @Override
            public void onResponse(Call<UpdateLocationRespond> call, Response<UpdateLocationRespond> response) {
                JsonObject data = response.body().data;
            }

            @Override
            public void onFailure(Call<UpdateLocationRespond> call, Throwable t) {

            }
        });
    }

    public int getHDOP(Location location) {
        int horiAcc = (int) (location.getAccuracy());
        return (int) (horiAcc / 5);
    }

    public String getID(){
        String id = "99999";
        return id;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(switch_status2 == true){
            updateLocationToServer(location);
        }

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
