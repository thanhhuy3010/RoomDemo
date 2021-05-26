package com.example.demoroomdb.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoroomdb.R;


public class CameraFragment extends BaseFragment{

    public static final String TAG = CameraFragment.class.getSimpleName();

    private static final String INSTANCE_VALUE = "camera_argument";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Running to Camera Fragment");
    }

    @Override
    protected int layoutResource() {
        return R.layout.canmera_fragment;
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

    public CameraFragment() {}

    public static CameraFragment newInstance(String args) {
        CameraFragment cameraFragment = new CameraFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INSTANCE_VALUE, args);
        cameraFragment.setArguments(bundle);
        return cameraFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
