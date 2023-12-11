package com.example.pcos_journey;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ReplyMailController {
    public TextArea message;
    public Button sendButton;
    public TextField user;
    public Button backhome;
    public Label errorMessage;
    public TextField reply; // Doctor's email field
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleSendButtonAction(ActionEvent event) {
        String userMessage = message.getText();
        String doctorEmail = user.getText();
        String userEmail = reply.getText();

        // Email subject
        String subject = "Reply from " + doctorEmail;

        // Send email
        if (sendEmail(userMessage, subject, userEmail, "pcosjourney220@gmail.com")) {
            // If email is sent, store the message
            if (storeMessage(userMessage, userEmail, doctorEmail)) {
                // Close the popup window
                if (stage != null) {
                    stage.close();
                    errorMessage.setText("Failed to close popup");
                }
            } else {
                errorMessage.setText("Failed to store the message. Try again.");
            }
        } else {
            errorMessage.setText("Error in sending mail.");
        }
    }

    public void handleGoBackButtonAction(ActionEvent event) {
        if (stage != null) {
            stage.close();
        } else {
            Stage currentStage = (Stage) backhome.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }
        }
    }

    private boolean sendEmail(String message, String subject, String to, String from) {
        // SMTP server settings
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
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean storeMessage(String message, String userEmail, String doctorEmail) {
        // Generate a timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-mm-ss"));

        // File paths for storing the message
        String doctorFilePath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/DRData/" + doctorEmail + "/reply_to_" + userEmail + "_" + timestamp + ".txt";
        String userFilePath = "E:/Java/PCOS_Journey/src/main/java/com/example/pcos_journey/UserData/" + userEmail + "/reply_from_" + doctorEmail + "_" + timestamp + ".txt";

        // Create the files
        File doctorFile = new File(doctorFilePath);
        File userFile = new File(userFilePath);

        try {
            // Write the messages to the files
            return writeToFile(doctorFile, "User Email: " + userEmail + "\nMessage: " + message) &&
                    writeToFile(userFile, "Doctor Email: " + doctorEmail + "\nMessage: " + message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean writeToFile(File file, String content) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
