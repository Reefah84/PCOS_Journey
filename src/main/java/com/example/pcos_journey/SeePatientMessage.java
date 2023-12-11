package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SeePatientMessage extends dashboardDoctorController{
    public AnchorPane patientmessage;
    public Label message;
    public Button reply;
    public Button back;
    public Label fromto;
    public void setFromto(String fromtoContent) {
        fromto.setText(fromtoContent);
        fromto.setVisible(true);
    }
    public void setMessage(String messageContent) {
        message.setText(messageContent);
        message.setVisible(true);
    }
    public void handleReplyAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("replyMailPopup.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) reply.getScene().getWindow();
            currentStage.setResizable(false);
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleBackAction(ActionEvent event) {
        Stage stage = (Stage) back.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
