package org.stuwiapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.stuwiapp.service.StudySessionAnalyticsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudySessionGraphController extends ParentController {

    public AnchorPane background;
    public StackedBarChart<String, Number> barChart;
    public CategoryAxis X;
    public NumberAxis Y;
    @FXML
    private Label hoursAndMinutesLabel;

    @FXML
    private Label averageRatingNumberLabel;

    @FXML
    private Button returnHomeButton;

    StudySessionAnalyticsService service = new StudySessionAnalyticsService();
    @FXML
    public void initialize() {
        background.getStyleClass().add("pane"); //sets the style class for the background AnchorPane to "pane".
        X = (CategoryAxis) barChart.getXAxis();
        List<Integer> studyTimeData = service.calculateTotalStudyTimePerDay();
        configureCategoryAxis();
        displayStudyTimeData(studyTimeData);


        hoursAndMinutesLabel.setText(service.totalStudiedInHoursAndMinutes());
        averageRatingNumberLabel.setText(service.averageRating());




    }

    private void displayStudyTimeData(List<Integer> studyTimeData) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Duration");


        for (int day = 1; day <= studyTimeData.size(); day++) {
            int studiedDuration = studyTimeData.get(day - 1);
            series.getData().add(new XYChart.Data<>(String.valueOf(day), studiedDuration));
        }


        barChart.getData().clear();
        barChart.getData().add(series);
        for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: #33C1FF;");
        }
    }

    public void returnHome(MouseEvent mouseEvent) {
        redirect(mouseEvent, "stuwi-home.fxml");
    }

    private void configureCategoryAxis() {
        List<String> dayLabels = generateDayLabels(1, 30);
        X.setCategories(FXCollections.observableArrayList(dayLabels));
    }

    private List<String> generateDayLabels(int startDay, int endDay) {
        List<String> labels = new ArrayList<>();
        for (int i = startDay; i <= endDay; i++) {
            labels.add(String.valueOf(i));
        }
        return labels;
    }
}
