package com.example.itthipon.test3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    APIInterface apiInterface;

    Button btnLoc;
    ListView lv;
    ArrayAdapter<String> adapter;
    LinearLayout mainLayout,mainLayout2,mainLayout3;
    String[] data = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        apiInterface = APIClient.getClient().create(APIInterface.class);

//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mainLayout = (LinearLayout) findViewById(R.id.layout_1);
        mainLayout2 = (LinearLayout) findViewById(R.id.layout_2);
        mainLayout3 = (LinearLayout) findViewById(R.id.layout3);
        mainLayout2.setVisibility(LinearLayout.GONE);
        mainLayout3.setVisibility(LinearLayout.GONE);


        ArrayAdapter customAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, data);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(customAdapter);

        btnLoc = (Button) findViewById(R.id.btn_location);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = getLocation();
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(),"GPS Latitude = "+lat+"\n lontitude = "+lon,Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    private void updateLocationToServer(Context context, Location location) {
        Call<LoginRespone> call = apiInterface.updateLocation();
        call.enqueue(new Callback<LoginRespone>() {
            @Override
            public void onResponse(Call<LoginRespone> call, Response<LoginRespone> response) {
                JsonObject data = response.body().data;
            }

            @Override
            public void onFailure(Call<LoginRespone> call, Throwable t) {

            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mainLayout2.setVisibility(LinearLayout.GONE);
                    mainLayout3.setVisibility(LinearLayout.GONE);
                    mainLayout.setVisibility(LinearLayout.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    mainLayout.setVisibility(LinearLayout.GONE);
//                    mainLayout2.setVisibility(LinearLayout.VISIBLE);
                    mainLayout3.setVisibility(LinearLayout.VISIBLE);
                    return true;
            }
            return false;
        }

    };

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

        updateLocationToServer();
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
}
