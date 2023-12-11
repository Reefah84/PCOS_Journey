package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MedicinePopup{
    public AnchorPane medicinedetails;
    public Button back;
    public Label morning;
    public Label afternoon;
    public Label evening;
    public Label name;
    public Label freq;
    User loggedInUser = UserSession.getLoggedInUser();
    String email;

    private String medicineName;

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void initialize() {
        email = loggedInUser.getUsername();
        if (medicineName != null) {
            System.out.println(medicineName+" in initialize in medicine popup");
            loadMedicine();
        }
    }
    public void loadMedicine(){
        Path medicineFilePath = Paths.get("E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\", email, medicineName + "_medicine.txt");
        System.out.println("in load medicine in medicine popup");
        try {
            // Read all lines from the medicine.txt file
            List<String> lines = Files.readAllLines(medicineFilePath);
            name.setText(medicineName);
            // Assuming the file has a specific format: MedicineName;Frequency;TimesPerDay;Dosage
            // and that there is only one line for simplicity
            if (!lines.isEmpty()) {
                String[] parts = lines.get(0).split(";");
                if (parts.length >= 3) {
                    String medicineName = parts[0];
                    int frequency = Integer.parseInt(parts[1]);
                    int dosage = Integer.parseInt(parts[2]);

                    // Set the labels
                    name.setText(medicineName);
                    freq.setText(String.valueOf(frequency));

                    // Reset labels
                    morning.setText("0");
                    afternoon.setText("0");
                    evening.setText("0");

                    // Adjust the dosage labels accordingly
                    if (dosage == 1) {
                        morning.setText("1");
                        System.out.println("in morning dose 1");
                    } else if (dosage == 2) {
                        morning.setText("1");
                        afternoon.setText("1");
                        System.out.println("in morning and afternoon dose 1");
                    } else if (dosage == 3) {
                        morning.setText("1");
                        afternoon.setText("1");
                        evening.setText("1");
                        System.out.println("in morning,afternoon and evening dose 1");
                    } else {
                        morning.setText("2");
                        afternoon.setText("2");
                        evening.setText("2");
                    }
                    morning.setVisible(true);
                    afternoon.setVisible(true);
                    evening.setVisible(true);
                }
            }
            morning.setVisible(true);
            afternoon.setVisible(true);
            evening.setVisible(true);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println("in catch in medicine popup");
            // Handle exceptions here, perhaps notify the user that an error occurred
        }
    }

    public void handleBackAction(ActionEvent event) {
        Stage stage = (Stage) back.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
