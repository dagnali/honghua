package com.qianpic.db;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2018/5/18.
 */

public class ImgBig extends DataSupport {

    private int id;
    private String cate;
    private int page;
    private String name;
    private String link;
    private String imgLink;

    public ImgBig()
    {

    }

    public ImgBig(String name,String link,String cate)
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getImglink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

}
