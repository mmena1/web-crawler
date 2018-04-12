package org.demo.webcrawler.rest;

import org.demo.webcrawler.exception.WebCrawlerException;
import org.demo.webcrawler.service.FilterService;
import org.demo.webcrawler.service.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class NewsEntryController {

    @Autowired
    private FilterService filterService;

    @Autowired
    private Scraper scraper;

    @RequestMapping("/entries")
    public Collection getNewsEntries() throws WebCrawlerException {
        return scraper.getItemList().values();
    }

    @RequestMapping("/entries/filterMoreThanFiveWords")
    public Collection getFilteredEntriesMoreThanFiveWords() throws WebCrawlerException {
        return filterService.filterEntriesWithMoreThanFiveWords();
    }

    @RequestMapping("/entries/filterLessThanSixWords")
    public Collection getFilteredEntriesLessThanSixWords() throws WebCrawlerException {
        return filterService.filterEntriesWithLessThanOrEqualToFiveWords();
    }

}
