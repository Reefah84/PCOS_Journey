package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Properties;


public class forgotpasswordController extends sharedOTP{

    public Button sendotp;
    public TextField enteremail;
    public Label incorrect;
    public Circle home;
    String generatedOTP;
    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Generate 6-digit OTP
        return String.valueOf(otp);
    }
    private boolean isEmailRegistered(String email) {
        String drDataPath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/DRData";
        String userDataPath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/UserData";

        File drDataDir = new File(drDataPath, email);
        File userDataDir = new File(userDataPath, email);

        return drDataDir.exists() || userDataDir.exists();
    }

    // Method to send email with OTP
    private void sendEmail(String message, String subject, String to, String from) {
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pcosjourney220@gmail.com", "voklbqljusokjqef");
            }
        });
        session.setDebug(true);
        try {
            MimeMessage m = new MimeMessage(session);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            m.setText(message);
            Transport.send(m);
            incorrect.setText("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Method to validate the email and send OTP
    private boolean validateAndSendOTP() throws EmailException {
        String email = enteremail.getText();
        if (email.isEmpty() || !email.endsWith("@gmail.com")) {
            throw new EmailException("Please enter a valid Gmail address.");
        } else {
            generatedOTP = generateOTP();
            sharedOTP.setCurrentOTP(generatedOTP);
            sharedOTP.setEmail(email);
            String fullMessage = "Hello! Welcome to PCOS Journey.\nHere is your OTP code: " + generatedOTP;
            sendEmail(fullMessage, "PCOS Journey Forgot Password", email, "pcosjourney220@gmail.com");
            incorrect.setVisible(false);
            return true;
        }
    }

    // Event handler for the next button
    public void setSendotp(ActionEvent event) throws EmailException {
        setSaveButton(event);
    }
    public void setHome(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
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

    @Override
    public void setSaveButton(ActionEvent event) throws EmailException {
        if (!isEmailRegistered(enteremail.getText())) {
            incorrect.setText("User account not found");
            incorrect.setVisible(true);
        }
        if (!validateAndSendOTP()) {
            incorrect.setText("Failed to send OTP. Please check the email provided and try again.");
            incorrect.setVisible(true);
        } else {
            // This block will now only execute if validateAndSendOTP returns true, meaning the email was sent
            incorrect.setText("Email sent successfully.");
            incorrect.setVisible(true);
            // Load and transition to the new FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OTP_code.fxml")); // Adjust the path
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage)sendotp.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setResizable(false);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("button.css")).toExternalForm());
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                incorrect.setText("Failed to load the next screen.");
            }
        }
    }
}