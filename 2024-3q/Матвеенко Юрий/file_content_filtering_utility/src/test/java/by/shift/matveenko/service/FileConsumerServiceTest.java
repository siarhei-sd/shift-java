package by.shift.matveenko.service;

import by.shift.matveenko.data.DataTypes;
import by.shift.matveenko.data.StatisticsTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileConsumerServiceTest {
    FileConsumerService fileConsumerService;

    @BeforeEach
    void setUp() {
        fileConsumerService = new FileConsumerService(StatisticsTypes.FULL, "", "", false);
    }

    @ParameterizedTest
    @DisplayName("Test for integer numbers")
    @ValueSource(strings = {"45", "100500", "123456789", "-75", "-7445678"})
    public void dataTypeIdentifierInteger(String str) {
        assertEquals(DataTypes.INTEGER, fileConsumerService.dataTypeIdentifier(str));
    }

    @ParameterizedTest
    @DisplayName("Test for double numbers")
    @ValueSource(strings = {"1.528535047E-25", "3.1415", "-0.001", "25.655e3",
            "-38.723", "58.335", "17.37784e+183", "11.2783E-12", "-0.12378"})
    public void dataTypeIdentifierDouble(String str) {
        assertEquals(DataTypes.DOUBLE, fileConsumerService.dataTypeIdentifier(str));
    }

    @ParameterizedTest
    @DisplayName("Test for strings")
    @ValueSource(strings = {"Lorem ipsum dolor sit amet", "Пример", "consectetur adipiscing",
            "тестовое задание", "Нормальная форма числа с плавающей запятой",
            "Long"})
    public void dataTypeIdentifierString(String str) {
        assertEquals(DataTypes.STRING, fileConsumerService.dataTypeIdentifier(str));
    }
}