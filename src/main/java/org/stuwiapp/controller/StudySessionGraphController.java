package org.stuwiapp.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import org.stuwiapp.service.StudySessionAnalyticsService;

public class StudySessionGraphController {

    public LineChart durationChart;

    StudySessionAnalyticsService service = new StudySessionAnalyticsService();
    @FXML
    public void initialize() {




    }
}
