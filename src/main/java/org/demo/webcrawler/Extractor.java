package org.demo.webcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public class Extractor {

//    private static final String URL = "https://news.ycombinator.com/";

    public HashSet<Entry> getItemList(Document document) {
        HashSet<Entry> entries = new HashSet<>();
//        try {
//            Document document = Jsoup.connect(URL).get();
        Element tbody = document.selectFirst("table.itemlist > tbody");
        Elements elements = tbody.children();
        int i = 0;
        Element element = elements.first();
        Entry entry = new Entry();
        while (i < 30 && element != null) {

            if (element.hasClass("athing")) {
                Element rank = element.selectFirst(".rank");
                entry.setNumberOfOrder(Integer.parseInt(rank.text().substring(0, rank.text().length() - 1)));

                Element title = element.selectFirst(".storylink");
                entry.setTitle(title.text());

                element = element.nextElementSibling();

                Element score = element.selectFirst(".score");
                entry.setNumberOfOrder(Integer.parseInt(score.text().substring(0, score.text().indexOf("point") - 1)));

                Element comments = element.selectFirst("a[href^=\"item\"]");
                entry.setCommentsAmount(Integer.parseInt(comments.text().substring(0, score.text().indexOf("comment") - 1)));

                entries.add(entry);
                i++;
            }
            element = element.nextElementSibling();

        }
        return entries;

//        } catch (IOException e) {
//            throw new WebCrawlerException("Can't connect to the URL: " + URL + ". Please make sure you have an active internet connection.");
//        }
    }
}
