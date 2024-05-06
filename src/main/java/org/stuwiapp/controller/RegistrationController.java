package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.stuwiapp.database.UserDAO;

public class RegistrationController extends ParentController{
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    public void registerNewUser(ActionEvent event) {
        String enteredUsername = usernameField.getText().trim();
        String enteredPassword = passwordField.getText();
        boolean registrationSuccess = false;

        try {
            registrationSuccess = UserDAO.registerUser(enteredUsername, enteredPassword);
        } catch (Exception e) {
            displayAlert(Alert.AlertType.ERROR,"Registration unsuccessful", e.getMessage());
            e.printStackTrace();
        }

        if (registrationSuccess){
            redirect(event, "stuwi-home.fxml");
        }
    }

    public void returnToLogin(ActionEvent event) {
        redirect(event, "login.fxml");
    }
}
