package org.demo.webcrawler.util;

import org.demo.webcrawler.exception.WebCrawlerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Wrapper class for connecting the Jsoup library to the URL defined in application.yml.
 * The main purpose of this class is to facilitate the tests so that the connect() method can be mocked.
 *
 * @author martin
 */
@Service
public class JsoupWrapper {

    @Value("${url.news-entries}")
    private String newsUrl;

    /**
     * Returns a {@link Document} object which represents the content of the URL defined in application.yml.
     *
     * @return a Document object
     * @throws WebCrawlerException in case there is connection issues
     */
    public Document connect() throws WebCrawlerException {
        try {
            return Jsoup.connect(newsUrl).get();
        } catch (IOException e) {
            throw new WebCrawlerException("Can't connect to the URL: " + newsUrl + ". Please make sure you have an active internet connection.");
        }
    }
}
