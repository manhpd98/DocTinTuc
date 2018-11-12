package com.example.ducmanh.app1.uis.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.ducmanh.app1.R;
import com.example.ducmanh.app1.adapter.NewAdapter;
import com.example.ducmanh.app1.model.News;
import com.example.ducmanh.app1.parser.XMLAsync;
import com.example.ducmanh.app1.uis.BaseFragment;
import com.example.ducmanh.app1.uis.activity.MainActivity;
import com.example.ducmanh.app1.uis.activity.WebActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 23/5/2018.
 */

public class FragmentNew extends BaseFragment implements NewAdapter.OnItemClick {

    private static FragmentNew fragmentNew;
    private ArrayList<News> listNews = new ArrayList<>();
    private NewAdapter adapter;
    private RecyclerView lvNews;
    private String keyWord="";

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public static FragmentNew getInstance(){
        if(fragmentNew == null){
            fragmentNew = new FragmentNew();
        }
        return fragmentNew;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.new_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
    }

    private void initview() {
        lvNews = getActivity().findViewById(R.id.lv);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvNews.setLayoutManager(manager);
        adapter = new NewAdapter(listNews, getActivity());
        lvNews.setAdapter(adapter);
        adapter.setOnItemClick(this);

    }

    private void parseData() {
        XMLAsync xmlAsync = new XMLAsync(getActivity(),handler);
        keyWord = Uri.encode(keyWord);
        String link = "https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=" +keyWord ;
        xmlAsync.execute(link);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == XMLAsync.WHAT_DATA){
                ArrayList<News> arr = (ArrayList<News>) msg.obj;
                listNews.clear();
                listNews.addAll(arr);
                listNews.remove(0);
                adapter.notifyDataSetChanged();
            }
        }
    };


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tin Tức");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FragmentNew.getInstance().setKeyWord(query);
                FragmentNew.getInstance().parseData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("link", listNews.get(position).getLink());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, News news) {
        FragmentSave.getInstance().getNewsSave(news);
//        MainActivity.viewPager.setCurrentItem(1);
    }
}
