package com.example.ducmanh.app1.model;

/**
 * Created by Administrator on 19/5/2018.
 */

public class News {

    private String id;
    private String title;
    private String desc;
    private String img;
    private String pubDate;
    private String link;
    private String type;


    public News(String id, String title, String desc, String img, String pubDate, String link, String type) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.pubDate = pubDate;
        this.link = link;
        this.type = type;
    }

    public News(String title, String desc, String img, String pubDate, String link) {
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.pubDate = pubDate;
        this.link = link;
    }

    public News() {
        //for parse
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
