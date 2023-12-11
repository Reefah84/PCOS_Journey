package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class DeleteAccount extends dashboardUserController{
    @FXML
    public Button deletebutton;
    @FXML
    public Button back;

    private String userEmail = UserSession.getLoggedInUser().getUsername();
    private String userDirectoryPath;
    public void initializeForUser(String username) {
        this.userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + username;
    }

    public void initializeForDoctor(String username) {
        this.userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\DRData\\" + username;
    }
    @FXML
    @Override
    public void onDelete(ActionEvent event) {
        try {
            Path directory = Paths.get(userDirectoryPath);
            Files.walk(directory)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            redirectToLogin();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., show an error dialog)
        }
    }

    @FXML
    private void onGoBack(ActionEvent event) {
        closePopup();
    }

    private void redirectToLogin() {
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) back.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}