package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.stuwiapp.RangeSettingsTemplate;
import org.stuwiapp.UserManager;
import org.stuwiapp.database.LatestSettingsDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class StuwiHomeController extends ParentController implements Initializable {

    public AnchorPane background;

    public void initialize(URL url, ResourceBundle resourceBundle){
        background.getStyleClass().add("pane");

        RangeSettingsTemplate latestRangeTemplate =  LatestSettingsDAO.getLatestRangeTemplate(UserManager.getInstance().getCurrentUser());
        if (latestRangeTemplate == null) {
            try {
                latestRangeTemplate.publishRangeSettings();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML public Button settingsButton;
    @FXML Button overviewButton;
    @FXML public Button newSessionButton;

    public void newSession(ActionEvent event) {
        redirect(event, "study-session-configuration.fxml");
    }

    public void redirectToOverview(ActionEvent event) {redirect(event, "session-overview.fxml");

    }
    public void redirectToSettings(ActionEvent event) {redirect(event, "range-settings.fxml");

    }
}


