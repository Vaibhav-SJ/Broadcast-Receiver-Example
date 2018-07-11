package com.example.appmomos.broadcastreceiverexample;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ChargerDisconnectedBroadCastReceiver extends BroadcastReceiver
{

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Charger Removed",Toast.LENGTH_SHORT).show();
    }
}
