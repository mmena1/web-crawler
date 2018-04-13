package org.demo.webcrawler.IT;

import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class Scenario2Test extends ScenarioIT {

    @Before
    public void setUp() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File input = new File(classLoader.getResource("scenario2.html").getFile());
            document = Jsoup.parse(input, "UTF-8");
            given(jsoupWrapper.connect()).willReturn(document);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldReturnOnlyEntriesWithMoreThanFiveWords() throws Exception {

        int[] orderedComments = {256, 253, 248, 176, 74, 72, 71, 71, 59, 57, 52, 38, 37, 36, 34, 27, 18, 15, 14, 3, 1, 0, 0, 0, 0};
        filterEntriesWithMoreThanFiveWords(orderedComments, 25);

    }

    @Test
    public void shouldReturnEntriesWithLessThanSixWords() throws Exception {

        int[] orderedScore = {255, 80, 67, 25, 15};
        filterEntriesWithLessThanSixWords(orderedScore, 5);

    }
}
