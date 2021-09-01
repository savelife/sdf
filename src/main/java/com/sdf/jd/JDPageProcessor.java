package com.sdf.jd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author barry
 * @date 2021/9/1 4:26 下午
 * @description
 */
public class JDPageProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        // 获取商品列表url 分页数据url
        List<String> detailUrls = new ArrayList<>();
        //List<JSONObject> allProduct = new ArrayList<>();
        /*detailUrls.forEach(detailUrl -> {

        });*/
        Document document = Jsoup.parse(page.getHtml().toString());




        List<String> result = Xsoup.compile("//div[@class=jPage]/a/@href").evaluate(document).list();
        if(!page.getUrl().equals(result.get(result.size()-1))) {
            //page.putField("nextUrl", result.get(result.size()-1));
            // 下一页 document.getElementsByClassName('jPage')[0].lastChild.baseURI
            page.addTargetRequest(result.get(result.size()-1));
        }


        // 下一页 document.getElementsByClassName('jPage')[0].lastChild.baseURI
        //page.addTargetRequest(page.getHtml());


    }

    @Override
    public Site getSite() {
        return null;
    }


}
