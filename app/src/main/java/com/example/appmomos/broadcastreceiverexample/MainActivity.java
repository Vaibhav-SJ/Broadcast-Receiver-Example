package com.example.appmomos.broadcastreceiverexample;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

    BroadcastReceiver receiver;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnShow);
        textView = findViewById(R.id.txt);
        editText = findViewById(R.id.txtMsg);

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
            }
        };

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


}
