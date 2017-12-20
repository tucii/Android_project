package com.example.atalante.sos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class HelpScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    ImageButton sosButton;
    TextView shownumber1;
    EditText message;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private  Double myLatitude;
    private  Double myLongtitude;
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;
    private boolean permissionIsGranted = false;
    SharedPrefs sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

      sp = new SharedPrefs(HelpScreen.this);
       // sp.setString("lattitude:", "37.422");
      // sp.setString("longtitude:", "45");
        String x = sp.getString("lattitude:");
        String y = sp.getString("longtitude:");

        Toast.makeText(
                HelpScreen.this,
                "x:" + x,
                Toast.LENGTH_SHORT )
                .show();








        shownumber1 = (TextView) findViewById(R.id.shownumber1);
        sosButton = (ImageButton) findViewById(R.id.sosButton);
        message = (EditText) findViewById(R.id.message);
        shownumber1.setText(getIntent().getStringExtra("Phone1"));
        sosButton.setEnabled(false);
        googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(15 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        message.setText("Help me, I'm here!!!  https://www.google.com.tr/maps/place/" + (x) + "," + (y));




        if (checkPermission(Manifest.permission.SEND_SMS)) {
            sosButton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);

        }






        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phonenumber = shownumber1.getText().toString();
                String msg = message.getText().toString();



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
                } else {
                    permissionIsGranted = true;
                }

                if (!TextUtils.isEmpty(phonenumber) && (!TextUtils.isEmpty(msg))) {
                    if (checkPermission(Manifest.permission.SEND_SMS)) {

                        android.telephony.gsm.SmsManager sm = android.telephony.gsm.SmsManager.getDefault();
                        sm.sendTextMessage(phonenumber, phonenumber, msg, null, null);
                        //  SmsManager smsManager = SmsManager.getDefault();
                        //  smsManager.sendTextMessage(phonenumber, null, msg, null, null);

                    } else {
                        Toast.makeText(HelpScreen.this, "Permission denied", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(HelpScreen.this, "Enter a phone number and message ", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();

    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongtitude = location.getLongitude();

        message.setText("Help me, I'm here!!!  https://www.google.com.tr/maps/place/" + String.valueOf(myLatitude) + "," + String.valueOf(myLongtitude));
        sp.setString("x" , String.valueOf(myLatitude) );
        sp.setString("y", String.valueOf(myLongtitude));


    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionIsGranted) {
            if (googleApiClient.isConnected()) {
                requestLocationUpdates();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (permissionIsGranted)
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (permissionIsGranted)
            googleApiClient.disconnect();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
           case MY_PERMISSION_REQUEST_FINE_LOCATION:
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permissionIsGranted = true;
            }
            else {
                    permissionIsGranted = false;
                    Toast.makeText(getApplicationContext(), "This app requires location permission to be granted", Toast.LENGTH_SHORT).show();
                    message.setText("Location permission not granted");
            }


            case SEND_SMS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sosButton.setEnabled(true);
                }

            case MY_PERMISSION_REQUEST_COARSE_LOCATION:

                break;
        }

    }



}





