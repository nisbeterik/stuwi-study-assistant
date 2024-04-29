package org.stuwiapp;

public class UserManager {

    private static UserManager instance;
    private String currentUser;
    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public String getCurrentUser() {return currentUser;}

    public void setCurrentUser(String user) {
        this.currentUser = user;
    }

    public void resetCurrentUser() {this.currentUser = null;}
}
