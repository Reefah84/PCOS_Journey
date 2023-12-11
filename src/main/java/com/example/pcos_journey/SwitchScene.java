package com.example.pcos_journey;


import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SwitchScene {

    public Button goBackButton;
    public Button agreeButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button yesButton1;
    @FXML
    private Button noButton1;
    @FXML
    private Button yesButton2;
    @FXML
    private Button noButton2;
    @FXML
    private Button yesButton3;
    @FXML
    private Button noButton3;
    @FXML
    private Button yesButton4;
    @FXML
    private Button noButton4;
    String answer;
    private static final PseudoClass PRESSED_CLASS = PseudoClass.getPseudoClass("pressed");
    private boolean isYesButton1Pressed = false;
    private boolean isNoButton1Pressed = false;
    private boolean isYesButton2Pressed = false;
    private boolean isNoButton2Pressed = false;
    private boolean isYesButton3Pressed = false;
    private boolean isNoButton3Pressed = false;
    private boolean isYesButton4Pressed = false;
    private boolean isNoButton4Pressed = false;
    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homepage_attempt2.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("quizpage.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==yesButton1)
        {
            isYesButton1Pressed = !isYesButton1Pressed;
            updateButtonStyle(yesButton1, isYesButton1Pressed);
            answer = "yes";
            if(answer.equals("yes"))
            {
                score++;
            }
        }
        if(e.getSource()==noButton1)
        {
            isNoButton1Pressed = !isNoButton1Pressed;
            updateButtonStyle(noButton1, isNoButton1Pressed);
            answer = "no";
            if(answer == "no")
            {

            }
        }
        if(e.getSource()==yesButton2)
        {
            isYesButton2Pressed = !isYesButton2Pressed;
            updateButtonStyle(yesButton2, isYesButton2Pressed);
            answer = "yes";
            if(answer == "yes")
            {
                score++;
            }
        }
        if(e.getSource()==noButton2)
        {
            isNoButton2Pressed = !isNoButton2Pressed;
            updateButtonStyle(noButton2, isNoButton2Pressed);
            answer = "no";
            if(answer == "no")
            {

            }
        }
        if(e.getSource()==yesButton3)
        {
            isYesButton3Pressed = !isYesButton3Pressed;
            updateButtonStyle(yesButton3, isYesButton3Pressed);
            answer = "yes";
            if(answer == "yes")
            {
                score++;
            }
        }
        if(e.getSource()==noButton3)
        {
            isNoButton3Pressed = !isNoButton3Pressed;
            updateButtonStyle(noButton3, isNoButton3Pressed);
            answer = "no";
            if(answer == "no")
            {

            }
        }
        if(e.getSource()==yesButton4)
        {
            isYesButton4Pressed = !isYesButton4Pressed;
            updateButtonStyle(yesButton4, isYesButton4Pressed);
            answer = "yes";
            if(answer == "yes")
            {
                score++;
            }
        }
        if(e.getSource()==noButton4)
        {
            isNoButton4Pressed = !isNoButton4Pressed;
            updateButtonStyle(noButton4, isNoButton4Pressed);
            answer = "no";
            if(answer == "no")
            {

            }
        }

    }

    @FXML

    private Button submitButton;

    private int score = 0;

    public void initialize(URL location, ResourceBundle resources)
    {
        yesButton1.setOnAction(event -> handleAnswer(event, true));
        noButton1.setOnAction(event -> handleAnswer(event, false));
        yesButton2.setOnAction(event -> handleAnswer(event, true));
        noButton2.setOnAction(event -> handleAnswer(event, false));
        yesButton3.setOnAction(event -> handleAnswer(event, true));
        noButton3.setOnAction(event -> handleAnswer(event, false));
        yesButton4.setOnAction(event -> handleAnswer(event, true));
        noButton4.setOnAction(event -> handleAnswer(event, false));
        yesButton1.setOnAction(this::actionPerformed);
        noButton1.setOnAction(this::actionPerformed);
        yesButton2.setOnAction(this::actionPerformed);
        noButton2.setOnAction(this::actionPerformed);
        yesButton3.setOnAction(this::actionPerformed);
        noButton3.setOnAction(this::actionPerformed);
        yesButton4.setOnAction(this::actionPerformed);
        noButton4.setOnAction(this::actionPerformed);
    }
    private void updateButtonStyle(Button button, boolean isPressed) {
        if (isPressed) {
            // Set the style for pressed state
            button.setStyle("-fx-background-color: #e7939f; -fx-text-fill: black;");
        } else {
            // Set the style for unpressed state
            button.setStyle("-fx-background-color: #ffc3ee; -fx-text-fill: black;");
        }
    }


    private void handleAnswer(ActionEvent event, boolean isYes)
    {
        if(isYes)
        {
            //System.out.println("hi");
            score++;
        }

        //System.out.println(score);
    }

    public void SubmitButton(ActionEvent event) throws IOException {
        //root = FXMLLoader.load(getClass().getResource("ScoringQuiz.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoringQuiz.fxml"));
        Parent root = loader.load();
        com.example.pcos_journey.ScoringQuiz scorequiz = loader.getController();
        scorequiz.setScore(score);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }


}
