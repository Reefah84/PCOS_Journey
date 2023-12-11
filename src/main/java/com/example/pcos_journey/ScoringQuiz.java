package com.example.pcos_journey;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ScoringQuiz {

    @FXML
    private Label scoreLabel;

    public void setScore(int score) {

        if(score == 0)
        {
            scoreLabel.setText("Congratulations! You are NOT diagnosed with PCOS.");
        }
        else if(score==4)
        {
            scoreLabel.setText("You HAVE PCOS. Please contact with a professional doctor for more information.");
        }
        else
        {
            scoreLabel.setText("You MIGHT have PCOS. Consult with a professional doctor");
        }

    }

    public void switchToScene3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homepage_attempt2.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


}

