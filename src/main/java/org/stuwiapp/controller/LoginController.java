package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.stuwiapp.database.UserDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends ParentController implements Initializable {

    @FXML
    private ImageView stuwiImage;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registrationButton;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stuwiImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/stuwi-logo.png")));
    }


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
