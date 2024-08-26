import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.korona.shift.FullStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FullStatisticsTest {

    private FullStatistics statistics;

    @BeforeEach
    public void setUp() {
        statistics = new FullStatistics();
    }

    @Test
    public void testNoData() {
        String expectedOutput = "Total integers: 0\n" +
                "Minimum integer: No data\n" +
                "Maximum integer: No data\n" +
                "Sum of integers: No data\n" +
                "Total floats: 0\n" +
                "Minimum float: No data\n" +
                "Maximum float: No data\n" +
                "Sum of floats: No data\n" +
                "Totals strings: 0\n" +
                "Shortest string: No data\n" +
                "Longest string: No data\n";

        assertEquals(expectedOutput, statistics.getStatistics());
    }

    @Test
    public void testSingleInteger() {
        statistics.updateStatistics("42");

        String expectedOutput = "Total integers: 1\n" +
                "Minimum integer: 42\n" +
                "Maximum integer: 42\n" +
                "Sum of integers: 42\n" +
                "Total floats: 0\n" +
                "Minimum float: No data\n" +
                "Maximum float: No data\n" +
                "Sum of floats: No data\n" +
                "Totals strings: 0\n" +
                "Shortest string: No data\n" +
                "Longest string: No data\n";

        assertEquals(expectedOutput, statistics.getStatistics());
    }

    @Test
    public void testMultipleIntegers() {
        statistics.updateStatistics("42");
        statistics.updateStatistics("7");
        statistics.updateStatistics("100");

        String expectedOutput = "Total integers: 3\n" +
                "Minimum integer: 7\n" +
                "Maximum integer: 100\n" +
                "Sum of integers: 149\n" +
                "Total floats: 0\n" +
                "Minimum float: No data\n" +
                "Maximum float: No data\n" +
                "Sum of floats: No data\n" +
                "Totals strings: 0\n" +
                "Shortest string: No data\n" +
                "Longest string: No data\n";

        assertEquals(expectedOutput, statistics.getStatistics());
    }

    @Test
    public void testSingleFloat() {
        statistics.updateStatistics("3.14");

        String expectedOutput = "Total integers: 0\n" +
                "Minimum integer: No data\n" +
                "Maximum integer: No data\n" +
                "Sum of integers: No data\n" +
                "Total floats: 1\n" +
                "Minimum float: 3.14\n" +
                "Maximum float: 3.14\n" +
                "Sum of floats: 3.14\n" +
                "Totals strings: 0\n" +
                "Shortest string: No data\n" +
                "Longest string: No data\n";

        assertEquals(expectedOutput, statistics.getStatistics());
    }

    @Test
    public void testMultipleFloats() {
        statistics.updateStatistics("3.14");
        statistics.updateStatistics("2.71");
        statistics.updateStatistics("42.0");

        String expectedOutput = "Total integers: 0\n" +
                "Minimum integer: No data\n" +
                "Maximum integer: No data\n" +
                "Sum of integers: No data\n" +
                "Total floats: 3\n" +
                "Minimum float: 2.71\n" +
                "Maximum float: 42.0\n" +
                "Sum of floats: 47.85\n" +
                "Totals strings: 0\n" +
                "Shortest string: No data\n" +
                "Longest string: No data\n";

        assertEquals(expectedOutput, statistics.getStatistics());
    }

    @Test
    public void testSingleString() {
        statistics.updateStatistics("hello");

        String expectedOutput = "Total integers: 0\n" +
                "Minimum integer: No data\n" +
                "Maximum integer: No data\n" +
                "Sum of integers: No data\n" +
                "Total floats: 0\n" +
                "Minimum float: No data\n" +
                "Maximum float: No data\n" +
                "Sum of floats: No data\n" +
                "Totals strings: 1\n" +
                "Shortest string: hello\n" +
                "Longest string: hello\n";

        assertEquals(expectedOutput, statistics.getStatistics());
    }

    @Test
    public void testMultipleStrings() {
        statistics.updateStatistics("short");
        statistics.updateStatistics("a much longer string");
        statistics.updateStatistics("mid_mid");

        String expectedOutput = "Total integers: 0\n" +
                "Minimum integer: No data\n" +
                "Maximum integer: No data\n" +
                "Sum of integers: No data\n" +
                "Total floats: 0\n" +
                "Minimum float: No data\n" +
                "Maximum float: No data\n" +
                "Sum of floats: No data\n" +
                "Totals strings: 3\n" +
                "Shortest string: short\n" +
                "Longest string: a much longer string\n";

        assertEquals(expectedOutput, statistics.getStatistics());
    }
}
