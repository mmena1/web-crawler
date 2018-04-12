package org.demo.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;

import java.io.File;


public abstract class WebCrawlerTest {

    protected Document document;

    @Before
    public void setUp() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File input = new File(classLoader.getResource("testDoc.html").getFile());
            document = Jsoup.parse(input, "UTF-8");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
