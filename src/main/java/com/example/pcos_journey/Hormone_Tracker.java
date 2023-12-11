package com.example.pcos_journey;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

;


public class Hormone_Tracker implements HormoneTracker {


    public Label labelside;
    public Label labeup;
    public Circle homeButton;
    public MenuButton menuButton;

    @FXML
    private Label label;

    @FXML
    private MenuItem item1;

    @FXML
    private MenuItem item2;

    @FXML
    private MenuItem item3;

    @FXML
    private TextField value;

    @FXML
    private Label result;

    @FXML
    private Button submit;

    String enteredvalue;
    float valuee;
    User loggedInUser;
    public void initialize(ActionEvent actionEvent) {
        URL url = null;
        ResourceBundle resourceBundle = null;
        initialize2(url,resourceBundle);
    }
    public void initialize2(URL url, ResourceBundle resourceBundle) {
        loggedInUser = UserSession.getLoggedInUser();
        if (loggedInUser != null && !loggedInUser.isDoctor()) {
            System.out.println(loggedInUser.getUsername() + " logged in");
            // Other initialization code...
        } else {
            System.out.println("User is not logged in or is a doctor.");
            // Handle the case where the user is not logged in or is a doctor
        }
        item1.setOnAction(event -> {
            label.setText("Testosteron Tracker:");

            submit.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    enteredvalue = value.getText();
                    String labelContent = label.getText();
                    String[] parts = labelContent.split(" Tracker:");
                    String hormone = parts[0];

                    if (loggedInUser != null && !loggedInUser.isDoctor()) {
                        saveHormoneLevel(loggedInUser.getUsername(), hormone, enteredvalue);
                        System.out.println(loggedInUser.getUsername() + " logged in");
                    } else {
                        System.out.println("User is not logged in or is a doctor.");
                    }

                    if(!enteredvalue.isEmpty())
                    {
                        valuee = Float.parseFloat(value.getText());
                        //System.out.println(valuee);
                    }
                    else
                    {
                        //valuee = Float.parseFloat(enteredvalue);
                        //System.out.println(valuee);
                    }

                    if(valuee>5.71 && valuee<77)
                    {
                        result.setText("Your Testosteron level is in range. Reference Range is: 5.71-77 ng/dl");
                    }
                    else if(valuee<5.71)
                    {
                        result.setText("Your Testosteron level is low. Reference Range is: 5.71-77 ng/dl");
                    }
                    else
                    {
                        result.setText("Your Testosteron level is high. Reference Range is: 5.71-77 ng/dl");
                    }

                }

            });


        });

        item2.setOnAction(event -> {
            label.setText("Prolactin Tracker:");

            submit.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    enteredvalue = value.getText();
                    String labelContent = label.getText();
                    String[] parts = labelContent.split(" Tracker:");
                    String hormone = parts[0];

                    if (loggedInUser != null && !loggedInUser.isDoctor()) {
                        saveHormoneLevel(loggedInUser.getUsername(), hormone, enteredvalue);
                        System.out.println(loggedInUser.getUsername() + " logged in");
                    } else {
                        System.out.println("User is not logged in or is a doctor.");
                    }

                    if(!enteredvalue.isEmpty())
                    {
                        valuee = Float.parseFloat(value.getText());
                        //System.out.println(valuee);
                    }
                    else
                    {
                        //valuee = Float.parseFloat(enteredvalue);
                        //System.out.println(valuee);
                    }

                    if(valuee > 1 && valuee < 25)
                    {
                        result.setText("Your Prolactin level is in range. Reference Range is: 1-25 ng/ml");
                    }
                    else if(valuee < 1)
                    {
                        result.setText("Your Prolactin level is low. Reference Range is: 1-25 ng/ml");
                    }
                    else
                    {
                        result.setText("Your Prolactin level is high. Reference Range is: 1-25 ng/ml");
                    }

                }

            });


        });

        item3.setOnAction(event -> {
            label.setText("TSH Tracker:");

            submit.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    enteredvalue = value.getText();
                    String labelContent = label.getText();
                    String[] parts = labelContent.split(" Tracker:");
                    String hormone = parts[0];

                    if (loggedInUser != null && !loggedInUser.isDoctor()) {
                        saveHormoneLevel(loggedInUser.getUsername(), hormone, enteredvalue);
                        System.out.println(loggedInUser.getUsername() + " logged in");
                    } else {
                        System.out.println("User is not logged in or is a doctor.");
                    }

                    if(!enteredvalue.isEmpty())
                    {
                        valuee = Float.parseFloat(value.getText());
                        //System.out.println(valuee);
                    }
                    else
                    {
                        //valuee = Float.parseFloat(enteredvalue);
                        //System.out.println(valuee);
                    }

                    if(valuee > 0.35 && valuee < 5.50)
                    {
                        result.setText("Your TSH level is in range. Reference Range is: 0.35-5.50 uIU/ml");
                    }
                    else if(valuee < 0.35)
                    {
                        result.setText("Your TSH level is low. Reference Range is: 0.35-5.50 uIU/ml");
                    }
                    else
                    {
                        result.setText("Your TSH level is high. Reference Range is: 0.35-5.50 uIU/ml");
                    }

                }
            });
        });
    }
    public void saveHormoneLevel(String userEmail, String hormoneName, String hormoneLevel) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm-ss");
        String timestamp = now.format(formatter);
        String fileName = hormoneName.replaceAll("\\s+", "_")+"_levels_" + timestamp + ".txt";
        String userDirectoryPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + userEmail;
        File userDirectory = new File(userDirectoryPath);
        File outputFile = new File(userDirectory, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("Hormone: " + hormoneName + "\n");
            writer.write("Level: " + hormoneLevel + "\n");
            writer.write("Timestamp: " + timestamp + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    public void setHomeButton(MouseEvent event) {
        try {
            // Check if a user is logged in
            User loggedInUser = UserSession.getLoggedInUser();

            FXMLLoader loader = null;
            if (loggedInUser != null) {
                // Check whether the logged-in user is a doctor or a regular user
                if (loggedInUser.isUser()) {
                    // Redirect to the user dashboard
                    loader = new FXMLLoader(getClass().getResource("dashboard_user.fxml"));
                } else {
                    System.out.println("Unknown user type detected!");
                }
            } else {
                // Redirect to the home page if no user is logged in
                loader = new FXMLLoader(getClass().getResource("homepage_attempt2.fxml"));
            }

            Parent root = loader.load();
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error dialog)
        }
    }

    
}