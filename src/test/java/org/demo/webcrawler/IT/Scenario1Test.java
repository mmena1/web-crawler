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
public class Scenario1Test extends ScenarioIT {

    @Before
    public void setUp() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File input = new File(classLoader.getResource("scenario1.html").getFile());
            document = Jsoup.parse(input, "UTF-8");
            given(jsoupWrapper.connect()).willReturn(document);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldReturnOnlyEntriesWithMoreThanFiveWords() throws Exception {

        int[] orderedComments = {190, 134, 110, 93, 88, 67, 63, 39, 37, 32, 30, 26, 17, 16, 16, 10, 9, 9, 5, 4, 1, 0, 0, 0};
        filterEntriesWithMoreThanFiveWords(orderedComments, 24);

    }

    @Test
    public void shouldReturnEntriesWithLessThanSixWords() throws Exception {

        int[] orderedScore = {503, 121, 77, 56, 45, 27};
        filterEntriesWithLessThanSixWords(orderedScore, 6);

    }
}
