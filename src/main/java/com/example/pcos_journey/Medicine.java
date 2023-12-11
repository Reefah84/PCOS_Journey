package com.example.pcos_journey;

import javafx.event.ActionEvent;

public interface Medicine {
    public String name = "";
    public int frequency = 0; // in days
    public int timesPerDay = 0;

    public default void handleSendEmailAction(ActionEvent event) {

    }
    public void initialize();
}
