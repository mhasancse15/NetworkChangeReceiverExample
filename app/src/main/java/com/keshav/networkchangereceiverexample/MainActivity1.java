package com.keshav.networkchangereceiverexample;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.keshav.networkchangereceiverexample.receivers.NetworkChangeReceiver;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity1 extends AppCompatActivity {

    private BroadcastReceiver mNetworkReceiver;
    static Thread thread;
    static TextView tv_check_connection;
    static Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_check_connection=(TextView) findViewById(R.id.tv_check_connection);

        animation = AnimationUtils.loadAnimation(MainActivity1.this,R.anim.blink);
        // Could not Connect to internet

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        Log.e("Keshav","MainActivity onCreate called");
    }

    public static void dialog(boolean value){

        if(value){
            tv_check_connection.setText("We are back !!!");
            tv_check_connection.setBackgroundColor(Color.GREEN);
            tv_check_connection.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);


        }else {
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Could not Connect to internet");
            tv_check_connection.setBackgroundColor(Color.RED);
            tv_check_connection.setTextColor(Color.WHITE);
//            callAnimation();
        }
    }


    public  static void callAnimation()
    {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tv_check_connection.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_check_connection.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tv_check_connection.startAnimation(animation);
    }


    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            Log.e("Keshav","MainActivity registerNetworkBroadcastForNougat called Nougat ");
        } if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            Log.e("Keshav","MainActivity registerNetworkBroadcastForNougat called MarshMallow ");
        }

        Log.e("Keshav","MainActivity registerNetworkBroadcastForNougat called");
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        Log.e("Keshav","MainActivity unregisterNetworkChanges called");
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
        Log.e("Keshav","MainActivity onDestroy called");
    }
}


/*

<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>

*/