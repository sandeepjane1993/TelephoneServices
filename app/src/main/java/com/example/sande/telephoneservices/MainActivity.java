package com.example.sande.telephoneservices;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2, tv3;
    Button button;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        tv1 = findViewById(R.id.textView_IEMI);
        tv2 = findViewById(R.id.textView_SWV);
        tv3 = findViewById(R.id.textView_PhNum);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);



        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
               String imei =  telephonyManager.getImei();
                tv1.setText("imei number : " + imei);
                String softwareVersion = telephonyManager.getDeviceSoftwareVersion();
                tv2.setText("software Version : " + softwareVersion);
                String phNum = telephonyManager.getLine1Number();
                tv3.setText("phone number : " + phNum);

                telephonyManager.
            }
        });
    }
}
