package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class myProfile {
    public Button save;
    public Label username;
    public Label tsh;
    public Label testosterone;
    public Label prolactine;
    public Label weight;
    public Label height;
    public PasswordField pass;
    public Button medicine;
    public PasswordField confirmpass;
    public Label incorrect;
    User loggedInUser = UserSession.getLoggedInUser();
    String email;

    public void initialize() {
        email = loggedInUser.getUsername();
        username.setText(email);
        loadLatestHormoneLevels();
        loadLatestWeightAndHeight();
    }
    private void loadLatestHormoneLevels() {
        loadLatestHormoneLevel("Testosteron", testosterone);
        loadLatestHormoneLevel("TSH", tsh);
        loadLatestHormoneLevel("Prolactin", prolactine);
    }

    private void loadLatestHormoneLevel(String hormoneName, Label hormoneLabel) {
        String userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + email;
        File userDirectory = new File(userDirectoryPath);

        File[] files = userDirectory.listFiles((dir, name) -> name.startsWith(hormoneName) && name.endsWith(".txt"));
        if (files != null && files.length > 0) {
            File latestFile = Stream.of(files).max(Comparator.comparingLong(File::lastModified)).orElse(null);
            if (latestFile != null) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(latestFile.getAbsolutePath())));
                    Pattern pattern = Pattern.compile("Level: (\\d+(\\.\\d+)?)");
                    Matcher matcher = pattern.matcher(content);
                    if (matcher.find()) {
                        hormoneLabel.setText(matcher.group(1));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadLatestWeightAndHeight() {
        String userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + email;
        File userDirectory = new File(userDirectoryPath);

        File[] files = userDirectory.listFiles((dir, name) -> name.startsWith("weight_and_height") && name.endsWith(".txt"));
        if (files != null && files.length > 0) {
            File latestFile = Stream.of(files).max(Comparator.comparingLong(File::lastModified)).orElse(null);
            if (latestFile != null) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get(latestFile.getAbsolutePath()));
                    // Assuming the first line is weight and the second line is height
                    if (!lines.isEmpty()) {
                        weight.setText(lines.get(0).split(":")[1].trim()); // "Weight: value"
                        height.setText(lines.get(1).split(":")[1].trim()); // "Height: value"
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle exception (e.g., show an error message)
                }
            }
        }
    }
    private boolean isCurrentPasswordValid(String enteredPassword) {
        String userInfoPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + email + "\\USER_info.txt";
        File userInfoFile = new File(userInfoPath);

        try {
            List<String> lines = Files.readAllLines(userInfoFile.toPath());
            for (String line : lines) {
                if (line.startsWith("Password:")) {
                    String currentPassword = line.substring("Password:".length()).trim();
                    return currentPassword.equals(enteredPassword);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onSave() {
        if (!confirmpass.getText().isEmpty() && isCurrentPasswordValid(confirmpass.getText())) {
            if (!pass.getText().isEmpty()) {
                updatePassword(pass.getText());
                incorrect.setText("Password changed successfully");
                incorrect.setVisible(true);
            }
            closeWindow();
        } else {
            incorrect.setText("Please enter your current password correctly");
            incorrect.setVisible(true);
        }
    }


    private void updatePassword(String newPassword) {
        String userInfoPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + email + "\\USER_info.txt";
        File userInfoFile = new File(userInfoPath);

        try {
            // Read all lines
            List<String> lines = Files.readAllLines(userInfoFile.toPath());
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("Password:")) {
                    // Update the password line
                    lines.set(i, "Password: " + newPassword);
                    break;
                }
            }

            // Rewrite the file
            Files.write(userInfoFile.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception (e.g., show an error message)
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }
    public void showPopup(ActionEvent event) throws IOException {
        // Load the new FXML file for the popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seeMyMedicine.fxml"));
        Parent root = loader.load();

        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // To block interaction with other windows
        popupStage.setTitle("Medicine View");

        // Set the scene for the popup stage
        Scene scene = new Scene(root);
        popupStage.setScene(scene);
        popupStage.setResizable(false);
        // Show the popup and wait for it to be closed before returning to the main window
        popupStage.showAndWait();
    }
}


