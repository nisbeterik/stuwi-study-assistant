package org.stuwiapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import org.stuwiapp.*;
import org.stuwiapp.database.LatestSettingsDAO;
import org.stuwiapp.database.StudySessionTemplateDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

public class StudySessionConfigurationController extends ParentController implements Initializable  {
    @FXML public ChoiceBox templateChoiceBox;
    @FXML public Button deleteTemplateButton;

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

    final static StudySessionTemplate RESET_TEMPLATE;
    final static StudySessionTemplate RECOMMENDED_TEMPLATE;
    private StudySessionTemplate currentTemplate;


    //init standard session templates.
    static {
        try {
            RESET_TEMPLATE = new StudySessionTemplate("RESET", "Reset", "", 0, 0, 0);
            RECOMMENDED_TEMPLATE = new StudySessionTemplate("RECOMMENDED", "Recommended Settings", "General", SCIENCE_BASED_BLOCK_DURATION, SCIENCE_BASED_BREAK_DURATION, SCIENCE_BASED_BLOCK_AMOUNT );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();
    private final String startSessionTopic = "stuwi/startsession"; // topic that WIO subscribes to

    public StudySessionConfigurationController(){

    }
    public void initialize(URL url, ResourceBundle resourceBundle){

        // Retrieves the current user's saved templates from the database
        String currentUser = UserManager.getInstance().getCurrentUser();
        ArrayList<StudySessionTemplate> savedTemplates = StudySessionTemplateDAO.getUserTemplates(currentUser);
        ObservableList<StudySessionTemplate> templatesList= FXCollections.observableArrayList(savedTemplates);

        // Adds the saved templates to the choice box
        if (templatesList.size() > 0) {
            templateChoiceBox.getItems().addAll(templatesList);
        }

        templateChoiceBox.getItems().addAll(RESET_TEMPLATE, RECOMMENDED_TEMPLATE);
        templateChoiceBox.setValue("Recommended Settings");

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


        //Loads the template to update sliders / indicators
        StudySessionTemplate latestStudyTemplate = LatestSettingsDAO.getLatestStudyTemplate(currentUser);
        if (latestStudyTemplate == null) {
            loadStudyTemplate(RECOMMENDED_TEMPLATE);
        } else {
            loadStudyTemplate(latestStudyTemplate);
        }

    }

    //gets the current values for each slider and makes a StudySessionTemplateObject from it.
    public StudySessionTemplate getSliderValues(){
        int duration = (int)durationSlider.getValue();
        int blocks = (int)blocksSlider.getValue();
        int breakDuration = (int)breakDurationSlider.getValue();
        String subject = subjectField.getText();
        String title = null;
        try{
            String templateId = UUID.randomUUID().toString();
            return new StudySessionTemplate(templateId, title, subject, duration, breakDuration, blocks);
        } catch (Exception e) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText(e.getMessage());
        }
        return null;
    }
    public void startSession(ActionEvent event){
        StudySessionTemplate sessionSettings = getSliderValues();
        if(sessionSettings == null) { return; }

        // Sets the current template to the one that is about to be started, so it can be saved with the session
        StudySessionManager.getInstance().setCurrentTemplate(sessionSettings);
        LatestSettingsDAO.saveLatestStudyTemplateInDatabase(sessionSettings, UserManager.getInstance().getCurrentUser());

        try{
            //Starts a study session with current setting
            mqttManager.publish(startSessionTopic, String.format("%d %d %d", sessionSettings.getBlocks(), sessionSettings.getDuration(), sessionSettings.getBreakDuration()));
            redirect(event, "dashboard.fxml");
        } catch (Exception e){
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("Failed to connect to WIO terminal");
        }
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
            if (curValues == null) { return null; }

            String templateId = UUID.randomUUID().toString();
            StudySessionTemplate newStudySessionTemplate = new StudySessionTemplate(templateId, title, curValues.getSubject(), curValues.getDuration(), curValues.getBreakDuration(), curValues.getBlocks());

            // Saves the recently created template to the database
            StudySessionTemplateDAO.saveTemplateInDatabase(newStudySessionTemplate, UserManager.getInstance().getCurrentUser());

            templateChoiceBox.getItems().add(newStudySessionTemplate);
            infoLabel.setStyle("-fx-text-fill: lightgreen;");
            infoLabel.setText("Successfully saved template " + title);
            return newStudySessionTemplate;

        } catch (Exception e) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public void redirectToHome(ActionEvent event){
        redirect(event, "stuwi-home.fxml");
    }

    public void deleteTemplateFromDatabase(ActionEvent event) {
        StudySessionTemplate template = (StudySessionTemplate) templateChoiceBox.getValue();
        if (template == null) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("No template selected");
            return;
        }
        if (template.getId().equals("RESET") || template.getId().equals("RECOMMENDED")) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("Cannot delete default templates");
            return;
        }
        Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDeleteAlert.setHeaderText(null);
        confirmDeleteAlert.setTitle("Confirmation");
        confirmDeleteAlert.setContentText("Are you sure you want to delete template: " + template.getTitle() + "?");

        Optional<ButtonType> result = confirmDeleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            StudySessionTemplateDAO.deleteTemplateFromDatabase(template);
            templateChoiceBox.getItems().remove(template);
            templateChoiceBox.setValue(RECOMMENDED_TEMPLATE);
            infoLabel.setStyle("-fx-text-fill: lightgreen;");
            infoLabel.setText("Template deleted");
        }
    }
}


