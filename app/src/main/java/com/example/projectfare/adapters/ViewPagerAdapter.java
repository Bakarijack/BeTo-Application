package com.example.projectfare.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projectfare.hometablayoutfragments.OneFragment;
import com.example.projectfare.hometablayoutfragments.ThreeFragment;
import com.example.projectfare.hometablayoutfragments.TwoFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new OneFragment();
        else if (position == 1)
            fragment = new TwoFragment();
        else if (position == 2)
            fragment = new ThreeFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

//    @Override
//    public CharSequence getPageTitle(int position)
//    {
//        String title = null;
//        if (position == 0)
//            title = "one";
//        else if (position == 1)
//            title = "two";
//        else if (position == 2)
//            title = "three";
//        return title;
//    }
}
