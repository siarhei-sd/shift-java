import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import org.example.core.exeptions.KoronaIncorrectCommandLineArgumentsException;
import org.example.services.CommandLineService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CommandLineServiceTest {

    @Test
    @DisplayName(
            "Given correct list of params" +
                    "When parse command line parameters" +
                    "Then parameters are parsed well"
    )
    public void parseCommandLineParamsSuccessTest() {
        final var params = CommandLineService.getParams(
                new String[] { "-a", "-f", "input.txt", "-p", "sample-" }
        );
        final var listOfFiles = new ArrayList<>();
        listOfFiles.add("input.txt");

        assertEquals(listOfFiles, params.getArgList());
    }

    @Test
    @DisplayName(
            "Given incorrect list of params" +
                    "When parse command line parameters" +
                    "Then throw KoronaIncorrectCommandLineArgumentsException"
    )
    public void parseCommandLineParamsFailTest() {
        Throwable exception = assertThrows(
                KoronaIncorrectCommandLineArgumentsException.class,
                () -> {
                    CommandLineService.getParams(
                            new String[] {
                                    "-a",
                                    "-f",
                                    "input.txt",
                                    "-p",
                                    "sample-",
                                    "-x",
                            }
                    );
                }
        );
        assertEquals(
                "Incorrect command line arguments : ",
                exception.getMessage()
        );
    }
}
