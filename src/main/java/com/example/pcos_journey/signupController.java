package com.example.pcos_journey;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;


public class signupController {

    public DatePicker enterdob;
    public TextField enteremail;
    public PasswordField enterpass;
    public CheckBox terms;
    public Button signupbutton;
    public Button loginbutton;
    public Label errorlabel;
    public CheckBox doctorcheck;
    public CheckBox usercheck;
    public Circle homepage;
    public RadioButton showPass;
    public RadioButton showPassword;
    public TextField plainTextField;
    public void initialize() {
        enterpass.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!showPassword.isSelected()) {
                plainTextField.setText(newValue);
            }
        });
        plainTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (showPassword.isSelected()) {
                enterpass.setText(newValue);
            }
        });
    }
    private void showError(String errorMessage) {
        errorlabel.setText(errorMessage);
        errorlabel.setVisible(true);
    }
    public void setSignupbutton(ActionEvent event)
    {
        String email = enteremail.getText();
        String pass = enterpass.getText();
        LocalDate DOB = enterdob.getValue();
        if (email.isEmpty() || pass.isEmpty() || DOB == null) {
            showError("Please fill in all the fields.");
        } else if (!terms.isSelected()) {
            showError("Please check terms and conditions.");
        } else if (!usercheck.isSelected() && !doctorcheck.isSelected()) {
            showError("Please select one type of user.");
        }
        else if (!email.endsWith("@gmail.com")){
            showError("Invalid email. Please provide a correct Gmail address.");
        }else {
            // Determine the file name based on the selected checkbox
            String userFolderName = usercheck.isSelected() ? "UserData" : "DRData";

            // Create a folder with the user's email
            String dob = DOB.toString();
            String userFolderPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\" + userFolderName + "\\" + email;
            File userFolder = new File(userFolderPath);
            userFolder.mkdir();

            // Create a file within the folder to store the user's information
            String userFileName = usercheck.isSelected() ? "USER" : "DR";
            File userInfoFile = new File(userFolder, userFileName + "_info.txt");

            try (FileWriter writer = new FileWriter(userInfoFile)) {
                // Write the appropriate prefix (USER or DR) based on the selected checkbox
                String userPrefix = usercheck.isSelected() ? "USER" : "DR";
                writer.write(userPrefix + "\n");
                writer.write("Email: " + email + "\n");
                writer.write("Password: " + pass + "\n");
                writer.write("Date of Birth: " + dob + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            showError("User account is created");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event2 -> switchToLogin());
            pause.play();
        }
    }
    private void switchToLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml")); // Update this path
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) errorlabel.getScene().getWindow(); // Replace showError with any other control in your scene if needed
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception here
        }
    }
    public void setLoginbutton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)loginbutton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
    }
    public void setBackHome(MouseEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) homepage.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void toggleShowPassword(ActionEvent event) {
        if (showPassword.isSelected()) {
            // Show plain text field and hide password field
            plainTextField.setVisible(true);
            plainTextField.setManaged(true);

            enterpass.setVisible(false);
            enterpass.setManaged(false);
        } else {
            // Show password field and hide plain text field
            enterpass.setVisible(true);
            enterpass.setManaged(true);

            plainTextField.setVisible(false);
            plainTextField.setManaged(false);
        }
    }
}