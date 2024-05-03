package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class SessionOverviewController extends ParentController{
    @FXML
    public Button returnButton;
    @FXML
    public TableView sessionTable;

    public void redirectToHome(ActionEvent event) {redirect(event, "stuwi-home.fxml");
    }
}
