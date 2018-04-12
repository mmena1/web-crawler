package org.demo.webcrawler.service;


import org.demo.webcrawler.entity.NewsEntry;
import org.demo.webcrawler.exception.WebCrawlerException;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilterService {

    @Autowired
    private Scraper scraper;


    public List<NewsEntry> filterEntriesWithMoreThanFiveWords() throws WebCrawlerException {
        Map<Integer, NewsEntry> itemList = scraper.getItemList();
        return itemList.values().stream()
                .filter(newsEntry -> newsEntry.getTitle().split(" ").length > 5)
                .sorted(Comparator.comparingInt(NewsEntry::getCommentsAmount).reversed())
                .collect(Collectors.toList());
    }

    public List<NewsEntry> filterEntriesWithLessThanOrEqualToFiveWords() throws WebCrawlerException {
        Map<Integer, NewsEntry> itemList = scraper.getItemList();
        return itemList.values().stream()
                .filter(newsEntry -> newsEntry.getTitle().split(" ").length <= 5)
                .sorted(Comparator.comparingInt(NewsEntry::getScore).reversed())
                .collect(Collectors.toList());
    }

}
