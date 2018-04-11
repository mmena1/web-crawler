package org.demo.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExtractorTest {

    private static final Logger LOG = Logger.getLogger(ExtractorTest.class.getName());

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
    public void extractedEntriesIs30() {
        // given
        int totalEntries = 30;
        // when
        Map<Integer, NewsEntry> itemList = Extractor.getINSTANCE().getItemList(document);
        // then
        assertThat(itemList.size(), equalTo(totalEntries));
    }

    @Test
    public void validateAnEntry() {
        // given
        int entryPosition = 5;

        // when
        Map<Integer, NewsEntry> itemList = Extractor.getINSTANCE().getItemList(document);

        // then
        assertThat(itemList.get(entryPosition).getNumberOfOrder(), equalTo(entryPosition));
        assertThat(itemList.get(entryPosition).getTitle(), equalTo("Don't give away historic details about yourself"));
        assertThat(itemList.get(entryPosition).getScore(), equalTo(221));
        assertThat(itemList.get(entryPosition).getCommentsAmount(), equalTo(110));

    }
    @Test
    public void filterEntriesWithMoreThanFiveWords() {
        // given
        Integer[] orderedComments = {190, 134, 110, 93, 88, 67, 63, 39, 37, 32, 30, 26, 17, 16, 16, 10, 9, 9, 5, 4, 1, 0, 0, 0};
        int i = 0;

        // when
        List<NewsEntry> newsEntries = EntryManager.getINSTANCE().filterEntriesWithMoreThanFiveWords(document);

        // then
        assertThat(newsEntries.size(), equalTo(orderedComments.length));
        for (NewsEntry newsEntry : newsEntries) {
            assertThat(newsEntry.getCommentsAmount(), equalTo(orderedComments[i]));
            i++;
        }


    }

    @Test
    public void filterEntriesWithLessThanOrEqualToFiveWords() {
        // given
        Integer[] orderedScore = {503, 121, 77, 56, 45, 27};
        int i = 0;

        // when
        List<NewsEntry> newsEntries = EntryManager.getINSTANCE().filterEntriesWithLessThanOrEqualToFiveWords(document);

        // then
        assertThat(newsEntries.size(), equalTo(orderedScore.length));
        for (NewsEntry newsEntry : newsEntries) {
            assertThat(newsEntry.getScore(), equalTo(orderedScore[i]));
            i++;
        }

    }
}