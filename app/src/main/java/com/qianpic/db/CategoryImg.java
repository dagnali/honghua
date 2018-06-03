package com.qianpic.db;

/**
 * Created by admin on 2018/4/5.
 */

public class CategoryImg {

    private int id;
    private String cate;

    private String name;
    private String link;

    public CategoryImg()
    {

    }

    public CategoryImg(String name,String link,String cate)
    {
        this.name=name;
        this.link=link;
        this.cate=cate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
