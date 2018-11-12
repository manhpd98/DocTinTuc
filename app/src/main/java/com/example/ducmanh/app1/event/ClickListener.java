package com.example.ducmanh.app1.event;

import com.example.ducmanh.app1.model.News;


public interface ClickListener {

    interface NewOnClick{
        void switchFragentSave(News news);
        News getNews();
    }

}
