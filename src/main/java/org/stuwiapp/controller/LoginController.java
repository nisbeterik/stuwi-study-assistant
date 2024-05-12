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
import javafx.scene.layout.AnchorPane;
import org.stuwiapp.database.UserDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends ParentController implements Initializable {

    public AnchorPane background;
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
        background.getStyleClass().add("pane"); //sets the style class for the background AnchorPane to "pane".

        stuwiImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/StuWi-Transparent.png")));
    }


    public void logInUser(ActionEvent event) {
        String enteredUsername = usernameField.getText().trim();
        String enteredPassword = passwordField.getText();
        boolean loginSuccess = false;

        try {
            loginSuccess = UserDAO.loginUser(enteredUsername, enteredPassword);
        } catch (Exception e) {
            displayAlert(Alert.AlertType.ERROR, "Login unsuccessful", "Username or password is incorrect. Please try again.");
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
