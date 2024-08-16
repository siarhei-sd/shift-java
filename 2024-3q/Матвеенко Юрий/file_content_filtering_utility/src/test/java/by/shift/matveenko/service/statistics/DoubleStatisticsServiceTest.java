package by.shift.matveenko.service.statistics;

import by.shift.matveenko.data.StatisticsTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleStatisticsServiceTest {
    DoubleStatisticsService doubleStatisticsService;

    @BeforeEach
    void setUp() {
        doubleStatisticsService = new DoubleStatisticsService(StatisticsTypes.FULL);
    }

    @Test
    @DisplayName("Test for add data")
    public void addData() {
        doubleStatisticsService.addData("3.1415");
        doubleStatisticsService.addData("-0.001");
        doubleStatisticsService.addData("1.528535047E-25");
        assertEquals(3, doubleStatisticsService.getAmount());
        assertEquals(-0.001, doubleStatisticsService.getMinValue());
        assertEquals(3.1415, doubleStatisticsService.getMaxValue());
        assertEquals(3.1405, doubleStatisticsService.getSum(), 1e-8);
        assertEquals(1.04683333, doubleStatisticsService.average(), 1e-8);
    }
}