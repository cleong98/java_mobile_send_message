package com.example.sendmessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //init variable
    EditText etPhone, etMessage;
    Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        etPhone = findViewById(R.id.et_phone);
        etMessage = findViewById(R.id.et_message);
        btSend = findViewById(R.id.bt_send);

        //set button listener
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check condition
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    //when permission is granted
                    //create method
                    sendMessage();
                } else {
                    //when permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
    }

    private void sendMessage() {
        // get value from edit text
        String sPhone = etPhone.getText().toString().trim();
        String sMessage = etMessage.getText().toString().trim();

        //check condition
        if(!sPhone.equals("") && !sMessage.equals("")) {
            //when both edit text value are not equal to blank
            //init sms manager
            SmsManager smsManager = SmsManager.getDefault();
            //sent text message
            smsManager.sendTextMessage(sPhone, null, sMessage, null, null);
            //display toast
            Toast.makeText(getApplicationContext(), "SMS send successfully", Toast.LENGTH_LONG).show();
        } else {
            //when edit text value is blank
            Toast.makeText(getApplicationContext(), "Enter value first.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if (requestCode == 100  && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //when permission is granted
            sendMessage();
        } else {
            //when permission is denied
            Toast.makeText(getApplicationContext(), "Permission Denied !", Toast.LENGTH_SHORT).show();
        }
    }
}