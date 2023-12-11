package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class dashboardUserController{

    public Button logout;
    public Button Health;

    public Label welcome=new Label("Welcome");
    public Button symptomButton;
    public Button weightButton;
    public Button DoctorButton;
    public Button gy;
    public Circle home;
    public ListView <String> messageFrom=new ListView<>();
    public Button FAQ;
    private String userEmail;
    public Button delete;

    public void initialize() {
        // Access the logged-in user from UserSession
        User loggedInUser = UserSession.getLoggedInUser();
        UserSession.getInstance().setUserEmail(loggedInUser.getUsername());
        if (UserSession.isUserLoggedIn()) {
            loggedInUser = UserSession.getLoggedInUser();
            userEmail = loggedInUser.getUsername();
            welcome.setText("Welcome " + loggedInUser.getUsername());
            welcome.setVisible(true);
            loadMessagefrom();
        } else {
            resetUIComponents(); // Call a method to reset or disable UI components
        }
        messageFrom.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedReply = messageFrom.getSelectionModel().getSelectedItem();
                if (selectedReply != null) {
                    openPatientMessagePopup(selectedReply);
                }
            }
        });
        welcome.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                openProfile();
            }
        });
    }

    private void resetUIComponents() {
        welcome.setVisible(false);
        messageFrom.getItems().clear();
        messageFrom.setDisable(true);
        symptomButton.setDisable(true);
        weightButton.setDisable(true);
        DoctorButton.setDisable(true);
        gy.setDisable(true);
        FAQ.setDisable(true);
        Health.setDisable(true);
    }

    private void loadMessagefrom() {
        String userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\"+userEmail;
        File userDirectory = new File(userDirectoryPath);
        String[] replies = userDirectory.list((dir, name) -> name.startsWith("reply_from_"));
        if (replies != null) {
            messageFrom.getItems().addAll(
                    Arrays.stream(replies)
                            .map(this::formatFileName)
                            .collect(Collectors.toList())
            );
        }
    }
    private String formatFileName(String fileName) {
        return fileName.replace("reply_from_", "Message from ")
                .replace("sent_mail_to_", "Sent mail to ")
                .replace("_", " ")
                .replace(".txt", "");
    }
    private void openPatientMessagePopup(String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeeDoctorMessage.fxml"));
            Parent root = loader.load();

            SeeDoctorMessage controller = loader.getController();
            String filePath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + userEmail + "\\" + fileName.replace("Message from ", "reply_from_").replace(" ", "_") + ".txt";
            String messageContent = new String(Files.readAllBytes(Paths.get(filePath)));
            controller.setMessage(messageContent);
            controller.setFromto("Message from Doctor:");
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Doctor's Message");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void openProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("myProfile.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Profile");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions (e.g., show an error dialog)
        }
    }
    public void setLogout(ActionEvent event)
    {
        UserSession.logout();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage_attempt2.fxml")); // Adjust the path
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)logout.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSymptomButton(ActionEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("trackerlanding.fxml")); // Adjust the path
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)symptomButton.getScene().getWindow();
            Scene scene = new Scene(root);
            // If you have a stylesheet
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWeightButton(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WeightTracker.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) weightButton.getScene().getWindow();
            // Set the new content in the same window
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setHome(MouseEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage_attempt2.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) home.getScene().getWindow();
            // Set the new content in the same window
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDoctorButton(ActionEvent event)
    {
        try {
            // Load the new FXML file for the popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeeMySentMails.fxml"));
            Parent root = loader.load();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Set the popup to block interaction with other windows
            popupStage.setTitle("Sent Messages");
            popupStage.setResizable(false);
            // Set the scene for the popup stage
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            // Show the popup window and wait for it to close
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onDelete(ActionEvent event) {
        try {
            // Open the DeleteAccount popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteAccount.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            DeleteAccount controller = loader.getController();
            controller.initializeForUser(userEmail);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);
            // Show the popup and wait until it is closed
            popupStage.showAndWait();

            // After the popup is closed, check if the user has been logged out
            if (UserSession.isUserLoggedIn()) {
                // User is logged out, redirect to login.fxml
                UserSession.logout();
                redirectToLogin();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private void redirectToLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    public void showListOfDoctors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListofDoctors.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) gy.getScene().getWindow();
            stage.setResizable(false);
            // Set the new content in the same window
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    public void setFAQ(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatBot.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)FAQ.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
    }
    public void setHealth(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("baseHEALTH.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)Health.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
    }
}
