package com.example.pcos_journey;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.event.ActionEvent;

public class loginController {
    @FXML
    private TextField username;
    private PasswordField password;
    public Button signupbutton;
    @FXML
    public  Button loginbutton;
    public void setLoginbutton(ActionEvent event)
    {
        Stage stage=(Stage) loginbutton.getScene().getWindow();
        stage.close();
    }


}