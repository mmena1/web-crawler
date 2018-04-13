package org.demo.webcrawler.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsEntryControllerTest {

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
        List<NewsEntry> filteredList = new ArrayList<>();
        filteredList.add(new NewsEntry(4, "Title With More than five words 2", 145, 102));
        String jsonResult = objectMapper.writeValueAsString(filteredList);
        given(filterService.filterEntriesWithMoreThanFiveWords()).willReturn(filteredList);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/entries/filterMoreThanFiveWords"), String.class);
        NewsEntry[] newsEntries = objectMapper.readValue(response.getBody(), NewsEntry[].class);

        // then
        assertThat(newsEntries.length).isEqualTo(1);
        assertThat(response.getBody()).isEqualTo(jsonResult);
    }

    @Test
    public void shouldReturnEntriesWithLessThanSixWords() throws Exception {

        // given
        List<NewsEntry> filteredList = new ArrayList<>();
        filteredList.add(new NewsEntry(4, "Title With More than five words 2", 145, 102));
        String jsonResult = objectMapper.writeValueAsString(filteredList);
        given(filterService.filterEntriesWithLessThanOrEqualToFiveWords()).willReturn(filteredList);

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/entries/filterLessThanSixWords"), String.class);
        NewsEntry[] newsEntries = objectMapper.readValue(response.getBody(), NewsEntry[].class);

        // then
        assertThat(newsEntries.length).isEqualTo(1);
        assertThat(response.getBody()).isEqualTo(jsonResult);
    }
}