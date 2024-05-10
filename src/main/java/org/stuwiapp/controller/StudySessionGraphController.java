package org.stuwiapp.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.stuwiapp.service.StudySessionAnalyticsService;

import java.util.List;

public class StudySessionGraphController extends ParentController {

    @FXML
    private LineChart<Number, Number> durationChart;

    @FXML
    private Label hoursAndMinutesLabel;

    @FXML
    private Label averageRatingNumberLabel;

    @FXML
    private Button returnHomeButton;

    StudySessionAnalyticsService service = new StudySessionAnalyticsService();
    @FXML
    public void initialize() {

        List<Integer> studyTimeData = service.calculateTotalStudyTimePerDay();
        displayStudyTimeData(studyTimeData);

        hoursAndMinutesLabel.setText(service.totalStudiedInHoursAndMinutes());
        averageRatingNumberLabel.setText(service.averageRating());


    }

    private void displayStudyTimeData(List<Integer> studyTimeData) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for (int day = 1; day <= studyTimeData.size(); day++) {
            int studiedDuration = studyTimeData.get(day - 1);

            if (studiedDuration > 0) {
                series.getData().add(new XYChart.Data<>(day, studiedDuration));
            }
        }

        durationChart.getXAxis().setLabel("Day of Month");
        durationChart.getYAxis().setLabel("Studied Duration (minutes)");

        durationChart.getData().clear();
        durationChart.getData().add(series);
    }

    public void returnHome(MouseEvent mouseEvent) {
        redirect(mouseEvent, "stuwi-home.fxml");
    }
}
