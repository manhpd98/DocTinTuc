package com.example.ducmanh.app1.uis.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
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


public class FragmentLike extends BaseFragment implements SaveAdapter.OnItemClick {

    private SQLiteHelper mDB;
    private RecyclerView mRecyclerView;
    private SaveAdapter mAdapter;
    private static FragmentLike fragmentLike;
    private ArrayList<News> mNewList;

    public static FragmentLike getInstance() {
        if (fragmentLike == null) {
            fragmentLike = new FragmentLike();
        }
        return fragmentLike;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.like_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        setMangDB();
        mRecyclerView = view.findViewById(R.id.lv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SaveAdapter(mNewList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClick(this);
    }

    private void setMangDB() {
        mNewList = new ArrayList<>();
        mDB = new SQLiteHelper(getActivity());
        ArrayList<News> data = mDB.getAllNew();
        for (News x : data) {
            if (x.getType().equals("LIKE")) {
                mNewList.add(x);
            }
        }

    }


    public void getNews(News news) {
        for (News x : mNewList) {
            if (x.getTitle().equalsIgnoreCase(news.getTitle())) {
                Toast.makeText(getActivity(), "Bài viết đã có!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        mDB.addNews(news, "LIKE");
        Toast.makeText(getContext(), "Đã thêm vào yêu thích!", Toast.LENGTH_SHORT).show();
        mNewList.add(news);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("link", mNewList.get(position).getLink());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(final int position, News news, View view) {
        PopupMenu menu = new PopupMenu(getActivity(), view);
        menu.inflate(R.menu.poupup_menu_like);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                        mNewList.remove(position);
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
