package team.korona.shift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.korona.shift.parser.StatisticsType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileFilterCoordinatorTest {

    private FileFilterCoordinator coordinator;
    private Path tempDir;
    private List<File> inputFiles;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("testDir");

        inputFiles = List.of(Files.createTempFile(tempDir, "input1", ".txt").toFile());

        coordinator = new FileFilterCoordinator(
                inputFiles,
                tempDir.toString(),
                "test_",
                false,
                StatisticsType.SHORT
        );
    }

    @Test
    void testIntegersSuccessFilter() throws IOException {
        Files.writeString(inputFiles.get(0).toPath(), "123\n456\n");

        coordinator.filterFiles();

        Path intFilePath = tempDir.resolve("test_integers.txt");
        assertEquals("123\n456\n", Files.readString(intFilePath));
    }

    @Test
    void testFloatsSuccessFilter() throws IOException {
        Files.writeString(inputFiles.get(0).toPath(), "0.33\n-9.23\n");

        coordinator.filterFiles();

        Path floatsFilePath = tempDir.resolve("test_floats.txt");
        assertEquals("0.33\n-9.23\n", Files.readString(floatsFilePath));
    }

    @Test
    void testStringsSuccessFilter() throws IOException {
        Files.writeString(inputFiles.get(0).toPath(), "abc\nxyz\n");

        coordinator.filterFiles();

        Path strFilePath = tempDir.resolve("test_strings.txt");
        assertEquals("abc\nxyz\n", Files.readString(strFilePath));
    }

    @Test
    void testAppendMode() throws IOException {
        Path intFilePath = tempDir.resolve("test_integers.txt");
        Files.writeString(intFilePath, "555\n");

        coordinator = new FileFilterCoordinator(
                inputFiles,
                tempDir.toString(),
                "test_",
                true,
                StatisticsType.SHORT
        );

        Files.writeString(inputFiles.get(0).toPath(), "123\n456\n");

        coordinator.filterFiles();

        assertEquals("555\n123\n456\n", Files.readString(intFilePath));
    }
}

