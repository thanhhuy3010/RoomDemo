package com.example.demoroomdb.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.viewmodel.EmployeeViewModel;
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
        Log.d(TAG, "Running to List Fragment");
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new ListAdapter(getContext(), mEmployees);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        employeeViewModel.getAllEmployee().observe(getViewLifecycleOwner(), employees -> mAdapter.setEmployee(employees));

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
                Toast.makeText(getContext(), "Delete success user: " + employee.getIdUser(),Toast.LENGTH_SHORT).show();
                employeeViewModel.delete(employee);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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
