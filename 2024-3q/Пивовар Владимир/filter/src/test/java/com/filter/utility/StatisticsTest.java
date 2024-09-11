package com.filter.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StatisticsTest {
    private Statistics statistics;

    @BeforeEach
    void setUp() {
        statistics = new Statistics();
    }

    @Test
    void testAddNumericValue() {
        statistics.addNumericValue(5.0);
        statistics.addNumericValue(10.0);

        assertEquals(2, statistics.getNumericCount());
        assertEquals(5.0, statistics.getMinValue().orElse(Double.NaN));
        assertEquals(10.0, statistics.getMaxValue().orElse(Double.NaN));
        assertEquals(15.0, statistics.getSum());
        assertEquals(7.5, statistics.getAverage().orElse(Double.NaN));
    }

    @Test
    void testAddStringValue() {
        statistics.addStringValue("short");
        statistics.addStringValue("much longer string");

        assertEquals(2, statistics.getStringCount());
        assertEquals(5, statistics.getMinStringLength());
        assertEquals(18, statistics.getMaxStringLength());
    }

    @Test
    void testEmptyStatistics() {
        assertEquals(0, statistics.getNumericCount());
        assertEquals(0, statistics.getStringCount());
        assertFalse(statistics.getMinValue().isPresent());
        assertFalse(statistics.getMaxValue().isPresent());
        assertEquals(0, statistics.getMinStringLength());
        assertEquals(0, statistics.getMaxStringLength());
    }

    @Test
    void testSingleNumericValue() {
        statistics.addNumericValue(42.0);

        assertEquals(1, statistics.getNumericCount());
        assertEquals(42.0, statistics.getMinValue().orElse(Double.NaN));
        assertEquals(42.0, statistics.getMaxValue().orElse(Double.NaN));
        assertEquals(42.0, statistics.getSum());
        assertEquals(42.0, statistics.getAverage().orElse(Double.NaN));
    }

    @Test
    void testSingleStringValue() {
        statistics.addStringValue("hello");

        assertEquals(1, statistics.getStringCount());
        assertEquals(5, statistics.getMinStringLength());
        assertEquals(5, statistics.getMaxStringLength());
    }

    @Test
    void testMultipleNumericValues() {
        statistics.addNumericValue(1.0);
        statistics.addNumericValue(2.0);
        statistics.addNumericValue(3.0);
        statistics.addNumericValue(4.0);
        statistics.addNumericValue(5.0);

        assertEquals(5, statistics.getNumericCount());
        assertEquals(1.0, statistics.getMinValue().orElse(Double.NaN));
        assertEquals(5.0, statistics.getMaxValue().orElse(Double.NaN));
        assertEquals(15.0, statistics.getSum());
        assertEquals(3.0, statistics.getAverage().orElse(Double.NaN));
    }

    @Test
    void testMultipleStringValues() {
        statistics.addStringValue("a");
        statistics.addStringValue("abc");
        statistics.addStringValue("abcd");
        statistics.addStringValue("abcdefg");

        assertEquals(4, statistics.getStringCount());
        assertEquals(1, statistics.getMinStringLength());
        assertEquals(7, statistics.getMaxStringLength());
    }
}