package com.example.demoroomdb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.demoroomdb.R;

public class MessagingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
    }
}