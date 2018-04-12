package org.demo.webcrawler.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.webcrawler.WebCrawlerTest;
import org.demo.webcrawler.entity.NewsEntry;
import org.demo.webcrawler.service.FilterService;
import org.demo.webcrawler.service.Scraper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsEntryControllerTest extends WebCrawlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private FilterService filterService;

    @MockBean
    private Scraper scraper;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/web-crawler" + uri;
    }

    @Test
    public void shouldReturnAllEntries() throws Exception {

        // given
        Map<Integer, NewsEntry> itemList = new HashMap<>();
        itemList.put(1, new NewsEntry(1, "Title 1", 21, 44));
        itemList.put(2, new NewsEntry(2, "Title 2", 5, 30));
        itemList.put(3, new NewsEntry(3, "Title 3", 105, 12));
        String jsonResult = objectMapper.writeValueAsString(itemList.values());
        given(scraper.getItemList()).willReturn(itemList);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/entries"), String.class);
        NewsEntry[] newsEntries = objectMapper.readValue(response.getBody(), NewsEntry[].class);

        // then
        assertThat(newsEntries.length).isEqualTo(3);
        assertThat(response.getBody()).isEqualTo(jsonResult);
    }

    @Test
    public void shouldReturnOnlyEntriesWithMoreThanFiveWords() throws Exception {

        // given
        Map<Integer, NewsEntry> itemList = new HashMap<>();
        itemList.put(1, new NewsEntry(1, "Title With More than five words 1", 21, 44));
        itemList.put(2, new NewsEntry(2, "Less than five words", 5, 30));
        itemList.put(3, new NewsEntry(3, "Title 3", 105, 12));
        itemList.put(4, new NewsEntry(4, "Title With More than five words 2", 145, 102));
        itemList.put(5, new NewsEntry(5, "Title With More than five words 3", 69, 35));
        String jsonResult = objectMapper.writeValueAsString(itemList.values());

        Map<Integer, NewsEntry> filteredMap = new HashMap<>();
        filteredMap.put(4, new NewsEntry(4, "Title With More than five words 2", 145, 102));
        filteredMap.put(1, new NewsEntry(1, "Title With More than five words 1", 21, 44));
        filteredMap.put(5, new NewsEntry(5, "Title With More than five words 3", 69, 35));
        String jsonFilteredResult = objectMapper.writeValueAsString(filteredMap.values());
        given(scraper.getItemList()).willReturn(itemList);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/entries/filterMoreThanFiveWords"), String.class);
        NewsEntry[] newsEntries = objectMapper.readValue(response.getBody(), NewsEntry[].class);

        // then
        assertThat(newsEntries.length).isEqualTo(3);
        assertThat(response.getBody()).isNotEqualTo(jsonResult);
        assertThat(response.getBody()).isEqualTo(jsonFilteredResult);
    }
}