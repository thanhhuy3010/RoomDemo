package com.example.demoroomdb.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Users;
import com.example.demoroomdb.view.ListAdapter;
import com.example.demoroomdb.viewmodel.EmployeeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class ListFragment extends BaseFragment {

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
     * Create instance Firebase User to reference to Real Time database - Firebase
     */
    private FirebaseUser firebaseUser;

    /**
     * Create employee linked list to observer data.
     */
    private final List<Users> mEmployees = new LinkedList<>();

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
        mRecyclerView = view.findViewById(R.id.recyclerview);
//        mAdapter = new ListAdapter(getContext(), mEmployees);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        displayUser();

//        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
//        employeeViewModel.getAllEmployee().observe(getViewLifecycleOwner(), employees -> mAdapter.setEmployee(employees));

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
                Users users = mAdapter.getPosition(position);
                Toast.makeText(getContext(), "Delete success user: " + users.getId(),Toast.LENGTH_SHORT).show();
//                employeeViewModel.delete(users);
                mEmployees.remove(users);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void displayUser( ) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Users users = dataSnapshot.getValue(Users.class);
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (!users.getId().equals(firebaseUser.getUid())) {
                        mEmployees.add(users);
                    }

                    mAdapter = new ListAdapter(getContext(), mEmployees);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
