package com.example.demoroomdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Users;
import com.example.demoroomdb.utils.Defined;
import com.example.demoroomdb.utils.Utils;
import com.example.demoroomdb.view.fragment.BaseFragment;
import com.example.demoroomdb.view.fragment.CameraFragment;
import com.example.demoroomdb.view.fragment.ListFragment;
import com.example.demoroomdb.view.fragment.MessagesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class EmployeeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private final String TAG = EmployeeActivity.class.getSimpleName();
    private BaseFragment fragment;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    TextView tvUserName;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    Utils utils;

    private void setPermission () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        setPermission();
        utils = Utils.getInstance(this);
        tvUserName = findViewById(R.id.usernameonmainactivity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.menu_messages);
        }
//        fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment
//                , MessagesFragment.newInstance("MESSAGES", EmployeeActivity.this));
//        fragmentTransaction.commit();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                tvUserName.setText(users.getUsername());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d(TAG, "Receive Failed Notification : " + task.getException());
                return;
            }
            String token = task.getResult();
            Log.d(TAG, "Receive data Notification : " + task.getException());
            Log.d(TAG, "Token : " + token);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on Resume");
        status(Defined.ACCOUNT_STATUS_ONLINE);
//        utils.status(Defined.ACCOUNT_STATUS_ONLINE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG,"on Create option menu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                onOpenDialog();
                break;
            case R.id.setting_item:
                startActivity(new Intent(EmployeeActivity.this, SettingsActivity.class));
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void onOpenDialog() {
        DialogAddContact addContact = DialogAddContact.newInstance("Adding Contact");
        addContact.show(fragmentManager,addContact.TAG);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_messages:
                fragment = MessagesFragment.newInstance("MESSAGES", EmployeeActivity.this);
                break;
            case R.id.menu_list:
                fragment = ListFragment.newInstance("LIST");
                break;
            case R.id.menu_more:
                fragment = CameraFragment.newInstance("CAMERA");
                break;
            default:
                break;
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, fragment);
        fragmentTransaction.commit();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Pause Employee");
//        utils.status(Defined.ACCOUNT_STATUS_OFFLINE);
        status(Defined.ACCOUNT_STATUS_OFFLINE);
    }

    private void status(String status) {
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> statusMap = new HashMap<>();
        statusMap.put("status", status);
        dbRef.updateChildren(statusMap);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Stop Employee");
    }
}
