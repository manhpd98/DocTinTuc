package com.example.ducmanh.app1.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ducmanh.app1.uis.fragment.FragmentLike;
import com.example.ducmanh.app1.uis.fragment.FragmentNew;
import com.example.ducmanh.app1.uis.fragment.FragmentSave;

public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentNew.getInstance();
            case 1:
                return FragmentSave.getInstance();
            default:
                return  FragmentLike.getInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TIN TỨC";
            case 1:
                return "ĐÃ LƯU";
            default:
                return "YÊU THÍCH";
        }
    }
}
