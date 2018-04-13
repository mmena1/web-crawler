package org.demo.webcrawler.service;

import org.demo.webcrawler.entity.NewsEntry;
import org.demo.webcrawler.util.JsoupWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Tests the correct behaviour of the {@link Scraper} class.
 *
 * @author martin
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScraperTest {

    @Autowired
    private Scraper scraper;

    @MockBean
    private JsoupWrapper jsoupWrapper;

    private Document document;

    @Before
    public void setUp() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File input = new File(classLoader.getResource("scenario1.html").getFile());
            document = Jsoup.parse(input, "UTF-8");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldExtract30Entries() throws Exception {
        // given
        int totalEntries = 30;
        given(jsoupWrapper.connect()).willReturn(document);
        // when
        Map<Integer, NewsEntry> itemList = scraper.getItemList();
        // then
        assertThat(itemList.size()).isEqualTo(totalEntries);
    }

    @Test
    public void shouldGetTheCorrectEntry() throws Exception {
        // given
        int entryPosition = 5;
        given(jsoupWrapper.connect()).willReturn(document);

        // when
        Map<Integer, NewsEntry> itemList = scraper.getItemList();

        // then
        assertThat(itemList.get(entryPosition).getNumberOfOrder()).isEqualTo(entryPosition);
        assertThat(itemList.get(entryPosition).getTitle()).isEqualTo("Don't give away historic details about yourself");
        assertThat(itemList.get(entryPosition).getScore()).isEqualTo(221);
        assertThat(itemList.get(entryPosition).getCommentsAmount()).isEqualTo(110);

    }

}