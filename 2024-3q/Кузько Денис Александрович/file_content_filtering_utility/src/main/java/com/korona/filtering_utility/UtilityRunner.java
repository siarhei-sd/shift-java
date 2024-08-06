package com.korona.filtering_utility;

import com.korona.filtering_utility.controller.ConsoleController;
import com.korona.filtering_utility.servise.FileService;
import com.korona.filtering_utility.view.ConsoleView;

public class UtilityRunner {
    public static void main(String[] args) {
        ConsoleController consoleController = new ConsoleController();
        consoleController.execute(args);
    }
}
