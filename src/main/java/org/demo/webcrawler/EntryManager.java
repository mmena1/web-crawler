package org.demo.webcrawler;


import org.jsoup.nodes.Document;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntryManager {

    private EntryManager() {
    }

    private static final EntryManager INSTANCE = new EntryManager();

    public List<NewsEntry> filterEntriesWithMoreThanFiveWords(Document document) {
        Map<Integer, NewsEntry> itemList = Extractor.getINSTANCE().getItemList(document);
        return itemList.values().stream()
                .filter(newsEntry -> newsEntry.getTitle().split(" ").length > 5)
                .sorted(Comparator.comparingInt(NewsEntry::getCommentsAmount).reversed())
                .collect(Collectors.toList());
    }

    public List<NewsEntry> filterEntriesWithLessThanOrEqualToFiveWords(Document document) {
        Map<Integer, NewsEntry> itemList = Extractor.getINSTANCE().getItemList(document);
        return itemList.values().stream()
                .filter(newsEntry -> newsEntry.getTitle().split(" ").length <= 5)
                .sorted(Comparator.comparingInt(NewsEntry::getScore).reversed())
                .collect(Collectors.toList());
    }

    public static EntryManager getINSTANCE() {
        return INSTANCE;
    }
}
