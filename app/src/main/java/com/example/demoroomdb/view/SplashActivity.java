package com.example.demoroomdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.demoroomdb.R;

public class SplashActivity extends AppCompatActivity {
    private static final String KEY_ACTIVITY_CREATE_ONCE = "SplashActivityCreatedOnce";
    private static final String TAG = SplashActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getSupportActionBar().hide();
        if (savedInstanceState != null) {
            return;
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } catch (Exception e) {
                    Log.d(TAG,"Error: " + e.getMessage());
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ACTIVITY_CREATE_ONCE,true);
    }
}
