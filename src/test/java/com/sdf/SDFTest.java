package com.sdf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import us.codecraft.xsoup.Xsoup;

import java.util.List;

/**
 * @author barry
 * @date 2021/9/1 5:52 下午
 * @description
 */

public class SDFTest {

    @Test
    public void test() {
            String html = "<html><div><div><a href='https://github.com'>github.com</a>"
                + "<a href='https://github.cn'>github.com</a></div></div>" +
                "<table><tr><td>a</td><td>b</td></tr></table></html>";

            Document document = Jsoup.parse(html);

            List<String> result = Xsoup.compile("//a/@href").evaluate(document).list();
            Assert.assertEquals("https://github.cn", result.get(1));

            List<String> list = Xsoup.compile("//tr/td/text()").evaluate(document).list();
            Assert.assertEquals("a", list.get(0));
            Assert.assertEquals("b", list.get(1));
    }
}
