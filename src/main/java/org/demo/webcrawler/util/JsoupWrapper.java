package org.demo.webcrawler.util;

import org.demo.webcrawler.exception.WebCrawlerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsoupWrapper {

    @Value("${url.news-entries}")
    private String newsUrl;

    public Document connect() throws WebCrawlerException {
        try {
            return Jsoup.connect(newsUrl).get();
        } catch (IOException e) {
            throw new WebCrawlerException("Can't connect to the URL: " + newsUrl + ". Please make sure you have an active internet connection.");
        }
    }
}
