package org.demo.webcrawler.rest;

import org.demo.webcrawler.exception.WebCrawlerException;
import org.demo.webcrawler.service.FilterService;
import org.demo.webcrawler.service.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * REST controller which exposes services' operations as a RESTful API with JSON responses
 *
 * @author martin
 */
@RestController
public class NewsEntryController {

    @Autowired
    private FilterService filterService;

    @Autowired
    private Scraper scraper;

    /**
     * Exposes all scraped entries in JSON format
     *
     * @return a collection of entries
     * @throws WebCrawlerException in case there is connection issues
     */
    @RequestMapping("/entries")
    public Collection getNewsEntries() throws WebCrawlerException {
        return scraper.getItemList().values();
    }

    /**
     * Exposes all filtered entries in JSON format
     *
     * @return a collection of filtered entries
     * @throws WebCrawlerException in case there is connection issues
     */
    @RequestMapping("/entries/filterMoreThanFiveWords")
    public Collection getFilteredEntriesMoreThanFiveWords() throws WebCrawlerException {
        return filterService.filterEntriesWithMoreThanFiveWords();
    }

    /**
     * Exposes all filtered entries in JSON format
     *
     * @return a collection of filtered entries
     * @throws WebCrawlerException in case there is connection issues
     */
    @RequestMapping("/entries/filterLessThanSixWords")
    public Collection getFilteredEntriesLessThanSixWords() throws WebCrawlerException {
        return filterService.filterEntriesWithLessThanOrEqualToFiveWords();
    }

}
