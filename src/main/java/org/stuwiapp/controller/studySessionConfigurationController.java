package org.stuwiapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.fxml.Initializable;

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
    public Label infoLabelBreaks;
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

    public void saveSettingsAsTemplate() {

    }
}


