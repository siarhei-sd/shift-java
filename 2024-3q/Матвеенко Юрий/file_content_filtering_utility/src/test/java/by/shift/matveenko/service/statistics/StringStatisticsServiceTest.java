package by.shift.matveenko.service.statistics;

import by.shift.matveenko.data.StatisticsTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringStatisticsServiceTest {
    StringStatisticsService stringStatisticsService;

    @BeforeEach
    void setUp() {
        stringStatisticsService = new StringStatisticsService(StatisticsTypes.FULL);
    }

    @Test
    @DisplayName("Test for add data")
    public void addData() {
        stringStatisticsService.addData("Lorem ipsum dolor sit amet");
        stringStatisticsService.addData("Пример");
        stringStatisticsService.addData("consectetur adipiscing");
        stringStatisticsService.addData("тестовое задание");
        stringStatisticsService.addData("Нормальная форма числа с плавающей запятой");
        stringStatisticsService.addData("Long");
        assertEquals(6, stringStatisticsService.getAmount());
        assertEquals(4, stringStatisticsService.getShortestString());
        assertEquals(42, stringStatisticsService.getLongestString());
    }
}