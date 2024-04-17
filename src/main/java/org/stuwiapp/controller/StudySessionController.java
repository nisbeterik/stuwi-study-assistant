package org.stuwiapp.controller;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class StudySessionController extends ParentController{
    public Button backToDashboard;

    public void redirectDashboard(MouseEvent mouseEvent) {
        redirect(mouseEvent, "dashboard.fxml");
    }
}
