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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class dashboardDoctorController {

    public Button logout;
    public Button Health;
    public Label welcome=new Label("Welcome Dr ");
    public ListView<String>patientMessage=new ListView<>();;
    public ListView<String> myReply=new ListView<>();;
    public Button gy;
    public Circle home;
    public Button FAQ;
    public Button delete;
    private String doctoremail;
    public void initialize() {
        User loggedInUser = UserSession.getLoggedInUser();
        if (loggedInUser != null) {
            welcome.setText("Welcome Dr " + loggedInUser.getUsername());
            doctoremail = loggedInUser.getUsername();
            welcome.setVisible(true);
        }

        loadpatientMessage();
        loadReplies();
        setupListViewListeners();
    }

    private void loadpatientMessage() {
        String doctorDirectoryPath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/DRData/" + doctoremail;
        File doctorDirectory = new File(doctorDirectoryPath);

        String[] patientMessages = doctorDirectory.list((dir, name) -> name.startsWith("message_from_"));
        if (patientMessages != null) {
            patientMessage.getItems().addAll(
                    Arrays.stream(patientMessages)
                            .map(this::formatFileName)
                            .collect(Collectors.toList())
            );
        }
    }

    private void loadReplies() {
        String doctorDirectoryPath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/DRData/" + doctoremail;
        File doctorDirectory = new File(doctorDirectoryPath);

        String[] replies = doctorDirectory.list((dir, name) -> name.startsWith("reply_to_"));
        if (replies != null) {
            myReply.getItems().addAll(
                    Arrays.stream(replies)
                            .map(this::formatFileName)
                            .collect(Collectors.toList())
            );
        }
    }

    private String formatFileName(String fileName) {
        return fileName.replace("message_from_", "Message from ")
                .replace("replied_to_", "Replied to ")
                .replace("_", " ")
                .replace(".txt", "");
    }

    private void setupListViewListeners() {
        patientMessage.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedMessage = patientMessage.getSelectionModel().getSelectedItem();
                if (selectedMessage != null) {
                    openPatientMessagePopup(selectedMessage, false);
                }
            }
        });

        myReply.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedReply = myReply.getSelectionModel().getSelectedItem();
                if (selectedReply != null) {
                    openPatientMessagePopup(selectedReply, true);
                }
            }
        });
    }

    private void openPatientMessagePopup(String fileName, boolean isReply) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeePatientMessage.fxml"));
            Parent root = loader.load();

            SeePatientMessage controller = loader.getController();
            String filePrefix = isReply ? "replied_to_" : "message_from_";
            String filePath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/DRData/" + doctoremail + "/" + fileName.replace(isReply ? "Replied to " : "Message from ", filePrefix).replace(" ", "_") + ".txt";
            String messageContent = new String(Files.readAllBytes(Paths.get(filePath)));
            controller.setMessage(messageContent);

            // Extract user email if needed
            if (isReply) {
                controller.setFromto("Message to patient");
            }

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Patient Message");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String extractUserEmailFromMessage(Path filePath) throws IOException {
        String content = new String(Files.readAllBytes(filePath));
        Pattern emailPattern = Pattern.compile("User Email: (.+)");
        Matcher matcher = emailPattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
    public void setLogout(ActionEvent event)
    {
        UserSession.logout();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homepage_attempt2.fxml")); // Adjust the path
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)logout.getScene().getWindow();
            Scene scene = new Scene(root);stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setBackHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage_attempt2.fxml"));
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

    public void showListOfDoctors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListofDoctors.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) gy.getScene().getWindow();
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
    public void onDeleteAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteAccount.fxml"));
            Parent root = loader.load();

            DeleteAccount deleteAccountController = loader.getController();
            User loggedInUser = UserSession.getLoggedInUser();
            if (loggedInUser.isDoctor()) {
                deleteAccountController.initializeForDoctor(loggedInUser.getUsername());
            } else {
                System.out.println("Error in deleting account");
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            if (UserSession.isUserLoggedIn()) {
                // User is logged out, redirect to login.fxml
                UserSession.logout();
                redirectToLogin();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
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
}
