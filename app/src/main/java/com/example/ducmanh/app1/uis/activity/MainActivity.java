package com.example.ducmanh.app1.uis.activity;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ducmanh.app1.R;
import com.example.ducmanh.app1.adapter.PagerAdapter;
import com.example.ducmanh.app1.dao.SQLiteHelper;
import com.example.ducmanh.app1.event.ClickListener;
import com.example.ducmanh.app1.model.News;
import com.example.ducmanh.app1.uis.fragment.FragmentSave;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private SQLiteHelper mDB;
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private News mNews = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDB = new SQLiteHelper(this);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                getSupportActionBar().setTitle("Tin Tức");
            case 1:
                getSupportActionBar().setTitle("Đã Lưu");
            case 2:
                getSupportActionBar().setTitle("Yêu Thích");
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
