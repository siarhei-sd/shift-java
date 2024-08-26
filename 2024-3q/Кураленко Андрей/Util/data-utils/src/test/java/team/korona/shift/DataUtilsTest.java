package team.korona.shift;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataUtilsTest {

    @Test
    public void testIsInteger() {
        assertTrue(DataUtils.isInteger("123"));
        assertTrue(DataUtils.isInteger("-456"));
        assertTrue(DataUtils.isInteger("0"));
        assertFalse(DataUtils.isInteger("12.34"));
        assertFalse(DataUtils.isInteger("abc"));
        assertFalse(DataUtils.isInteger(""));
        assertFalse(DataUtils.isInteger(null));
    }

    @Test
    public void testIsFloat() {
        assertTrue(DataUtils.isFloat("123.45"));
        assertTrue(DataUtils.isFloat("-456.78"));
        assertTrue(DataUtils.isFloat("0.0"));
        assertTrue(DataUtils.isFloat("-456"));
        assertTrue(DataUtils.isFloat("1.23e-4"));
        assertFalse(DataUtils.isFloat("abc"));
        assertFalse(DataUtils.isFloat(""));
    }
}