package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.stuwiapp.StudySession;
import org.stuwiapp.UserManager;
import org.stuwiapp.database.StudySessionDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SessionOverviewController extends ParentController implements Initializable {

    @FXML
    private Button returnButton;

    @FXML
    private TableView<StudySession> sessionTable;
    @FXML
    private TableColumn<StudySession, String> startDateColumn;
    @FXML
    private TableColumn<StudySession, String> endDateColumn;
    @FXML
    private TableColumn<StudySession, Integer> durationColumn;
    @FXML
    private TableColumn<StudySession, Double> tempColumn;
    @FXML
    private TableColumn<StudySession, Double> humidColumn;
    @FXML
    private TableColumn<StudySession, Double> loudColumn;
    @FXML
    private TableColumn<StudySession, Integer> ratingColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // String currentUser = UserManager.getInstance().getCurrentUser();
        // ArrayList<StudySession> sessions = StudySessionDAO.getUserSessions(currentUser);
        // sessionTable.getItems().addAll(sessions);
        // startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        // endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        //durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        // tempColumn.setCellValueFactory(new PropertyValueFactory<>("avgTemp"));
        // humidColumn.setCellValueFactory(new PropertyValueFactory<>("avgHumid"));
        // loudColumn.setCellValueFactory(new PropertyValueFactory<>("avgLoud"));
        //ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
    }


    public void redirectToHome(ActionEvent event) {redirect(event, "stuwi-home.fxml");}
}
