package com.sdf.jd;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author barry
 * @date 2021/9/2 2:35 下午
 * @description
 */
public class JDDetailProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").all();

        Long skuId =
            Long.valueOf(page.getHtml().xpath("//div[@class='left-btns']/a/@data-id").toString());
        String skuName = page.getHtml().xpath("//div[@class='sku-name']/text()").toString();
       /* Integer cid1 ;
        String cid1Name;
        Integer cid2;
        String cid2Name;
        Integer cid3;
        String cid3Name;
        String propCode;
        String wareQD;
        String wdis;*/
        List<JDGoods.UrlInfo> imageList = new ArrayList<>();
        JDGoods jdGoods = JDGoods.builder().skuId(skuId).skuName(skuName)
            /*.categoryInfo(
            JDGoods.CategoryInfo.builder().cid1(cid1).cid1Name(cid1Name).cid2(cid2)
                .cid2Name(cid2Name).cid3(cid3).cid3Name(cid3Name).build()).baseBigFieldInfo(
            JDGoods.BaseBigFieldInfo.builder().propCode(propCode).wareQD(wareQD).wdis(wdis).build())*/
            .imageInfo(JDGoods.ImageInfo.builder().imageList(imageList).build()).build();
        System.out.println(jdGoods);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
