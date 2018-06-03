package com.qianpic.db;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 2018/1/16.
 */

public class Page extends DataSupport{
    private int id;
    private String content;
    private String url;
    private String contentType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
