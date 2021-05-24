package com.example.demoroomdb.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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

import java.util.LinkedList;
import java.util.List;

public class EmployeeActivity extends AppCompatActivity {
    private LoggerManager log;
    private RecyclerView mRecyclerView;

//    mock data tu DB len
    EmployeeViewModel employeeViewModel;
    private ListAdapter mAdapter;
    private final List<Employee> mEmployees = new LinkedList<>();
    private final String TAG = EmployeeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        log = LoggerManager.getInstance(getApplicationContext());
        log.Debug(TAG, "On create home page");

        /**
         * RecyclerView find id, setting layout.
         */
        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new ListAdapter(this.getApplicationContext(), mEmployees);
            // Connect the adapter with the RecyclerView.
            // Give the RecyclerView a default layout manager.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        employeeViewModel.getAllEmployee().observe(this, employees -> mAdapter.setEmployee(employees));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Employee employee = mAdapter.getPosition(position);
                Toast.makeText(getApplicationContext(), "Delete success user: " + employee.getIdUser(),Toast.LENGTH_SHORT).show();
                employeeViewModel.delete(employee);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_list:
                    Toast.makeText(getApplicationContext(), "Click list fragment",Toast.LENGTH_SHORT).show();
                case R.id.menu_scanner:
                    Toast.makeText(getApplicationContext(), "Click list fragment",Toast.LENGTH_SHORT).show();
                case R.id.menu_more:
                    Toast.makeText(getApplicationContext(), "Click list fragment",Toast.LENGTH_SHORT).show();
            }
            return true;
        }

    };

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogAddContact addContact = DialogAddContact.newInstance("Adding Contact");
        addContact.show(fragmentManager,addContact.TAG);
    }
}
