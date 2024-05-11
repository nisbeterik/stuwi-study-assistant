package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.stuwiapp.RangeSettingsTemplate;
import org.stuwiapp.UserManager;
import org.stuwiapp.database.LatestSettingsDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class StuwiHomeController extends ParentController implements Initializable {

    @FXML private Button analyticsButton;
    @FXML private Button settingsButton;
    @FXML private Button overviewButton;
    @FXML private Button newSessionButton;

    public void initialize(URL url, ResourceBundle resourceBundle){
        RangeSettingsTemplate latestRangeTemplate =  LatestSettingsDAO.getLatestRangeTemplate(UserManager.getInstance().getCurrentUser());
        if (latestRangeTemplate == null) {
            try {
                latestRangeTemplate.publishRangeSettings();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void newSession(ActionEvent event) {
        redirect(event, "study-session-configuration.fxml");
    }

    public void redirectToOverview(ActionEvent event) {redirect(event, "session-overview.fxml");

    }
    public void redirectToSettings(ActionEvent event) {redirect(event, "range-settings.fxml");

    }

    public void redirectToAnalytics(ActionEvent actionEvent) {
        redirect(actionEvent, "study-sessions-graph.fxml");
    }
}


