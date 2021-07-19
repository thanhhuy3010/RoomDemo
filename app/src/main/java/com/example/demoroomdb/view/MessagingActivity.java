package com.example.demoroomdb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.demoroomdb.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MessagingActivity extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    TextView tvUsernameOnToolBar;
    String friendId;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        toolbar = findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvUsernameOnToolBar = findViewById(R.id.title);
        friendId = getIntent().getStringExtra("friendId");
    }

}