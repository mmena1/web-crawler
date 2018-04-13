package org.demo.webcrawler.service;


import org.demo.webcrawler.entity.NewsEntry;
import org.demo.webcrawler.exception.WebCrawlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for filtering the scraped entries
 *
 * @author martin
 */
@Service
public class FilterService {

    @Autowired
    private Scraper scraper;


    /**
     * Filers all the scraped entries with more than five words in their title and sorts them by amount of comments in descending order
     *
     * @return a list of filtered and ordered entries
     * @throws WebCrawlerException in case there is connection issues
     */
    public List<NewsEntry> filterEntriesWithMoreThanFiveWords() throws WebCrawlerException {
        Map<Integer, NewsEntry> itemList = scraper.getItemList();
        return itemList.values().stream()
                .filter(newsEntry -> newsEntry.getTitle().split(" ").length > 5)
                .sorted(Comparator.comparingInt(NewsEntry::getCommentsAmount).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Filers all the scraped entries with five or less words in their title and sorts them by points in descending order
     *
     * @return a list of filtered and ordered entries
     * @throws WebCrawlerException in case there is connection issues
     */
    public List<NewsEntry> filterEntriesWithLessThanOrEqualToFiveWords() throws WebCrawlerException {
        Map<Integer, NewsEntry> itemList = scraper.getItemList();
        return itemList.values().stream()
                .filter(newsEntry -> newsEntry.getTitle().split(" ").length <= 5)
                .sorted(Comparator.comparingInt(NewsEntry::getScore).reversed())
                .collect(Collectors.toList());
    }

}
