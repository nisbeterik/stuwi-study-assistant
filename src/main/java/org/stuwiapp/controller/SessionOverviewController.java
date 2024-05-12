package org.stuwiapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.stuwiapp.StudySession;
import org.stuwiapp.UserManager;
import org.stuwiapp.database.StudySessionDAO;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SessionOverviewController extends ParentController implements Initializable {

    @FXML
    private Button deleteSessionButton;
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
    @FXML
    public Label deletedSessionLabel;

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
            sessionTable.setRowFactory(tv -> {
                TableRow<StudySession> row = new TableRow<>();
                Tooltip tooltip = new Tooltip();

                row.hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (row.getItem() != null && newValue) {
                        String ratingText = row.getItem().getRatingText();
                        tooltip.setText(ratingText);
                        tooltip.show(row, row.localToScreen(0, 0).getX() + row.getWidth(), row.localToScreen(0, 0).getY());
                    } else {
                        tooltip.hide();
                    }
                });
                return row;
            });
            sessionTable.getItems().addAll(sessionsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void redirectToHome(ActionEvent event) {
        redirect(event, "stuwi-home.fxml");
    }


    public void deleteSession(ActionEvent event){

        //Alert to confirm to delete a session
        Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);

        //Confirm to delete a session or not
        confirmDeleteAlert.setHeaderText(null);
        confirmDeleteAlert.setTitle("Confirmation");
        confirmDeleteAlert.setContentText("Are you sure you want to delete this session?");

        try {
            if (sessionTable.getSelectionModel().isEmpty()) {
                throw new Exception("No study session selected");
            }
            Optional<ButtonType> result = confirmDeleteAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                //Delete from table view and database
                StudySession selectedSession = sessionTable.getSelectionModel().getSelectedItem();
                sessionTable.getItems().remove(selectedSession);
                StudySessionDAO.deleteSessionFromDatabase(selectedSession);

                //Confirmation label for when user deletes a session
                deletedSessionLabel.setStyle("-fx-text-fill: green;");
                deletedSessionLabel.setText("Session deleted");

                System.out.println("User deleted session");
            } else {
                System.out.println("User cancelled operation");
            }
        }catch (Exception e) {
            // Show error message if no session is selected
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle("Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }
}