package com.filter.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileProcessorTest {
    private FileProcessor fileProcessor;

    @BeforeEach
    void setUp() {
        fileProcessor = new FileProcessor();
    }

    @Test
    void testProcessLineInteger() {
        String line = "123";
        fileProcessor.processLine(line);

        // Проверяем, что в статистике по целым числам содержится один элемент, и это 123
        Statistics stats = fileProcessor.getStatistics(DataType.INTEGER);
        assertEquals(1, stats.getNumericCount());
        assertEquals(123.0, stats.getMinValue().orElse(Double.NaN));
        assertEquals(123.0, stats.getMaxValue().orElse(Double.NaN));
    }

    @Test
    void testProcessLineNegativeInteger() {
        String line = "-456";
        fileProcessor.processLine(line);

        // Проверяем, что в статистике по целым числам содержится один элемент, и это -456
        Statistics stats = fileProcessor.getStatistics(DataType.INTEGER);
        assertEquals(1, stats.getNumericCount());
        assertEquals(-456.0, stats.getMinValue().orElse(Double.NaN));
        assertEquals(-456.0, stats.getMaxValue().orElse(Double.NaN));
    }

    @Test
    void testProcessLineFloat() {
        String line = "3.14";
        fileProcessor.processLine(line);

        // Проверяем, что в статистике по числам с плавающей запятой содержится один элемент, и это 3.14
        Statistics stats = fileProcessor.getStatistics(DataType.FLOAT);
        assertEquals(1, stats.getNumericCount());
        assertEquals(3.14, stats.getMinValue().orElse(Double.NaN));
        assertEquals(3.14, stats.getMaxValue().orElse(Double.NaN));
    }

    @Test
    void testProcessLineNegativeFloat() {
        String line = "-0.001";
        fileProcessor.processLine(line);

        // Проверяем, что в статистике по числам с плавающей запятой содержится один элемент, и это -0.001
        Statistics stats = fileProcessor.getStatistics(DataType.FLOAT);
        assertEquals(1, stats.getNumericCount());
        assertEquals(-0.001, stats.getMinValue().orElse(Double.NaN));
        assertEquals(-0.001, stats.getMaxValue().orElse(Double.NaN));
    }

    @Test
    void testProcessLineScientificNotation() {
        String line = "1.528535047E-25";
        fileProcessor.processLine(line);

        // Проверяем, что в статистике по числам с плавающей запятой содержится один элемент, и это 1.528535047E-25
        Statistics stats = fileProcessor.getStatistics(DataType.FLOAT);
        assertEquals(1, stats.getNumericCount());
        assertEquals(1.528535047E-25, stats.getMinValue().orElse(Double.NaN));
        assertEquals(1.528535047E-25, stats.getMaxValue().orElse(Double.NaN));
    }

    @Test
    void testProcessLineString() {
        String line = "Hello World!";
        fileProcessor.processLine(line);

        // Проверяем, что в статистике по строкам содержится один элемент, и это "Hello World!"
        Statistics stats = fileProcessor.getStatistics(DataType.STRING);
        assertEquals(1, stats.getStringCount());
        assertEquals(12, stats.getMinStringLength());
        assertEquals(12, stats.getMaxStringLength());
    }
}