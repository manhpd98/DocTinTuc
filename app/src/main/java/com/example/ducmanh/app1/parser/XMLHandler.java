package com.example.ducmanh.app1.parser;

import com.example.ducmanh.app1.model.News;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Administrator on 19/5/2018.
 */

public class XMLHandler extends DefaultHandler {

    private ArrayList<News> listNews = new ArrayList<>();
    private News news;
    private final String TAG_ITEM = "item";
    private final String TAG_TITLE = "title";
    private final String TAG_DESC = "description";
    private final String TAG_DATE = "pubDate";
    private final String TAG_LINK = "link";
    private StringBuffer value;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals(TAG_ITEM)) {
            news = new News();

        }
        value = new StringBuffer();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (news == null) {
            return;
        }
        switch (qName) {
            case TAG_TITLE:
                news.setTitle(value.toString());
                break;
            case TAG_DESC:
                String src = "src=\"";
                if (value.indexOf(src) ==-1){
                    break;
                }else {
                    int index = value.indexOf(src);
                    String desc = value.substring(index + src.length());
                    index = desc.indexOf("\"");
                    String img = desc.substring(0, index);
                    String br = "</font><br><font size=\"-1\">";
                    index = desc.indexOf(br);
                    desc = desc.substring(index + br.length());
                    news.setImg("http:"+img);
                    news.setDesc(desc);
                }
                break;
            case TAG_LINK:
                String url = "url=";
                int index1 = value.indexOf(url);
                String link = value.substring(index1 + url.length());
                news.setLink(link);
                break;
            case TAG_DATE:
                news.setPubDate(value.toString());
                break;
            case TAG_ITEM:
                listNews.add(news);
                break;
        }
    }

    public ArrayList<News> getListNews() {
        return listNews;
    }
}
