package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SeeDoctorMessage {
    public AnchorPane patientmessage;
    public Label message;
    public Button back;
    public Label fromto;
    public void setFromto(String fromtoContent) {
        fromto.setText(fromtoContent);
    }
    public void setMessage(String messageContent) {
        message.setText(messageContent);
        message.setVisible(true);
    }
    public void handleBackAction(ActionEvent event) {
        Stage stage = (Stage) back.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
