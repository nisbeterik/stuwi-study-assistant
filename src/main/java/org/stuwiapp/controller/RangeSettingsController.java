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

import javafx.scene.control.*;
import org.controlsfx.control.RangeSlider;
import javafx.scene.layout.HBox;
import org.stuwiapp.*;
import org.stuwiapp.database.RangeSettingsTemplateDAO;
import org.stuwiapp.database.StudySessionTemplateDAO;

public class RangeSettingsController implements Initializable {

    public Slider loudSlider;
    public RangeSlider humidSlider;
    public RangeSlider tempSlider;
    public ChoiceBox templateChoiceBox;
    public Button saveTemplateButton;

    final static RangeSettingsTemplate RECOMMENDED_TEMPLATE = new RangeSettingsTemplate("Recommended Settings", 23, 21, 40,30, 70);
    public Label humidHighLabel;
    public Label humidLowLabel;
    public Label tempLowLabel;
    public Label tempHighLabel;
    public Label loudHighLabel;
    public Label infoLabel;
    public Button loadSettings;

    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();
    private final String publicRangeDataTopic = "stuwi/rangeupdate";

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
        loadRangeTemplate(RECOMMENDED_TEMPLATE);
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

        return new RangeSettingsTemplate("temporary", tempMax, tempMin, humidMax, humidMin, loudMax);
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

        RangeSettingsTemplate newRangeSettingsTempalte = new RangeSettingsTemplate(title, curValues.getTempMax(), curValues.getTempMin(), curValues.getHumidMax(), curValues.getHumidMin(), curValues.getLoudMax());

        // Saves the recently created template to the database
        RangeSettingsTemplateDAO.saveRangeTemplateInDatabase(newRangeSettingsTempalte, UserManager.getInstance().getCurrentUser());

        templateChoiceBox.getItems().add(newRangeSettingsTempalte);
        infoLabel.setStyle("-fx-text-fill: green;");
        infoLabel.setText("Successfully saved template " + title);
        return newRangeSettingsTempalte;
    }

    public void publishRangeSettings(ActionEvent event){
        RangeSettingsTemplate rangeSettings = getSliderValues();
        try{
            //Starts a study session with current setting
            mqttManager.publish(publicRangeDataTopic, String.format("%d %d %d %d %d", rangeSettings.getTempMax(), rangeSettings.getTempMin(), rangeSettings.getHumidMax(), rangeSettings.getHumidMin(), rangeSettings.getLoudMax()));
            infoLabel.setStyle("-fx-text-fill: green;");
            infoLabel.setText("Successfully loaded settings to terminal!");
        } catch (Exception e){
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText("Failed to connect to WIO terminal");
        }

    }

}
