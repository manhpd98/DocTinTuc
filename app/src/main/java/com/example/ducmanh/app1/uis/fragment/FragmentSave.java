package com.example.ducmanh.app1.uis.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ducmanh.app1.R;
import com.example.ducmanh.app1.adapter.NewAdapter;
import com.example.ducmanh.app1.adapter.SaveAdapter;
import com.example.ducmanh.app1.dao.SQLiteHelper;
import com.example.ducmanh.app1.model.News;
import com.example.ducmanh.app1.uis.BaseFragment;
import com.example.ducmanh.app1.uis.activity.MainActivity;
import com.example.ducmanh.app1.uis.activity.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentSave extends BaseFragment implements SaveAdapter.OnItemClick {

    private SaveAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<News> mNewsList;
    private SQLiteHelper mDB;


    @Override
    protected int getLayoutID() {
        return R.layout.save_fragment;
    }
    private static FragmentSave fragmentSave;
    public static FragmentSave getInstance(){
        if(fragmentSave == null){
            fragmentSave = new FragmentSave();
        }
        return fragmentSave;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View view) {

        setMangDB();

        mRecyclerView = view.findViewById(R.id.lv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SaveAdapter(mNewsList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClick(this);
    }

    private void setMangDB(){
        mNewsList = new ArrayList<>();
        mDB = new SQLiteHelper(getActivity());
        ArrayList<News> data = mDB.getAllNew();
        for (News x : data){
            if(x.getType().equals("SAVE")){
                mNewsList.add(x);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void getNewsSave(News news){
        for (News x : mNewsList){
            if (x.getTitle().equalsIgnoreCase(news.getTitle())){
                Toast.makeText(getActivity(), "Bài viết đã có!", Toast.LENGTH_SHORT).show();
               return;
            }
        }
        mDB.addNews(news, "SAVE");
        Toast.makeText(getActivity(), "Đã Lưu", Toast.LENGTH_SHORT).show();
        mNewsList.add(news);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("link", mNewsList.get(position).getLink());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(final int position, News news, View view) {
        PopupMenu menu = new PopupMenu(getActivity(),view);
        menu.inflate(R.menu.poupup_menu_save);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.save:
                        FragmentLike.getInstance().getNews(mNewsList.get(position));
//                        MainActivity.viewPager.setCurrentItem(2);
                        break;
                    case R.id.delete:
                        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        mNewsList.remove(position);
                        mDB.delete_byID(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        menu.show();

    }

}
