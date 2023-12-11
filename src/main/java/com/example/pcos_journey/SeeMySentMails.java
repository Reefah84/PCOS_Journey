package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SeeMySentMails {
    public ListView <String> sentMessage;
    public Button close;
    String userEmail;
    public void initialize()
    {
        this.userEmail = UserSession.getInstance().getUserEmail();
        loadsentMessage();
        sentMessage.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedReply = sentMessage.getSelectionModel().getSelectedItem();
                if (selectedReply != null) {
                    openPatientMessagePopup(selectedReply);
                }
            }
        });
    }

    private void openPatientMessagePopup(String fileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeeDoctorMessage.fxml"));
            Parent root = loader.load();

            SeeDoctorMessage controller = loader.getController();
            String filePath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + userEmail + "\\" + fileName.replace("Contacted ", "sent_mail_to_").replace(" ", "_") + ".txt";
            String messageContent = new String(Files.readAllBytes(Paths.get(filePath)));
            controller.setMessage(messageContent);
            controller.setFromto("Message to Doctor");
            Stage stage = new Stage();
            stage.setTitle("Doctor's Message");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadsentMessage() {
        String userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\"+userEmail;
        File userDirectory = new File(userDirectoryPath);
        String[] replies = userDirectory.list((dir, name) -> name.startsWith("sent_mail_to"));
        if (replies != null) {
            sentMessage.getItems().addAll(
                    Arrays.stream(replies)
                            .map(this::formatFileName)
                            .collect(Collectors.toList())
            );
        }
    }
    private String formatFileName(String fileName) {
        return fileName.replace("reply_from_", "Message from ")
                .replace("sent_mail_to_", "Contacted ")
                .replace("_", " ")
                .replace(".txt", "");
    }
    public void handleCloseButtonAction(ActionEvent event) {
        // Get the current stage from the button and close it
        Stage stage = (Stage) close.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
