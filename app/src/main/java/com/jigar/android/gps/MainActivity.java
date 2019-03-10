package com.jigar.android.gps;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager_gps;

    Button btn_camera;
    EditText ed_vehcle_no,ed_model_no,ed_customer_nm;
    double latti, logitu;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_camera = (Button) findViewById(R.id.btn_camera);

        ed_customer_nm = (EditText)findViewById(R.id.edit_custName);
        ed_vehcle_no = (EditText)findViewById(R.id.edit_vehicalNo);
        ed_model_no = (EditText)findViewById(R.id.edit_ModelNo);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
            locationManager_gps = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            getLocation();
        }
        else
        {
            showGPSDisabledAlertToUser();
        }
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    if(ed_customer_nm.getText().toString().equals(""))
                    {
                        ed_customer_nm.setError("Please enter customer name");
                        ed_customer_nm.requestFocus();
                    }
                    else if(ed_vehcle_no.getText().toString().equals(""))
                    {
                        ed_vehcle_no.setError("Please enter vehicle number");
                        ed_vehcle_no.requestFocus();
                    }
                    else if(ed_model_no.getText().toString().equals(""))
                    {
                        ed_model_no.setError("Please enter model number");
                        ed_model_no.requestFocus();
                    }
                    else
                    {
                        Intent myIntent = new Intent(MainActivity.this, CameraActivity.class);
                        myIntent.putExtra("key_latitude", String.valueOf(latti));
                        myIntent.putExtra("key_logitude", String.valueOf(logitu));
                        myIntent.putExtra("key_vehical_no",ed_vehcle_no.getText().toString());
                        myIntent.putExtra("key_model", ed_model_no.getText().toString());
                        myIntent.putExtra("key_cusomer_nm", ed_customer_nm.getText().toString());
                        startActivity(myIntent);
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "GPS is Disable(Please Switch on your GPS)", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        Settings.ACTION_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager_gps.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latti = location.getLatitude();
                logitu = location.getLongitude();

              //  textview_fieldLatitude.setText((String.valueOf(latti)));
               // textview_fieldLongitude.setText((String.valueOf(logitu)));
            } else
            {
               // textview_fieldLatitude.setText("Enable to connect lattitude");
               // textview_fieldLongitude.setText("Enable to connect logtitude");
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }
}

