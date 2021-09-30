package com.example.demoroomdb.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class SettingViewPager extends FragmentStateAdapter {
    ArrayList<Fragment> fragments;
    ArrayList<String> titles;
    public SettingViewPager(@NonNull FragmentActivity fm) {
        super(fm);
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void addFragment (Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

}
