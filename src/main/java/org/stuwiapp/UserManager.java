package org.stuwiapp;

public class UserManager {

    private static UserManager instance;
    private static String currentUser;
    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public String getCurrentUser() {return currentUser;}

    public void setCurrentUser(String user) {
        currentUser = user;
        System.out.println("Current user is now: " + currentUser);
    }

    public void resetCurrentUser() {this.currentUser = null;}
}
