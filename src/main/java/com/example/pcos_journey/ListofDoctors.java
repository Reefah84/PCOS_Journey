package com.example.pcos_journey;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ListofDoctors {

    public Button LoginButton;
    public Button Health;
    public Circle home;
    public Button gy;
    public Button FAQ;
    public Button search;
    public ImageView searchim;
    public Label error;
    @FXML
    private ListView<String> doctorsListView;
    @FXML
    private TextField searchField;
    private ObservableList<String> doctorsList = FXCollections.observableArrayList();
    private Map<String, String> doctorEmailsMap = new HashMap<>();
    public void initialize() {
        updateLoginButton();
        loadDoctorsList();
    }

    private void updateLoginButton() {
        if (UserSession.getInstance().isUserLoggedIn()) {
            User loggedInUser = UserSession.getLoggedInUser();
            if (loggedInUser.isUser()) {
                LoginButton.setText("USER");
                LoginButton.setOnAction(this::openDashboardUser);
            } else if (loggedInUser.isDoctor()) {
                LoginButton.setText("DOCTOR");
                LoginButton.setOnAction(this::openDashboardDoctor);
            }
        } else {
            LoginButton.setText("LOGIN");
            LoginButton.setOnAction(this::openLogin);
        }
    }

    private void openDashboardUser(ActionEvent event) {
        navigateTo("dashboard_user.fxml", event);
    }

    private void openDashboardDoctor(ActionEvent event) {
        navigateTo("dashboard_doctor.fxml", event);
    }

    private void openLogin(ActionEvent event) {
        navigateTo("login.fxml", event);
    }

    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setLoginButton(ActionEvent event) throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)LoginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);

    }
    private void loadDoctorsList() {
        File drDataDir = new File("E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/DRData");
        String[] doctorEmails = drDataDir.list();
        doctorsList.clear();
        if (doctorEmails != null) {
            Arrays.stream(doctorEmails).forEach(email -> {
                String formattedName = formatDoctorName(email);
                doctorsList.add(formattedName); // Add to doctorsList
                doctorEmailsMap.put(formattedName, email);
            });
        }
        doctorsListView.setItems(doctorsList);
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        performSearch();
    }
    @FXML
    private void handleSearchImageClicked(MouseEvent event) {
        performSearch();
    }

    private void performSearch() {
        String searchText = searchField.getText();
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(doctorsList);
            error.setVisible(true);
        } else {
            String lowerCaseFilter = searchText.toLowerCase();
            for (String doctor : doctorsList) {
                if (doctor.toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(doctor);
                }
            }
        }
        if (filteredList.isEmpty()) {
            error.setVisible(true); // Show error label if no results
        } else {
            error.setVisible(false); // Hide error label if there are results
        }
        doctorsListView.setItems(filteredList);
    }


    private String formatDoctorName(String email) {
        String namePart = email.split("@")[0]; // Split at '@' and take the first part
        namePart = namePart.replaceAll("\\d", ""); // Remove numbers
        return "Dr " + namePart; // Add 'Dr' prefix
    }
    @FXML
    private void handleDoctorSelection() {
        String selectedDoctor = doctorsListView.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            try {
                String doctorEmail = doctorEmailsMap.get(selectedDoctor);
                System.out.println(doctorEmail);
                System.out.println(selectedDoctor);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("sendMailPopupUser.fxml"));
                Parent root = loader.load();

                SendMailPopupUser controller = loader.getController();
                controller.initialize(doctorEmail); // Pass the extracted doctor email

                Stage stage = new Stage();
                stage.setTitle(selectedDoctor);
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        }
    }
    private String extractEmailFromName(String doctorName) {
        // Assuming doctorName is in the format "Dr FirstNameLastName"
        if (doctorName.startsWith("Dr ")) {
            doctorName = doctorName.substring(3); // Remove "Dr " prefix
        }
        return doctorName.replaceAll("\\s+", "") + "@gmail.com"; // Replace spaces and append "@gmail.com"
    }

    public void setHome(ActionEvent mouseEvent) {
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("chatBot.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)FAQ.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);

    }
    public void setHealth(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("baseHEALTH.fxml")); // Adjust the path
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) Health.getScene().getWindow();
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
