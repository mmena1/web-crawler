package org.demo.webcrawler.scraping;

import org.demo.webcrawler.WebCrawlerTest;
import org.demo.webcrawler.entity.NewsEntry;
import org.demo.webcrawler.service.Scraper;
import org.demo.webcrawler.util.JsoupWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScraperTest extends WebCrawlerTest {

    @Autowired
    private Scraper scraper;

    @MockBean
    private JsoupWrapper jsoupWrapper;

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