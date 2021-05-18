package com.example.demoroomdb.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.ViewModel.EmployeeViewModel;
import com.example.demoroomdb.model.Entity.Employee;

import java.util.LinkedList;
import java.util.List;

public class ListFragment extends BaseFragment{

    public static final String TAG = ListFragment.class.getSimpleName();

    private static final String INSTANCE_VALUE = "argument";

    /**
     * Calling recyclerview instance.
     */
    private RecyclerView mRecyclerView;

    /**
     * Calling employee view model - create communicate between Model - View.
     */
    EmployeeViewModel employeeViewModel;

    /**
     * Create list adapter instance.
     */
    private ListAdapter mAdapter;

    /**
     * Create employee linked list to observer data.
     */
    private final List<Employee> mEmployees = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
    }

    @Override
    protected int layoutResource() {
        return R.layout.list_view_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new ListAdapter(getContext(), mEmployees);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        employeeViewModel.getAllEmployee().observe(getViewLifecycleOwner(), employees -> mAdapter.setEmployee(employees));
    }

    public ListFragment() {}

    public static ListFragment newInstance(String args) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INSTANCE_VALUE, args);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
