package org.stuwiapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.scene.control.*;
import org.controlsfx.control.RangeSlider;
import javafx.scene.layout.HBox;
import org.stuwiapp.*;
import org.stuwiapp.database.LatestSettingsDAO;
import org.stuwiapp.database.RangeSettingsTemplateDAO;
import org.stuwiapp.database.StudySessionTemplateDAO;

public class RangeSettingsController extends ParentController implements Initializable {

    public Slider loudSlider;
    public RangeSlider humidSlider;
    public RangeSlider tempSlider;
    public ChoiceBox templateChoiceBox;
    public Button saveTemplateButton;

    final static RangeSettingsTemplate RECOMMENDED_TEMPLATE = new RangeSettingsTemplate("RECOMMENDED","Recommended Settings", 23, 21, 40,30, 70);
    public Label humidHighLabel;
    public Label humidLowLabel;
    public Label tempLowLabel;
    public Label tempHighLabel;
    public Label loudHighLabel;
    public Label infoLabel;
    public Button loadSettings;
    public Button backButton;
    public Button deleteSettingsButton;

    public void initialize(URL url, ResourceBundle resourceBundle){
        // Retrieves the current user's saved templates from the database
        String currentUser = UserManager.getInstance().getCurrentUser();
        ArrayList<RangeSettingsTemplate> savedTemplates = RangeSettingsTemplateDAO.getUserRangeTemplates(currentUser);
        ObservableList<RangeSettingsTemplate> templatesList= FXCollections.observableArrayList(savedTemplates);

        // Adds the saved templates to the choice box
        if (templatesList.size() > 0) {
            templateChoiceBox.getItems().addAll(templatesList);
        }

        templateChoiceBox.getItems().add(RECOMMENDED_TEMPLATE);
        templateChoiceBox.setValue("Recommended Settings");

        templateChoiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                loadRangeTemplate((RangeSettingsTemplate) templateChoiceBox.getValue());
            }
        });

        tempSlider.highValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                tempHighLabel.setText((int)tempSlider.getHighValue() + "");
            }
        });

        tempSlider.lowValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                tempLowLabel.setText((int)tempSlider.getLowValue() + "");
            }
        });

        humidSlider.highValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                humidHighLabel.setText((int)humidSlider.getHighValue() + "");
            }
        });
        humidSlider.lowValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                humidLowLabel.setText((int)humidSlider.getLowValue() + "");
            }
        });
        loudSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                loudHighLabel.setText((int)loudSlider.getValue()+ "");
            }
        });
        RangeSettingsTemplate latestRangeTemplate =  LatestSettingsDAO.getLatestRangeTemplate(currentUser);
        if (latestRangeTemplate == null) {
            loadRangeTemplate(RECOMMENDED_TEMPLATE);
        } else {
            loadRangeTemplate(latestRangeTemplate);
        }
    }
    public boolean loadRangeTemplate(RangeSettingsTemplate template){
        if (template == null) {return false;}
        tempSlider.setHighValue((template.getTempMax()));
        tempSlider.setLowValue((template.getTempMin()));
        humidSlider.setHighValue((template.getHumidMax()));
        humidSlider.setLowValue((template.getHumidMin()));
        loudSlider.setValue(template.getLoudMax());

        return true;
    }

    public RangeSettingsTemplate getSliderValues(){
        int tempMax = (int)tempSlider.getHighValue();
        int tempMin = (int)tempSlider.getLowValue();
        int humidMax = (int)humidSlider.getHighValue();
        int humidMin = (int)humidSlider.getLowValue();
        int loudMax = (int)loudSlider.getValue();

        String settingsId = UUID.randomUUID().toString();
        return new RangeSettingsTemplate(settingsId,"temporary", tempMax, tempMin, humidMax, humidMin, loudMax);
    }
    public RangeSettingsTemplate saveSettingsAsTemplate(ActionEvent event){
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


        RangeSettingsTemplate curValues = getSliderValues();
        if (curValues == null) { return null; }

        String settingsId = UUID.randomUUID().toString();
        RangeSettingsTemplate newRangeSettingsTemplate = new RangeSettingsTemplate(settingsId, title, curValues.getTempMax(), curValues.getTempMin(), curValues.getHumidMax(), curValues.getHumidMin(), curValues.getLoudMax());

        // Saves the recently created template to the database
        RangeSettingsTemplateDAO.saveRangeTemplateInDatabase(newRangeSettingsTemplate, UserManager.getInstance().getCurrentUser());

        templateChoiceBox.getItems().add(newRangeSettingsTemplate);
        infoLabel.setStyle("-fx-text-fill: lightgreen;");
        infoLabel.setText("Successfully saved template " + title);
        return newRangeSettingsTemplate;
    }

    public void publishRangeSettings(ActionEvent event){
        RangeSettingsTemplate rangeSettings = getSliderValues();
        LatestSettingsDAO.saveLatestRangeSettings(rangeSettings, UserManager.getInstance().getCurrentUser());

        try{
            rangeSettings.publishRangeSettings();
            infoLabel.setStyle("-fx-text-fill: lightgreen;");
            infoLabel.setText("Successfully loaded settings to terminal!");
        } catch (Exception e){
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("Failed to connect to WIO terminal");
        }
    }

    public void redirectBack(ActionEvent event){
        redirect(event, "stuwi-home.fxml");
    }

    public void deleteSettingsFromDatabase(ActionEvent event) {
        RangeSettingsTemplate settings = (RangeSettingsTemplate) templateChoiceBox.getSelectionModel().getSelectedItem();
        if (settings == null) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("No settings template selected");
            return;
        }
        if (settings.getId().equals("RECOMMENDED")) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("Cannot delete default settings");
            return;
        }
        Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDeleteAlert.setHeaderText(null);
        confirmDeleteAlert.setTitle("Confirmation");
        confirmDeleteAlert.setContentText("Are you sure you want to delete settings template: " + settings.getTitle() + "?");

        Optional<ButtonType> result = confirmDeleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            RangeSettingsTemplateDAO.deleteRangeSettingsFromDatabase(settings);
            templateChoiceBox.getItems().remove(settings);
            templateChoiceBox.setValue(RECOMMENDED_TEMPLATE);
            infoLabel.setStyle("-fx-text-fill: lightgreen;");
            infoLabel.setText("Template deleted");
        }
    }
}
