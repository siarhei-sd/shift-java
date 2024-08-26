package team.korona.shift;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;


public class DataProcessorTest {

    private static final String OUTPUT_DIR = "test_output";
    private static final String PREFIX = "test_";

    private Statistics statistics;

    @BeforeEach
    public void setUp() {
        statistics = Mockito.mock(Statistics.class);
    }

    @AfterEach
    public void tearDown() throws IOException {
        clearTempFiles(OUTPUT_DIR);
    }

    /***
     *  По хорошему, каждую проверку нужно выносить в отдельный тест, все зависит от принятых подходов.
     *  Оставляю так, чтобы не дублировать код.
     *  Вызовы DataProcessor завернуты в try, потому что на момент проверки содержимого файла, данные находятся в буфере и не выгружены.
     *  Тут либо модифицировать DataProcessor, где добавлять выгрузку в файл после каждой записи (что довольно таким плохо), либо через try,
     *  т.к. DataProcessor реализует AutoCloseable
     */

    @Test
    public void testProcessLineInteger() throws IOException {
        Path intFilePath = Path.of(OUTPUT_DIR, PREFIX + "integers.txt");

        try (DataProcessor processor = new DataProcessor(OUTPUT_DIR, PREFIX, false, statistics)) {
            processor.processLine("123");
            assertTrue(Files.exists(intFilePath));
        }

        assertEquals("123\n", Files.readString(intFilePath));

        verify(statistics).updateStatistics("123");
    }

    @Test
    public void testProcessLineFloat() throws IOException {
        Path floatFilePath = Path.of(OUTPUT_DIR, PREFIX + "floats.txt");

        try (DataProcessor processor = new DataProcessor(OUTPUT_DIR, PREFIX, false, statistics)) {
            processor.processLine("123.45");
            assertTrue(Files.exists(floatFilePath));
        }

        assertEquals("123.45\n", Files.readString(floatFilePath));

        verify(statistics).updateStatistics("123.45");
    }

    @Test
    public void testProcessLineString() throws IOException {
        Path stringFilePath = Path.of(OUTPUT_DIR, PREFIX + "strings.txt");

        try (DataProcessor processor = new DataProcessor(OUTPUT_DIR, PREFIX, false, statistics)) {
            processor.processLine("Вечер в хату. Как банька?");
            assertTrue(Files.exists(stringFilePath));
        }

        assertEquals("Вечер в хату. Как банька?\n", Files.readString(stringFilePath));

        verify(statistics).updateStatistics("Вечер в хату. Как банька?");
    }

    @Test
    public void testDirectoryCreation() throws IOException {
        String newOutputDir = "new_test_output";
        try (DataProcessor processor = new DataProcessor(newOutputDir, PREFIX, false, statistics)) {
            processor.processLine("456");
        }

        Path intFilePath = Path.of(newOutputDir, PREFIX + "integers.txt");
        assertTrue(Files.exists(intFilePath));

        clearTempFiles(newOutputDir);
    }

    private static void clearTempFiles(String newOutputDir) throws IOException {
        try (Stream<Path> paths = Files.walk(Path.of(newOutputDir))) {
            paths.map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
