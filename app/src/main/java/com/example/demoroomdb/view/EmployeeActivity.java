package com.example.demoroomdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.example.demoroomdb.model.Common.Logger.LoggerManager;
import com.example.demoroomdb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class EmployeeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private LoggerManager log;
    private final String TAG = EmployeeActivity.class.getSimpleName();
    private BaseFragment fragment;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

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
        log = LoggerManager.getInstance(getApplicationContext());
        log.Debug(TAG, "On create home page");
        fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.frame_fragment, ListFragment.newInstance("LIST-FRAG"));
        fragmentTransaction.commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        setPermission();

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

            Toast.makeText(EmployeeActivity.this,"Token : " + token, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG,"on Create option menu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu,menu);
        return true;
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
            case R.id.menu_list:
                fragment = ListFragment.newInstance("LIST");
                break;
            case R.id.menu_scanner:
                Toast.makeText(getApplicationContext(), "Click scanner fragment",Toast.LENGTH_SHORT).show();
                fragment = CameraFragment.newInstance("CAMERA");
                break;
            case R.id.menu_more:
                Toast.makeText(getApplicationContext(), "Click setting fragment",Toast.LENGTH_SHORT).show();
                fragment = CameraFragment.newInstance("CAMERA");
                break;
            default:
                break;
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, fragment);
        fragmentTransaction.commit();
        return true;
    }
}
