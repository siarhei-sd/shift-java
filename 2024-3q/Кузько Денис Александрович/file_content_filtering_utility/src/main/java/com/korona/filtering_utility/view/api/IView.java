package com.korona.filtering_utility.view.api;

public interface IView {
    void displayMessage(String message);
    void displayError(String errorMessage, Exception e);

}
