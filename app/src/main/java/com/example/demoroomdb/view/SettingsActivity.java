package com.example.demoroomdb.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.demoroomdb.R;
import com.example.demoroomdb.view.adapter.SettingViewPager;
import com.example.demoroomdb.view.fragment.ProfileFragment;
import com.example.demoroomdb.view.fragment.SettingsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private SettingViewPager settingViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        toolbar = findViewById(R.id.toolbar_setting);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingViewPager = new SettingViewPager(this);
        settingViewPager.addFragment(ProfileFragment.newInstance("PROFILE"),"PROFILE");
        settingViewPager.addFragment(SettingsFragment.newInstance("SETTING"),"APP SETTING");
        viewPager.setAdapter(settingViewPager);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Profile");
                    break;
                case 1:
                    tab.setText("App setting");
                    break;
            }
        }).attach();

    }

}