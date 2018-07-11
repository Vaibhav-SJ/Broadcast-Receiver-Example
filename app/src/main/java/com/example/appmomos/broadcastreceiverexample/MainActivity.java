package com.example.appmomos.broadcastreceiverexample;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    Button btn;
    TextView textView;
    EditText editText;
    NotificationCompat.Builder builder;
    BroadcastReceiver receiver;
    IntentFilter filter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPreference",MODE_PRIVATE);


        btn = findViewById(R.id.btnShow);
        textView = findViewById(R.id.txt);
        editText = findViewById(R.id.txtMsg);

        if (sharedPreferences.contains("editTextValue"))
        {
            editText.setText(sharedPreferences.getString("editTextValue",""));
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcastFun();
            }
        });

        // creating intent filter
        filter = new IntentFilter();
        filter.addAction("My Custom BroadCast");


        receiver = new BroadcastReceiver()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onReceive(Context context, Intent intent)
            {
                textView.setText ("Received msg is : \n"+intent.getStringExtra("msg"));


                builder = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Received msg is")
                        .setContentText(intent.getStringExtra("msg"));

                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                assert manager != null;
                manager.notify(0, builder.build());


            }
        };

    }



    @Override
    protected void onPause() {
        super.onPause();
        editor = sharedPreferences.edit();
        editor.putString("editTextValue",editText.getText().toString().trim());
        editor.apply();
    }


    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver,filter);
    }


    //send broadcast fun
    private void sendBroadcastFun()
    {
        Intent intent = new Intent();
        intent.putExtra("msg",editText.getText().toString().trim());
        intent.setAction("My Custom BroadCast");
        sendBroadcast(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}
