package com.sdf.jd;

import com.alibaba.excel.EasyExcel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.xsoup.Xsoup;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author barry
 * @date 2021/9/1 4:26 下午
 * @description
 */
public class JDPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(2000).setTimeOut(10000);

    private Set<String> allUrls = Collections.synchronizedSet(new HashSet<>());

    private Set<String> allDetailUrls = Collections.synchronizedSet(new HashSet<>());

    static Spider spider;

    static String fileDetailUrl = JDPageProcessor.class.getResource("").getPath() + "detail.xlsx";

    static String driverPath = JDPageProcessor.class.getResource("").getPath() + "/chromedriver";

    static String configFile = JDPageProcessor.class.getResource("").getPath() + "/config.ini";

    @Override
    public void process(Page page) {
        //
        Document document = Jsoup.parse(page.getHtml().toString());

        List<String> userUrls =
            Xsoup.compile("//div[@class='sh-head-menu-922476']//a/@href").evaluate(document).list();

        List<String> hrefs = Xsoup.compile("//a/@href").evaluate(document).list();

        Set<String> collect =
            hrefs.stream().filter(href -> href.contains("item.jd.com")).map(href -> {
                if(!href.contains("http")) {
                    href = "https:" + href;
                }
                return href;
            }).collect(Collectors.toSet());
        allDetailUrls.addAll(collect);

        if (CollectionUtils.isEmpty(allUrls)) {
            Set<String> jd_hrefs = userUrls.stream().filter(href -> !href.contains("item.jd.com"))
                .collect(Collectors.toSet());
            allUrls.addAll(jd_hrefs);
            for (String allUrl : allUrls) {
                page.addTargetRequest(allUrl);
            }
        }
        page.putField("allDetailUrls", allDetailUrls);

        //运行结束
        if (spider.getScheduler().poll(spider) == null) {
            List<DetailUrlHead> list = new ArrayList<>();
            for (String allDetailUrl : allDetailUrls) {
                DetailUrlHead head = new DetailUrlHead();
                head.setUrl(allDetailUrl);
                list.add(head);
            }
            EasyExcel.write(fileDetailUrl,DetailUrlHead.class).sheet("detail").doWrite(list);
            //执行新的 爬虫 获取详情
            Spider.create(new JDDetailProcessor()).thread(1).startUrls(new ArrayList(allDetailUrls))
                .setDownloader(new SeleniumDownloader(driverPath))
                .run();
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入店铺地址:");
        // https://apple.jd.com/view_search-394872-0-0-1-24-2.html
        // https://mall.jd.com/index-1000004259.html?from=pc
        String url = scanner.nextLine();
        System.out.println("scan page beginning...");

        System.getProperties().setProperty("selenuim_config", configFile);
        spider = Spider.create(new JDPageProcessor()).thread(1).addUrl(url)
            .setDownloader(new SeleniumDownloader(driverPath));
        spider.run();

    }
}
