package org.stuwiapp.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.stuwiapp.service.StudySessionAnalyticsService;

public class StudySessionGraphController extends ParentController {

    public LineChart durationChart;
    public Label hoursAndMinutesLabel;
    public Label averageRatingNumberLabel;
    public Button returnHomeButton;

    StudySessionAnalyticsService service = new StudySessionAnalyticsService();
    @FXML
    public void initialize() {
        hoursAndMinutesLabel.setText(service.totalStudiedInHoursAndMinutes());
        averageRatingNumberLabel.setText(service.averageRating());


    }

    public void returnHome(MouseEvent mouseEvent) {
        redirect(mouseEvent, "stuwi-home.fxml");
    }
}
