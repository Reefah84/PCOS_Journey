package com.example.pcos_journey;

import java.io.File;

public class User {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUser() {
        String userPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\UserData\\" + username;
        File userDir = new File(userPath);
        return userDir.exists() && userDir.isDirectory();
    }

    public boolean isDoctor() {
        String doctorPath = "E:\\Java\\PCOS_Journey\\src\\main\\java\\com\\example\\pcos_journey\\DRData\\" + username;
        File doctorDir = new File(doctorPath);
        return doctorDir.exists() && doctorDir.isDirectory();
    }
}

