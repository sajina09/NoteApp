package com.example.mg.noteappfirebase.Models;

public class userModel {

    String nodeAddress;
    String username;
    String email;
    String password;

    public userModel(String nodeAddress, String username, String email, String password) {
        this.nodeAddress = nodeAddress;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public userModel() {
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
