package org.demo.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExtractorTest {

    private static final String URL = "https://news.ycombinator.com/";

    private Document document;

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

    @Test
    public void getEntriesTest() {
        Extractor extractor = new Extractor();
        HashSet<Entry> itemList = extractor.getItemList(document);
        assertThat(itemList.size(), is(30));
    }
}