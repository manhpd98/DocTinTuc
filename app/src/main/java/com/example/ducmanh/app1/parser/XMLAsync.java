package com.example.ducmanh.app1.parser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.ducmanh.app1.model.News;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 19/5/2018.
 */

public class XMLAsync extends AsyncTask<String, Void, ArrayList<News>> {

    public static final int WHAT_DATA =1;
    private Handler handler;
    private ProgressDialog dialog;

    public XMLAsync(Context context,Handler handler) {
        this.handler = handler;
        dialog = new ProgressDialog(context);
    }


    @Override
    protected ArrayList<News> doInBackground(String... strings) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            String link = strings[0];
            parser.parse(link, handler);
            return handler.getListNews();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        Message message = new Message();
        message.what = WHAT_DATA;
        message.obj= news;
        handler.sendMessage(message);
        dialog.dismiss();
    }


}
