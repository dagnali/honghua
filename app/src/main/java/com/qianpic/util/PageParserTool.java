package com.qianpic.util;

import android.content.Context;

import com.qianpic.db.Img;
import com.qianpic.db.ImgBig;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by admin on 2018/1/14.
 */

public class PageParserTool {


    private Context mContext;



    /* 通过选择器来选取页面的 */
    public static Elements select(Page page , String cssSelector) {
        return page.getDoc().select(cssSelector);
    }

    /*
     *  通过css选择器来得到指定元素;
     *
     *  */
    public static Element select(Page page , String cssSelector, int index) {
        Elements eles = select(page , cssSelector);
        int realIndex = index;
        if (index < 0) {
            realIndex = eles.size() + index;
        }
        return eles.get(realIndex);
    }


    /**
     * 获取满足选择器的元素中的链接 选择器cssSelector必须定位到具体的超链接
     * 例如我们想抽取id为content的div中的所有超链接，这里
     * 就要将cssSelector定义为div[id=content] a
     *  放入set 中 防止重复；
     * @param cssSelector
     * @return
     */
    public static Set<String> getLinks(Page page , String cssSelector) {
        Set<String> links  = new HashSet<String>() ;
        Elements es = select(page , cssSelector);
        Iterator iterator  = es.iterator();
        while(iterator.hasNext()) {
            Element element = (Element) iterator.next();
            if ( element.hasAttr("href") ) {
                links.add(element.attr("abs:href"));
            }else if( element.hasAttr("src") ){
                links.add(element.attr("abs:src"));
            }
        }
        return links;
    }

    /**
     * 获取满足选择器的元素中的链接 选择器cssSelector必须定位到具体的超链接
     * 例如我们想抽取id为content的div中的所有超链接，这里
     * 就要将cssSelector定义为div[id=content] a
     *  放入set 中 防止重复；
     * @param cssSelector
     * @return
     */
    public static void getImgLists(Page page , String cssSelector,String cate,int p,Context mContext) {

        Set<String> links  = new HashSet<String>() ;
        Elements es = select(page , cssSelector);
        Iterator iterator  = es.iterator();
        while(iterator.hasNext()) {
            Element element = (Element) iterator.next();

            if ( element.getElementsByTag("img").hasAttr("href") ) {
                if(links.add(element.getElementsByTag("img").attr("abs:href")))
                {
                    Img img=new Img();
                    img.setLink(element.getElementsByTag("img").attr("abs:href"));
                    img.setName(element.getElementsByTag("a").attr("title"));
                    img.setCate(cate);
                    img.setPage(p);
                    img.setImgLink(element.getElementsByTag("a").attr("href"));
                    img.save();
                }
            }else if( element.getElementsByTag("img").hasAttr("src") ){
                if(links.add(element.getElementsByTag("img").attr("abs:src")))
                {
                    Img img=new Img();
                    img.setLink(element.getElementsByTag("img").attr("abs:src"));
                    img.setName(element.getElementsByTag("a").attr("title"));
                    img.setCate(cate);
                    img.setPage(p);
                    img.setImgLink(element.getElementsByTag("a").attr("href"));
                    img.save();

                }
            }
        }


    }


    /**
     * 获取满足选择器的元素中的链接 选择器cssSelector必须定位到具体的超链接
     * 例如我们想抽取id为content的div中的所有超链接，这里
     * 就要将cssSelector定义为div[id=content] a
     *  放入set 中 防止重复；
     * @param cssSelector
     * @return
     */
    public static void getImgBigLists(Page page , String cssSelector,String cate,int p,Context mContext) {

        Set<String> links  = new HashSet<String>() ;
        Elements es = select(page , cssSelector);
        if(es!=null) {
            Iterator iterator = es.iterator();

            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                if (element.getElementsByTag("img").hasAttr("href")) {
                    if (links.add(element.getElementsByTag("img").attr("abs:href"))) {
                        ImgBig img = new ImgBig();
                        img.setLink(element.getElementsByTag("img").attr("abs:href"));
                        img.setName(element.getElementsByTag("a").attr("title"));
                        img.setCate(cate);
                        img.setPage(p);
                        img.setImgLink(element.getElementsByTag("a").attr("href"));
                        img.save();
                    }
                } else if (element.getElementsByTag("img").hasAttr("src")) {
                    if (links.add(element.getElementsByTag("img").attr("abs:src"))) {
                        ImgBig img = new ImgBig();
                        img.setLink(element.getElementsByTag("img").attr("abs:src"));
                        img.setName(element.getElementsByTag("a").attr("title"));
                        img.setCate(cate);
                        img.setPage(p);

                        img.save();

                    }
                }
            }
        }

    }

    /**
     * 获取网页中满足指定css选择器的所有元素的指定属性的集合
     * 例如通过getAttrs("Img[src]","abs:src")可获取网页中所有图片的链接
     * @param cssSelector
     * @param attrName
     * @return
     */
    public static ArrayList<String> getAttrs(Page page , String cssSelector, String attrName) {
        ArrayList<String> result = new ArrayList<String>();
        Elements eles = select(page ,cssSelector);
        for (Element ele : eles) {
            if (ele.hasAttr(attrName)) {
                result.add(ele.attr(attrName));
            }
        }
        return result;
    }
}
