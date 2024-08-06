package com.korona.filtering_utility.view;

import com.korona.filtering_utility.view.api.IView;

public class ConsoleView implements IView {
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override

    public void displayError(String errorMessage, Exception e) {
        System.err.println("Error: " + errorMessage);
    }
}
