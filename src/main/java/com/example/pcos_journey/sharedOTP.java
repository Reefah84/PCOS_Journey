package com.example.pcos_journey;

import javafx.event.ActionEvent;

public abstract class sharedOTP {
    private static String currentOTP;
    private static String email;
    public static String getCurrentOTP() {
        return currentOTP;
    }
    public abstract void setSaveButton(ActionEvent event) throws EmailException;
    public static void setCurrentOTP(String currentOTP) {
        sharedOTP.currentOTP = currentOTP;
    }
    // Method to set the email
    public static void setEmail(String email) {
        sharedOTP.email = email;
    }

    // Method to get the email
    public static String getEmail() {
        return email;
    }
}

