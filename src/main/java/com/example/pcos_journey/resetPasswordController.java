package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class resetPasswordController extends sharedOTP{

    public Button SaveButton;
    public Label incorrect;
    public PasswordField newPass;
    public PasswordField confirmPass;
    // In the controller where you need the email
    String email = sharedOTP.getEmail();
// Now you can use this email to perform operations like changing the password
    private boolean isPasswordMatching() {
        return newPass.getText().equals(confirmPass.getText());
    }
    private void updatePasswordIfDifferent() throws IOException {
        String userDataPath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/UserData/" + email + "/USER_info.txt";
        String drDataPath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/DRData/" + email + "/Dr_info.txt";

        // Check and update password at userDataPath
        checkAndUpdatePassword(userDataPath);

        // Check and update password at drDataPath
        checkAndUpdatePassword(drDataPath);
    }

    private void checkAndUpdatePassword(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);

            // Find the line with the password and update it
            List<String> updatedLines = lines.stream()
                    .map(line -> line.startsWith("Password: ") ? "Password: " + newPass.getText() : line)
                    .collect(Collectors.toList());

            // Write the updated lines back to the file
            Files.write(Paths.get(filePath), updatedLines, StandardCharsets.UTF_8);
            incorrect.setText("Password updated successfully");
            incorrect.setVisible(true);
        } else {
            incorrect.setText("User account not found.");
            incorrect.setVisible(true);
        }
    }
    public void setSaveButton(ActionEvent event)
    {
        if (!isPasswordMatching()) {
            incorrect.setText("Please give the same password in two fields");
            incorrect.setVisible(true);
            return; // Stop further execution if passwords don't match
        }
        try {
            updatePasswordIfDifferent();
        } catch (IOException e) {
            e.printStackTrace();
            incorrect.setText("An error occurred while updating the password.");
            incorrect.setVisible(true);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml")); // Adjust the path
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)SaveButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            incorrect.setText("Failed to load reset password screen.");
            incorrect.setVisible(true);
        }
    }
}