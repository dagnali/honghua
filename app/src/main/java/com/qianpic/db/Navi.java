package com.qianpic.db;

/**
 * Created by admin on 2018/3/24.
 */

public class Navi {


    public Navi(String name){
        this.name=name;
    }

    private int id;
    private int cate;

    private String name;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
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
