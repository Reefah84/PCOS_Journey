package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BaseDietController {
    public Button FAQ;
    public TitledPane titledPane1;
    public TitledPane titledPane2;
    public TitledPane titledPane3;
    public TitledPane titledPane4;
    public Circle home;
    public Button gy;
    public Button LoginButton;
    public Button Health;
    public Button back;

    public void initialize() {
        titledPane1.setExpanded(false);
        titledPane2.setExpanded(false);
        titledPane3.setExpanded(false);
        titledPane4.setExpanded(false);
        updateLoginButton();
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
    public void setHome(MouseEvent event){
        try {
            // Load the new FXML file
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


    public void dbackbtn(MouseEvent mouseEvent) {
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
