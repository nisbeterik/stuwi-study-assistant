package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StuwiHomeController extends ParentController{

    @FXML Button overviewButton;
    @FXML public Button newSessionButton;

    public void newSession(ActionEvent event) {
        redirect(event, "study-session-configuration.fxml");
    }
}


