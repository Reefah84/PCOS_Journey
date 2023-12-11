package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class seeMyMedicine {
    public ListView<String> medicineList;
    public Button close;
    User loggedInUser;
    String email;
    public void initialize() {
        // Load medicine names when the controller is initialized
        UserSession userSession = UserSession.getInstance();
        loggedInUser = UserSession.getLoggedInUser();
        email = loggedInUser.getUsername();
        loadMedicineNames();
        medicineList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Pass the selected medicine name to the method
                openMedicinePopup(newValue);
            }
        });

    }

    private void loadMedicineNames() {
        Path directoryPath = Paths.get("E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\"+email); // Replace with the actual path
        try (Stream<Path> files = Files.list(directoryPath)) {
            medicineList.getItems().clear();
            medicineList.getItems().addAll(
                    files.filter(path -> path.toString().endsWith("medicine.txt"))
                            .map(path -> path.getFileName().toString().replace("_medicine.txt", ""))
                            .collect(Collectors.toList())
            );
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., display an error message)
        }
    }
    private void openMedicinePopup(String selectedMedicine) {
        try {
            // Load the FXML file for the pop-up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MedicinePopup.fxml"));
            Parent root = loader.load();
            MedicinePopup controller = loader.getController();
            System.out.println(selectedMedicine+" in open medicine popup");
            controller.setMedicineName(selectedMedicine);
            // Create and show the stage
            Stage stage = new Stage();
            stage.setTitle("My Medicine");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle your error here
        }
    }
    public void handleCloseButtonAction(ActionEvent event) {
        // Get the current stage from the button and close it
        Stage stage = (Stage) close.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
