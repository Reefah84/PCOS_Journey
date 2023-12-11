package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ChatBot {
    public Button messagesent;
    public TextField userInput;
    public Button sendMessageButton;
    public VBox messages;
    public ScrollPane scroll;
    public Button LoginButton;
    public Button Health;
    public Button gy;
    public Circle home;
    public MenuItem nutrition;
    public void initialize() {
        messages.setPrefWidth(942);
        messages.setFillWidth(true);
        updateLoginButton();
    }
    private void updateLoginButton() {
        if (UserSession.getInstance().isUserLoggedIn()) {
            User loggedInUser = UserSession.getLoggedInUser();
            if (loggedInUser.isUser()) {
                LoginButton.setText("USER");
                LoginButton.setOnAction(this::openDashboardUser);
            } else if (loggedInUser.isDoctor()) {
                LoginButton.setText("DOCTOR");
                LoginButton.setOnAction(this::openDashboardDoctor);
            }
        } else {
            LoginButton.setText("LOG IN");
            LoginButton.setOnAction(this::openLogin);
        }
    }

    private void openDashboardUser(ActionEvent event) {
        navigateTo("dashboard_user.fxml", event);
    }

    private void openDashboardDoctor(ActionEvent event) {
        navigateTo("dashboard_doctor.fxml", event);
    }

    private void openLogin(ActionEvent event) {
        navigateTo("login.fxml", event);
    }

    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setBackHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage_attempt2.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showListOfDoctors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListofDoctors.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) gy.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    public void setLoginButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage)LoginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
        stage.setScene(scene);
    }
    public void onSendMessage(ActionEvent event) {
        String message = userInput.getText().trim();
        if (!message.isEmpty()) {
            double scrollVvalueBefore = scroll.getVvalue();
            // Create and add user message label
            Label userMessageLabel = createLabel("User:\n" + message, "-fx-background-color: pink; -fx-text-fill: black; -fx-border-color: #ed407b;", 22);
            messages.getChildren().add(userMessageLabel);

            // Get and add bot response label
            String response = getResponse(message);
            Label botMessageLabel = createLabel("PCOS Journey:\n" + response, "-fx-background-color: lightblue; -fx-text-fill: black; -fx-border-color: #b58aff;", 22);
            messages.getChildren().add(botMessageLabel);

            // Scroll to the bottom of the VBox to make the latest message visible
            messages.requestLayout(); // Make sure layout is updated
            scroll.layout(); // This will ensure the layout pass happens now
            if (scrollVvalueBefore == scroll.getVmax()) {
                scroll.setVvalue(scroll.getVmax()); // User was at the bottom, scroll to the bottom
            } else {
                scroll.setVvalue(scrollVvalueBefore); // User was not at the bottom, maintain the position
            } // Scroll to the bottom

            // Clear the input field for the next message
            userInput.clear();
        }
    }
    // Helper method to create a label with a specific style and font size
    private Label createLabel(String text, String style, int fontSize) {
        Label label = new Label(text);
        label.setStyle(style + " -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        label.setFont(new Font(fontSize)); // Set the font size here
        label.setWrapText(true); // Enable text wrapping within the label
        label.setMaxWidth(messages.getPrefWidth() / 2 - 20); // Half of VBox width minus padding
        //label.setMinHeight(Region.USE_PREF_SIZE); // Ensure label height grows with content
        return label;
    }

    private String getResponse(String userMessage) {
        Path path = Path.of("E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\responses.txt"); // Ensure this path is correct.
        String lowerCaseUserMessage = userMessage.toLowerCase(); // Convert user message to lower case.
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts[0].trim().equalsIgnoreCase(userMessage.trim())) {
                    return parts[1].trim(); // Return the response part.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Sorry, I don't know about this."; // Default response.
    }
}
