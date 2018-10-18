package com.example.sande.telephoneservices;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextView tv1, tv2, tv3,tv4;
    Button button;
    TelephonyManager telephonyManager;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToSpeech = new TextToSpeech(MainActivity.this,this);
        button = findViewById(R.id.button);
        tv4 = findViewById(R.id.textView4);
        tv1 = findViewById(R.id.textView_IEMI);
        tv2 = findViewById(R.id.textView_SWV);
        tv3 = findViewById(R.id.textView_PhNum);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener psl = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);
                if(state == TelephonyManager.CALL_STATE_RINGING){
                    textToSpeech.speak("Your getting a call from this number : " + phoneNumber,TextToSpeech.QUEUE_FLUSH,null);
                    Toast.makeText(MainActivity.this, "caller is ringing" + phoneNumber, Toast.LENGTH_SHORT).show();
                }
                if(state == TelephonyManager.CALL_STATE_OFFHOOK)
                {
                    Toast.makeText(MainActivity.this, "call ended", Toast.LENGTH_SHORT).show();
                }
                if(state == TelephonyManager.CALL_STATE_IDLE)
                {
                    Toast.makeText(MainActivity.this, "caller is idle" , Toast.LENGTH_SHORT).show();
                }


            }
        };
        telephonyManager.listen(psl,PhoneStateListener.LISTEN_CALL_STATE);



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
                String data = tv4.getText().toString();
                textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);


            }
        });
    }

    @Override
    public void onInit(int status) {

        // check if it supports language etc
        if(status == TextToSpeech.SUCCESS)
        {
            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if(result ==    TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
            {
                Toast.makeText(this, "We do not support ur language", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
