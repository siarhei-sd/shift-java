package com.nadzeya;
import org.junit.Test;
import static org.junit.Assert.*;

public class FullStatisticTest {

    @Test
    public void testFullStatistic() {
        FullStatistic statisticInteger = new FullStatistic();
        FullStatistic statisticFloat = new FullStatistic();
        FullStatistic statisticString = new FullStatistic();
        // Тестирование добавления целых чисел
        statisticInteger.updateStatistics("10", "int");
        statisticInteger.updateStatistics("20", "int");
        statisticInteger.updateStatistics("30", "int");

        // Тестирование добавления чисел с плавающей запятой
        statisticFloat.updateStatistics("10.5", "float");
        statisticFloat.updateStatistics("20.5", "float");
        statisticFloat.updateStatistics("30.5", "float");
        statisticFloat.updateStatistics("-10.5", "float");
        statisticFloat.updateStatistics("-20.554", "float");
        statisticFloat.updateStatistics("300.5", "float");

        // Тестирование добавления строк
        statisticString.updateStatistics("hello", "string");
        statisticString.updateStatistics("world", "string");

        assertEquals("string", statisticString.getTypeForStatistics());
        assertEquals("int", statisticInteger.getTypeForStatistics());
        assertEquals("float", statisticFloat.getTypeForStatistics());

        // Проверка правильности вычислений
        assertEquals(3, statisticInteger.getElementsNumber());
        assertEquals(6, statisticFloat.getElementsNumber());
        assertEquals(2, statisticString.getElementsNumber());


        // Проверка корректности минимальных и максимальных значений
        assertEquals(10, statisticInteger.getMinIntString());
        assertEquals(30, statisticInteger.getMaxIntString());


        assertEquals(-20.554, statisticFloat.getMinFloat(), 0.0001);
        assertEquals(300.5, statisticFloat.getMaxFloat(), 0.001);
        // Проверка средних значений
        assertEquals(20.0, statisticInteger.getAvgInt(), 0.001);

        // Вывод полной статистики
        statisticInteger.printStatistic();
        statisticFloat.printStatistic();
        statisticString.printStatistic();
    }
}