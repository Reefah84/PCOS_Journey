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
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MedicineTracker implements Medicine{
    public Label errorMessage;
    public TextField medicineName;
    public TextField medicineFrequency;
    public TextField dosage;
    public Button view;
    public Circle home;
    User loggedInUser;
    String userEmail;
    public void initialize() {
        // Access the logged-in user from UserSession
        User loggedInUser = UserSession.getLoggedInUser();
        UserSession.getInstance().setUserEmail(loggedInUser.getUsername());
        if (loggedInUser != null) {
            userEmail = loggedInUser.getUsername();
        }
    }
    private void validateInput(String freq, String times) throws InvalidInputException {
        try {
            Integer.parseInt(freq);
            Integer.parseInt(times);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input: please enter integer values.");
        }
    }
    @Override
    public void handleSendEmailAction(ActionEvent event) {
        String name = medicineName.getText().trim();
        String freq = medicineFrequency.getText().trim();
        String dose = dosage.getText().trim();
        try {
            validateInput(freq, dose);
            int frequency = Integer.parseInt(freq);
            int dosageAmount = Integer.parseInt(dose);

            // Check for the special condition
            if (dosageAmount == 4 && frequency == 1) {
                // Send an email instantly
                sendEmailInstantly(name, dosageAmount);
                // Store the information with a special filename
                storeMedicineInfo(name + "_test_", frequency, dosageAmount, userEmail);
                errorMessage.setText("Medicine stored in database.");
                errorMessage.setVisible(true);
            } else {
                // Store medicine info and schedule emails normally
                storeMedicineInfo(name, frequency, dosageAmount, userEmail);
                calculateSchedule(dosageAmount, frequency);
            }

            // Proceed to show the popup
            showPopup();
        } catch (InvalidInputException | NumberFormatException e) {
            errorMessage.setText("Please enter valid numbers (1-3) for frequency and times per day.");
            errorMessage.setVisible(true);
            // You might want to log this exception or handle it further.
        } catch (IOException e) {
            errorMessage.setText("Error loading the window.");
            errorMessage.setVisible(true);
            e.printStackTrace();
        }
    }

    private void sendEmailInstantly(String medicineName, int dosage) {
        String message = "Time to take your medicine " + medicineName + "\nDosage: " + dosage;
        String subject = "Medicine Reminder";
        String to = userEmail;
        String from = "pcosjourney220@gmail.com";
        sendEmail(message, subject, to, from);
    }

    private void showPopup() throws IOException {
        // Load the new FXML file for the popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seeMyMedicine.fxml"));
        Parent root = loader.load();

        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.setResizable(false);
        popupStage.initModality(Modality.APPLICATION_MODAL); // To block interaction with other windows
        popupStage.setTitle("Medicine View");

        // Set the scene for the popup stage
        Scene scene = new Scene(root);
        popupStage.setScene(scene);

        // Show the popup and wait for it to be closed before returning to the main window
        popupStage.showAndWait();
    }
    private static final int ONE_DAY_MILLIS = (int) TimeUnit.DAYS.toMillis(1);
    private static final int MORNING_TIME_OFFSET = (int) TimeUnit.HOURS.toMillis(8); // 8 AM
    private static final int AFTERNOON_TIME_OFFSET = (int) TimeUnit.HOURS.toMillis(14); // 2 PM
    private static final int EVENING_TIME_OFFSET = (int) TimeUnit.HOURS.toMillis(20); // 8 PM
    //private static final int EVENING_TIME_OFFSET = (int) TimeUnit.HOURS.toMillis(24); // 12 AM

    // Method to calculate the schedule
    private void calculateSchedule(int dosage, int frequency) {
        Timer timer = new Timer();

        // Calculate the interval for the frequency
        int frequencyIntervalMillis = ONE_DAY_MILLIS * frequency;

        // Schedule the emails based on dosage and frequency
        if (dosage == 1) {
            // Morning email
            System.out.println("Sending 3 dosage");
            scheduleEmail(timer, userEmail, medicineName.getText(), Integer.parseInt(String.valueOf(dosage)), MORNING_TIME_OFFSET, frequencyIntervalMillis);
        }
        if (dosage == 2) {
            // Evening email
            System.out.println("Sending 3 dosage");
            scheduleEmail(timer, userEmail, medicineName.getText(), Integer.parseInt(String.valueOf(dosage)), EVENING_TIME_OFFSET, frequencyIntervalMillis);
        }
        if (dosage == 3) {
            // Afternoon email
            System.out.println("Sending 3 dosage");
            scheduleEmail(timer, userEmail, medicineName.getText(), Integer.parseInt(String.valueOf(dosage)), AFTERNOON_TIME_OFFSET, frequencyIntervalMillis);
        }
    }

    // Helper method to schedule an email with Timer
    private void scheduleEmail(Timer timer, String userEmail, String medicineName, int dosage, long timeOffset, int frequency) {
        // Calendar instance for manipulating date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, (int) TimeUnit.MILLISECONDS.toHours(timeOffset));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Adjust calendar to the next occurrence of the scheduled time
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        System.out.println("System time: "+System.currentTimeMillis());
        System.out.println("Calender time: "+calendar.getTimeInMillis());
        // TimerTask for sending email
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String message = "Time to take your medicine: " + medicineName + " tablet\nDosage: 1";
                String subject = "Medicine Reminder";
                sendEmail(message, subject, userEmail, "pcosjourney220@gmail.com");
            }
        };

        // Calculate the period for the timer based on the frequency
        long period = ONE_DAY_MILLIS * frequency;

        // Schedule the task for execution starting at the next occurrence and then repeat based on frequency
        timer.scheduleAtFixedRate(task, calendar.getTime(), period);
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
    private void storeMedicineInfo(String medicineName, int frequency, int dosage, String userEmail) {
        Path userFolder = Paths.get("E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\", userEmail);
        Path medicineFile = userFolder.resolve(medicineName + "_medicine.txt");

        // Ensure the user directory exists
        if (Files.notExists(userFolder)) {
            try {
                Files.createDirectories(userFolder);
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage.setText("Error: Unable to create user directory.");
                return; // Handle this appropriately
            }
        }

        // Store medicine info
        String content = medicineName + ";" + frequency + ";" + dosage + System.lineSeparator();
        try {
            Files.write(medicineFile, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.setText("Error: Unable to write to medicine file.");
            // Handle this appropriately
        }
    }
    public void setBackHome(MouseEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard_user.fxml"));
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

}
