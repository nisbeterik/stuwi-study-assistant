package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.action.Action;
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
    private Button registerButton;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stuwiImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/StuWi-Transparent.png")));

        usernameField.setOnKeyPressed(this::handleKeyPress);
        passwordField.setOnKeyPressed(this::handleKeyPress); // If you press enter while typing your password or username
        // this will try to log you in with the credentials entered.
    }
    // This method run's when you press any key on your keyboard, and if said key is Enter, it tries to log you in with the credentials entered
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            logInUser(null, event); // Simulate ActionEvent when Enter key is pressed
        }
    }

    public void login(ActionEvent event, KeyEvent keyEvent){
        String enteredUsername = usernameField.getText().trim();
        String enteredPassword = passwordField.getText();
        boolean loginSuccess = false;

        try {
            loginSuccess = UserDAO.loginUser(enteredUsername, enteredPassword);
        } catch (Exception e) {
            displayAlert(Alert.AlertType.ERROR, "Login unsuccessful", "Username or password is incorrect. Please try again.");
            e.printStackTrace();
        }

        if (loginSuccess && keyEvent == null){
            redirect(event, "stuwi-home.fxml");
        } else if(loginSuccess && keyEvent != null){
            // If login is successful and triggered by Enter key
            keyEvent.consume(); // Consume the key event to prevent errors
            loginButton.fire(); // Simulate a mouse click event on the login button
        }
    }

    public void logInUser(ActionEvent event)  { // This method runs if you click on the log in button
        login(event, null);
    }

    public void logInUser(ActionEvent event, KeyEvent keyEvent)  { // This method handles when you log in with the use of the enter key
        if (event != null) {
            login(event, null);
        } else if (keyEvent != null) {
            login(null, keyEvent);
        }
    }

    public void goToRegistration(ActionEvent event) {
        redirect(event, "registration.fxml");
    }

    private ActionEvent createActionEvent() {
        return new ActionEvent();
    }

}
