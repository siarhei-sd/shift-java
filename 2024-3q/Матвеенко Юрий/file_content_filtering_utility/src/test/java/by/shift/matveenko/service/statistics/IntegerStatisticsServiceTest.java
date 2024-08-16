package by.shift.matveenko.service.statistics;

import by.shift.matveenko.data.StatisticsTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerStatisticsServiceTest {
    IntegerStatisticsService integerStatisticsService;

    @BeforeEach
    void setUp() {
        integerStatisticsService = new IntegerStatisticsService(StatisticsTypes.FULL);
    }

    @Test
    @DisplayName("Test for add data")
    public void addData() {
        integerStatisticsService.addData("45");
        integerStatisticsService.addData("100500");
        integerStatisticsService.addData("123456789");
        assertEquals(3, integerStatisticsService.getAmount());
        assertEquals(45, integerStatisticsService.getMinValue());
        assertEquals(123456789, integerStatisticsService.getMaxValue());
        assertEquals(123557334, integerStatisticsService.getSum());
        assertEquals(41185778, integerStatisticsService.average());
    }
}