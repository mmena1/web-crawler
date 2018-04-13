package org.demo.webcrawler.service;


import org.demo.webcrawler.entity.NewsEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterServiceTest {

    @Autowired
    private FilterService filterService;

    @MockBean
    private Scraper scraper;

    @Test
    public void shouldFilterEntriesWithMoreThanFiveWords() throws Exception {
        // given
        Map<Integer, NewsEntry> itemList = new HashMap<>();
        itemList.put(1, new NewsEntry(1, "Title With More than five words 1", 21, 44));
        itemList.put(2, new NewsEntry(2, "Less than five words", 5, 30));
        itemList.put(3, new NewsEntry(3, "Title 3", 105, 12));
        itemList.put(4, new NewsEntry(4, "Title With More than five words 2", 145, 102));
        itemList.put(5, new NewsEntry(5, "Title With More than five words 3", 69, 35));
        Integer[] orderedComments = {102, 44, 35};
        int i = 0;
        given(scraper.getItemList()).willReturn(itemList);

        // when
        List<NewsEntry> newsEntries = filterService.filterEntriesWithMoreThanFiveWords();

        // then
        assertThat(newsEntries.size()).isEqualTo(orderedComments.length);
        for (NewsEntry newsEntry : newsEntries) {
            assertThat(newsEntry.getCommentsAmount()).isEqualTo(orderedComments[i]);
            i++;
        }


    }

    @Test
    public void shouldFilterEntriesWithLessThanOrEqualToFiveWords() throws Exception {
        // given
        Map<Integer, NewsEntry> itemList = new HashMap<>();
        itemList.put(1, new NewsEntry(1, "Title With More than five words 1", 21, 44));
        itemList.put(2, new NewsEntry(2, "Less than five words", 5, 30));
        itemList.put(3, new NewsEntry(3, "Title 3", 105, 12));
        itemList.put(4, new NewsEntry(4, "Title With More than five words 2", 145, 102));
        itemList.put(5, new NewsEntry(5, "Title With five words 1", 69, 35));
        Integer[] orderedScore = {105, 69, 5};
        int i = 0;
        given(scraper.getItemList()).willReturn(itemList);

        // when
        List<NewsEntry> newsEntries = filterService.filterEntriesWithLessThanOrEqualToFiveWords();

        // then
        assertThat(newsEntries.size()).isEqualTo(orderedScore.length);
        for (NewsEntry newsEntry : newsEntries) {
            assertThat(newsEntry.getScore()).isEqualTo(orderedScore[i]);
            i++;
        }

    }

}