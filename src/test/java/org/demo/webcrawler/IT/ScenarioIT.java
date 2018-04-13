package org.demo.webcrawler.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.webcrawler.entity.NewsEntry;
import org.demo.webcrawler.util.JsoupWrapper;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract class for integration tests of the scenarios
 *
 * @author martin
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ScenarioIT {

    protected Document document;

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JsoupWrapper jsoupWrapper;

    /**
     * Creates the url in which to test the RESTful API
     *
     * @param uri An endpoint of the API
     * @return the url to test the API
     */
    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/web-crawler" + uri;
    }

    /**
     * Tests the response of filtering entries with more than five words in their title, is correctly filtered and ordered
     *
     * @param orderedComments Ordered array with the amount of comments of each entry that should be validated
     * @param totalFiltered   the total number of filtered entries
     * @throws Exception in case something goes wrong
     */
    protected void filterEntriesWithMoreThanFiveWords(int[] orderedComments, int totalFiltered) throws Exception {
        // given
        int i = 0;
        // when
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/entries/filterMoreThanFiveWords"), String.class);
        NewsEntry[] newsEntries = objectMapper.readValue(response.getBody(), NewsEntry[].class);

        // then
        assertThat(newsEntries.length).isEqualTo(totalFiltered);

        for (NewsEntry newsEntry : newsEntries) {
            assertThat(newsEntry.getCommentsAmount()).isEqualTo(orderedComments[i]);
            assertThat(newsEntry.getTitle().split(" ").length).isGreaterThan(5);
            i++;
        }
    }

    /**
     * Tests the response of filtering entries with five or less words in their title, is correctly filtered and ordered
     *
     * @param orderedScore  Ordered array with the score of each entry that should be validated
     * @param totalFiltered the total number of filtered entries
     * @throws Exception in case something goes wrong
     */
    protected void filterEntriesWithLessThanSixWords(int[] orderedScore, int totalFiltered) throws Exception {

        // given
        int i = 0;
        // when
        ResponseEntity<String> response = restTemplate.getForEntity(createURLWithPort("/entries/filterLessThanSixWords"), String.class);
        NewsEntry[] newsEntries = objectMapper.readValue(response.getBody(), NewsEntry[].class);

        // then
        assertThat(newsEntries.length).isEqualTo(totalFiltered);

        for (NewsEntry newsEntry : newsEntries) {
            assertThat(newsEntry.getScore()).isEqualTo(orderedScore[i]);
            assertThat(newsEntry.getTitle().split(" ").length).isLessThanOrEqualTo(5);
            i++;
        }

    }
}
