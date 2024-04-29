package org.stuwiapp;

import java.time.LocalDateTime;

public class User {
    private String username;
    private String password;
    private LocalDateTime dateRegistered;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.dateRegistered = LocalDateTime.now();
    }


    public String createUsername(String name){
        // TODO Implement method for checking if username is valid
        return null;
    }

    public String createPassword(String name){
        // TODO Implement method for checking if password is valid
        return null;
    }

}
