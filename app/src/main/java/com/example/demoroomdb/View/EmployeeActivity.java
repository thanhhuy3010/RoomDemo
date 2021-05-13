package com.example.demoroomdb.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.demoroomdb.Common.Logger.LoggerManager;
import com.example.demoroomdb.Entity.Employee;
import com.example.demoroomdb.R;
import com.example.demoroomdb.ViewModel.EmployeeViewModel;

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
        // Put initial data into the word list.

        log = LoggerManager.getInstance(getApplicationContext());
        log.Debug(TAG, "On create home page");

        /**
         * RecyclerView find id, setting layout.
         */
        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new ListAdapter(this.getApplicationContext(), mEmployees);
            // Connect the adapter with the RecyclerView.
            // Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);

        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        employeeViewModel.getAllEmployee().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                mAdapter.setEmployee(employees);
            }
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
        if (item.getItemId() == R.id.add_item) onOpenDialog();
        return true;
    }

    private void onOpenDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogAddContact addContact = DialogAddContact.newInstance("Adding Contact");
        addContact.show(fragmentManager,addContact.TAG);
    }
}
