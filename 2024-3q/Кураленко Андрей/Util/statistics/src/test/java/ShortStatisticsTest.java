import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.korona.shift.ShortStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortStatisticsTest {
    private ShortStatistics statistics;

    @BeforeEach
    public void setUp() {
        statistics = new ShortStatistics();
    }

    @Test
    public void testUpdateStatisticsWithInteger() {
        statistics.updateStatistics("42");
        String result = statistics.getStatistics();
        assertEquals("Total integers: 1\n" +
                "Total floats: 0\n" +
                "Totals strings: 0\n", result);
    }

    @Test
    public void testUpdateStatisticsWithFloat() {
        statistics.updateStatistics("3.14");
        String result = statistics.getStatistics();
        assertEquals("Total integers: 0\n" +
                "Total floats: 1\n" +
                "Totals strings: 0\n", result);
    }

    @Test
    public void testUpdateStatisticsWithString() {
        statistics.updateStatistics("Hello");
        String result = statistics.getStatistics();
        assertEquals("Total integers: 0\n" +
                "Total floats: 0\n" +
                "Totals strings: 1\n", result);
    }

    @Test
    public void testUpdateStatisticsWithMixedData() {
        statistics.updateStatistics("42");
        statistics.updateStatistics("3.14");
        statistics.updateStatistics("Hello");

        String result = statistics.getStatistics();
        assertEquals("Total integers: 1\n" +
                "Total floats: 1\n" +
                "Totals strings: 1\n", result);
    }

    @Test
    public void testGetStatisticsEmpty() {
        String result = statistics.getStatistics();
        assertEquals("Total integers: 0\n" +
                "Total floats: 0\n" +
                "Totals strings: 0\n", result);
    }
}
