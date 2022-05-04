package com.example.projectfare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

         tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
         viewPager = (ViewPager) view.findViewById(R.id.viewPager);

         viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
         viewPager.setAdapter(viewPagerAdapter);

         //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
         tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}