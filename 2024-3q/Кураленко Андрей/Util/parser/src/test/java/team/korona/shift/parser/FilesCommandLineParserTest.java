package team.korona.shift.parser;

import org.junit.jupiter.api.Test;
import team.korona.shift.parser.FilesCommandLineParser;
import team.korona.shift.parser.ParsingException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilesCommandLineParserTest {

    @Test
    public void testDefaultOptions() throws ParsingException {
        String[] args = {"input1.txt", "input2.txt", "-s"};
        FilesCommandLineParser parser = new FilesCommandLineParser(args);

        assertFalse(parser.isAppendMode(), "Append mode should be false by default");
        assertEquals(".", parser.getOutputPath(), "Default output path should be current directory");
        assertEquals("", parser.getPrefix(), "Default prefix should be an empty string");
        assertEquals(List.of("input1.txt", "input2.txt"), parser.getInputFiles(), "Input files should match the provided list");
    }

    @Test
    public void testCustomOutputPath() throws ParsingException {
        String[] args = {"-o", "/custom/path", "input1.txt", "-f"};
        FilesCommandLineParser parser = new FilesCommandLineParser(args);

        assertEquals("/custom/path", parser.getOutputPath(), "Output path should be /custom/path");
    }

    @Test
    public void testCustomPrefix() throws ParsingException {
        String[] args = {"-p", "result_", "input1.txt", "-f"};
        FilesCommandLineParser parser = new FilesCommandLineParser(args);

        assertEquals("result_", parser.getPrefix(), "Prefix should be 'result_'");
    }

    @Test
    public void testAppendMode() throws ParsingException {
        String[] args = {"-a", "input1.txt", "-s"};
        FilesCommandLineParser parser = new FilesCommandLineParser(args);

        assertTrue(parser.isAppendMode(), "Append mode should be true when -a is specified");
        assertEquals(List.of("input1.txt"), parser.getInputFiles(), "Input files should contain only input1.txt");
    }

    @Test
    public void testMissingInputFiles() {
        String[] args = {"-s"};

        Exception exception = assertThrows(ParsingException.class, () -> {
            new FilesCommandLineParser(args);
        });

        String expectedMessage = "No input files specified.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testMissingStatsOption() {
        String[] args = {"input1.txt"};

        Exception exception = assertThrows(ParsingException.class, () -> {
            new FilesCommandLineParser(args);
        });

        String expectedMessage = "You must specify either -s (short statistics) or -f (full statistics).";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}