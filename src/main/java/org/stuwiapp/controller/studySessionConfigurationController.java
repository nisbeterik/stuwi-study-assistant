package org.stuwiapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import org.stuwiapp.StudySessionTemplate;

import java.net.URL;
import java.util.ResourceBundle;

public class studySessionConfigurationController extends ParentController implements Initializable  {

    public Button saveTemplateButton;
    public Label titleLabel;
    public MenuButton templateDropdown;
    public TextField subjectField;
    public Label subjectLabel;
    public Label sessionDurationLabel;
    public Slider durationSlider;
    public Slider breakDurationSlider;
    public Label infoLabel;
    public Label breakIntervalLabel;
    public Slider breakIntervalSlider;
    public Label breakDurationLabel;
    public Button startSessionButton;
    public Label sessionDurationIndicator;
    public Label breakDurationIndicator;
    public Label breakIntervalIndicator;

    public void initialize(URL url, ResourceBundle resourceBundle){
        durationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sessionDurationIndicator.setText((int)durationSlider.getValue() + " m");
            }
        });
        breakDurationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                breakDurationIndicator.setText((int)breakDurationSlider.getValue() + " m");
            }
        });
        breakIntervalSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                breakIntervalIndicator.setText((int)breakIntervalSlider.getValue() + " m");
            }
        });
    }

    public StudySessionTemplate saveSettingsAsTemplate() {
        int duration = (int)durationSlider.getValue();
        int breakInterval = (int)breakIntervalSlider.getValue();
        int breakDuration = (int)breakDurationSlider.getValue();
        String subject = subjectField.getText();

        try {
            StudySessionTemplate studySessionTemplate = new StudySessionTemplate(subject, duration, breakInterval, breakDuration);
            infoLabel.setText("Successfully saved template " + subject);
            return studySessionTemplate;

        } catch (Exception e) {
            infoLabel.setText(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}


