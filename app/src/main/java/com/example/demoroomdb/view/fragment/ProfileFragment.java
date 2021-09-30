package com.example.demoroomdb.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Users;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment {
    private static final String INSTANCE_VALUE = "argument";
    @Override
    protected int layoutResource() {return R.layout.fragment_profile;}

    public static ProfileFragment newInstance(String args) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INSTANCE_VALUE, args);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public ProfileFragment() {}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
