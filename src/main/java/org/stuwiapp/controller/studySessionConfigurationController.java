package org.stuwiapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import org.stuwiapp.StudySessionTemplate;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class studySessionConfigurationController extends ParentController implements Initializable  {


    @FXML public ChoiceBox templateChoiceBox;
    @FXML Button saveTemplateButton;
    @FXML public Label titleLabel;
    @FXML public TextField subjectField;
    @FXML public Label subjectLabel;
    @FXML public Label sessionDurationLabel;
    @FXML public Slider durationSlider;
    @FXML public Slider breakDurationSlider;
    @FXML public Label infoLabel;
    @FXML public Label blocksLabel;
    @FXML public Slider blocksSlider;
    @FXML public Label breakDurationLabel;
    @FXML public Button startSessionButton;
    @FXML public Label sessionDurationIndicator;
    @FXML public Label breakDurationIndicator;
    @FXML public Label blocksIndicator;

    private String title;

    public studySessionConfigurationController(){

    }
    public void initialize(URL url, ResourceBundle resourceBundle){

        //TODO: for each saved template add as item to the templateChoiceBox.

        try {
            templateChoiceBox.getItems().add(new StudySessionTemplate("Reset", "", 0, 0, 0 ));
            templateChoiceBox.getItems().add(new StudySessionTemplate("Recommended Settings", "General", 25, 5, 4 ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        templateChoiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loadStudyTemplate((StudySessionTemplate)templateChoiceBox.getValue());
            }
        });
        durationSlider.valueProperty().addListener(new ChangeListener<Number>() { //Slider listener tu update indicator
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sessionDurationIndicator.setText((int)durationSlider.getValue() + " min");
            }
        });
        breakDurationSlider.valueProperty().addListener(new ChangeListener<Number>() {//Slider listener tu update indicator
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                breakDurationIndicator.setText((int)breakDurationSlider.getValue() + " min");
            }
        });
        blocksSlider.valueProperty().addListener(new ChangeListener<Number>() {//Slider listener tu update indicator
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                blocksIndicator.setText((int)blocksSlider.getValue() + " st");
            }
        });
    }
    public boolean loadStudyTemplate(StudySessionTemplate template){
        if (template == null) {return false;}
        durationSlider.setValue(template.getDuration());
        blocksSlider.setValue(template.getBlocks());
        breakDurationSlider.setValue(template.getBreakDuration());
        subjectField.setText(template.getSubject());

        return true;
    }

    public StudySessionTemplate saveSettingsAsTemplate(ActionEvent event){
        TextInputDialog nameInputDialog = new TextInputDialog();
        nameInputDialog.setTitle("Save template as: ");
        nameInputDialog.setHeaderText(null);
        nameInputDialog.setContentText("Please name the template");

        Optional<String> nameResult = nameInputDialog.showAndWait();

        String title = null;

        if (nameResult.isPresent()){
            title = nameResult.get();
        } else {
            infoLabel.setText("You need to enter a name! ");
            return null;
        }

        int duration = (int)durationSlider.getValue();
        int blocks = (int)blocksSlider.getValue();
        int breakDuration = (int)breakDurationSlider.getValue();
        String subject = subjectField.getText();

        try {
            StudySessionTemplate newStudySessionTemplate = new StudySessionTemplate(title, subject, duration, breakDuration, blocks);
            templateChoiceBox.getItems().add(newStudySessionTemplate);
            infoLabel.setText("Successfully saved template " + subject);
            return newStudySessionTemplate;

        } catch (Exception e) {
            infoLabel.setText(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}


