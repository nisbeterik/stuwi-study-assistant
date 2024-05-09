package org.stuwiapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SessionOverviewController extends ParentController implements Initializable {

    @FXML
    private Button returnButton;
    @FXML
    private TableView<StudySession> sessionTable;
    @FXML
    private TableColumn<StudySession, LocalDateTime> startDateColumn;
    @FXML
    private TableColumn<StudySession, Integer> durationColumn;
    @FXML
    private TableColumn<StudySession, String> tempColumn;
    @FXML
    private TableColumn<StudySession, String> humidColumn;
    @FXML
    private TableColumn<StudySession, String> loudColumn;
    @FXML
    private TableColumn<StudySession, String> ratingColumn;
    @FXML
    public TableColumn<StudySession, String> subjectColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String currentUser = UserManager.getInstance().getCurrentUser();
            ArrayList<StudySession> sessions = StudySessionDAO.getUserSessions(currentUser);
            ObservableList<StudySession> sessionsList = FXCollections.observableArrayList(sessions);
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStartDate"));
            durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            tempColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            humidColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            loudColumn.setCellValueFactory(new PropertyValueFactory<>("loudness"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            subjectColumn.setCellValueFactory(new PropertyValueFactory<>("templateSubject"));
            sessionTable.getItems().addAll(sessionsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void redirectToHome(ActionEvent event) {
        redirect(event, "stuwi-home.fxml");
    }


    public void deleteSession(ActionEvent event){

    }

}