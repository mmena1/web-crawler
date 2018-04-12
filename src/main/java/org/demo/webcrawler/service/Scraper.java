package org.demo.webcrawler.service;

import org.demo.webcrawler.entity.NewsEntry;
import org.demo.webcrawler.exception.WebCrawlerException;
import org.demo.webcrawler.util.JsoupWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Scraper {

    @Autowired
    private JsoupWrapper jsoupWrapper;

    public Map<Integer, NewsEntry> getItemList() throws WebCrawlerException {
        Document document = jsoupWrapper.connect();
        Map<Integer, NewsEntry> entries = new HashMap<>();
        Element tbody = document.selectFirst("table.itemlist > tbody");
        Elements elements = tbody.children();
        int i = 0;
        Element element = elements.first();
        while (i < 30 && element != null) {
            NewsEntry newsEntry = createEntry(element);
            if (newsEntry != null) {
                i++;
                entries.put(newsEntry.getNumberOfOrder(), newsEntry);
            }
            element = element.nextElementSibling();
        }
        return entries;

    }

    private NewsEntry createEntry(Element element) {
        NewsEntry newsEntry = new NewsEntry();
        if (element.hasClass("athing")) {
            Element rank = element.selectFirst(".rank");
            newsEntry.setNumberOfOrder(Integer.parseInt(rank.text().substring(0, rank.text().length() - 1)));

            Element title = element.selectFirst(".storylink");
            newsEntry.setTitle(title.text());

            element = element.nextElementSibling();

            Element score = element.selectFirst(".score");
            newsEntry.setScore(Integer.parseInt(score.text().substring(0, score.text().indexOf("point") - 1)));

            Element comments = element.selectFirst(".subtext > a[href^=\"item\"]");
            if (comments.text().contains("comment")) {
                newsEntry.setCommentsAmount(Integer.parseInt(comments.text().substring(0, comments.text().indexOf("comment") - 1)));
            }

            return newsEntry;
        }
        return null;
    }
}
