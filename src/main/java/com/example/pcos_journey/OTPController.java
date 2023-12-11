package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class OTPController {

    public Button resendOTP;
    public TextField OTP;
    public Button nextbutton;
    public Label incorrect;


    public void setResendOTP(ActionEvent actionEvent) {
        incorrect.setText("OTP resent. Please check email.");
        incorrect.setVisible(true);
    }
    public void setNextbutton(ActionEvent actionEvent) {
        String enteredOTP = OTP.getText();
        String generatedOTP = sharedOTP.getCurrentOTP();

        if (enteredOTP.equals(generatedOTP)) {
            // Correct OTP, proceed with loading the next scene
            loadResetPasswordScene();
        } else {
            incorrect.setText("Incorrect OTP. Please try again.");
            incorrect.setVisible(true);
        }
    }

    private void loadResetPasswordScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reset_password.fxml")); // Adjust the path
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)nextbutton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            incorrect.setText("Failed to load reset password screen.");
        }
    }
}