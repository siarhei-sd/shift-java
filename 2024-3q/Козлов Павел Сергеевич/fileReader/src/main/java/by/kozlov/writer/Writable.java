package by.kozlov.writer;

import java.io.FileWriter;
import java.util.List;

public interface Writable {
    void write(List<String> args, FileWriter fw);
}
