package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class WeightTrackerController {

    public Circle home;
    public TextField weight;
    public TextField height;
    public Label BMIresult;
    public Label Advice;
    public Button submit;
    private String userEmail;
    public void initialize() {
        userEmail = UserSession.getInstance().getUserEmail();
        // Use userEmail to load user-specific data
    }
    public void calculateBMI(ActionEvent event) {
        try {
            float weightValue = Float.parseFloat(weight.getText());
            float heightValue = Float.parseFloat(height.getText()) / 100; // Convert cm to meters
            if (weightValue <= 0 || heightValue <= 0) {
                throw new IllegalArgumentException();
            }
            float bmi = weightValue / (heightValue * heightValue);
            BMIresult.setText(String.format("Your BMI is: %.2f", bmi));
            giveAdvice(bmi);
            if (userEmail != null && !userEmail.isEmpty()) {
                saveHeightAndWeight(weightValue, heightValue);
                System.out.println(" Is logged in"+userEmail);
            }else {
                System.out.println("User is not logged in or is a doctor.");
            }
        } catch (NumberFormatException e) {
            BMIresult.setText("Please enter correct values");
            Advice.setText("");
            BMIresult.setVisible(true);
        } catch (IllegalArgumentException e) {
            BMIresult.setText("Please enter positive values");
            Advice.setText("");
            BMIresult.setVisible(true);
        }
    }

    private void giveAdvice(float bmi) {
        if (bmi < 18.5) {
            Advice.setText("Underweight: Try to gain some weight for a healthy BMI.");
            Advice.setVisible(true);
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            Advice.setText("Normal: Keep up the good work for a healthy BMI.");
            Advice.setVisible(true);
        } else if (bmi >= 25 && bmi <= 29.9) {
            Advice.setText("Overweight: Consider diet and exercise to reach a healthier BMI.");
            Advice.setVisible(true);
        } else {
            Advice.setText("Obesity: It's important to consult with a healthcare provider for guidance.");
            Advice.setVisible(true);
        }
    }
    private void saveHeightAndWeight(float weight, float height) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm-ss");
        String timestamp = now.format(formatter);
        String fileName = "weight_and_height_" + timestamp + ".txt";
        String userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + userEmail;
        File userDirectory = new File(userDirectoryPath);
        File outputFile = new File(userDirectory, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("Weight: " + weight + " kg\n");
            writer.write("Height: " + height * 100 + " cm\n"); // Convert back to cm for storage
            writer.write("Timestamp: " + timestamp + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    public void setBackHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard_user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
