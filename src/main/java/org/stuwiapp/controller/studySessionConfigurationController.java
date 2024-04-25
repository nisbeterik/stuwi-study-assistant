package org.stuwiapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;
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

    final static int SCIENCE_BASED_BLOCK_DURATION = 25;
    final static int SCIENCE_BASED_BREAK_DURATION = 5;
    final static int SCIENCE_BASED_BLOCK_AMOUNT = 4;

    public studySessionConfigurationController(){

    }
    public void initialize(URL url, ResourceBundle resourceBundle){

        //TODO: for each saved template add as item to the templateChoiceBox.

        try {
            templateChoiceBox.getItems().add(new StudySessionTemplate("Reset", "", 0, 0, 0 ));
            templateChoiceBox.getItems().add(new StudySessionTemplate("Recommended Settings", "General", SCIENCE_BASED_BLOCK_DURATION, SCIENCE_BASED_BREAK_DURATION, SCIENCE_BASED_BLOCK_AMOUNT ));
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
    public StudySessionTemplate getSliderValues(){
        int duration = (int)durationSlider.getValue();
        int blocks = (int)blocksSlider.getValue();
        int breakDuration = (int)breakDurationSlider.getValue();
        String subject = subjectField.getText();
        String title = null;
        try{
            return new StudySessionTemplate(title, subject, duration, breakDuration, blocks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void startSession(ActionEvent event){
        getSliderValues();
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

        if (nameResult.isPresent() && !nameResult.get().isEmpty()){
            title = nameResult.get();
        } else {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("You need to enter a name! ");
            return null;
        }

        try {
            StudySessionTemplate curValues = getSliderValues();
            StudySessionTemplate newStudySessionTemplate = new StudySessionTemplate(title, curValues.getSubject(), curValues.getDuration(), curValues.getBreakDuration(), curValues.getBlocks());
            templateChoiceBox.getItems().add(newStudySessionTemplate);
            infoLabel.setStyle("-fx-text-fill: green;");
            infoLabel.setText("Successfully saved template " + title);
            return newStudySessionTemplate;

        } catch (Exception e) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText(e.getMessage());
            e.printStackTrace();
        }
        return null;


    }
}


