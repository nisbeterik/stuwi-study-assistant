package org.stuwiapp.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.stuwiapp.service.StudySessionAnalyticsService;

import java.util.Collections;
import java.util.List;

public class StudySessionGraphController extends ParentController {


    public StackedBarChart<String, Integer> barChart;
    public CategoryAxis categoryAxis;
    public NumberAxis numberAxis;
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
        Collections.reverse(studyTimeData);
        System.out.println(studyTimeData);
        displayStudyTimeData(studyTimeData);

        hoursAndMinutesLabel.setText(service.totalStudiedInHoursAndMinutes());
        averageRatingNumberLabel.setText(service.averageRating());


    }

    private void displayStudyTimeData(List<Integer> studyTimeData) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (int day = 1; day <= studyTimeData.size(); day++) {
            int studiedDuration = studyTimeData.get(day - 1);


            if (studiedDuration >= 0) {
                System.out.println(series.getData().add(new XYChart.Data<>(String.valueOf(day), studiedDuration)));
            }
        }


        barChart.getData().clear();
        barChart.getData().add(series);
    }

    public void returnHome(MouseEvent mouseEvent) {
        redirect(mouseEvent, "stuwi-home.fxml");
    }
}
