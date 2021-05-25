package com.example.demoroomdb.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.demoroomdb.model.Common.Logger.LoggerManager;
import com.example.demoroomdb.model.Entity.Employee;
import com.example.demoroomdb.R;
import com.example.demoroomdb.ViewModel.EmployeeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.LinkedList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private LoggerManager log;
    private final String TAG = EmployeeActivity.class.getSimpleName();
    private BaseFragment fragment;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                break;

        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, fragment);
        fragmentTransaction.commit();
        return true;
    }
}
