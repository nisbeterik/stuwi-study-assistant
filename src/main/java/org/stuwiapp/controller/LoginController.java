package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.stuwiapp.database.UserDAO;

public class LoginController extends ParentController {

    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registrationButton;
    @FXML
    private PasswordField passwordField;


    public void logInUser(ActionEvent event) {
        String enteredUsername = usernameField.getText().trim();
        String enteredPassword = passwordField.getText();
        boolean loginSuccess = false;

        try {
            loginSuccess = UserDAO.loginUser(enteredUsername, enteredPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loginSuccess){
            redirect(event, "stuwi-home.fxml");
        }
    }

    public void goToRegistration(ActionEvent event) {
        redirect(event, "registration.fxml");
    }
}
