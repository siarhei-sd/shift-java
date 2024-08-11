package com.javaded78.service.reader;

import java.util.List;
import java.util.stream.Stream;

public interface Reader {

    Stream<String> readAll(List<String> inputFiles);
}
