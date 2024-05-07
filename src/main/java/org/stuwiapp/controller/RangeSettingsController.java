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
import org.stuwiapp.RangeSettingsTemplate;
import org.stuwiapp.StudySessionTemplate;
import org.stuwiapp.UserManager;
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
    public Label HighTempLabel11;

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
    }
    public StudySessionTemplate saveSettingsAsTemplate(ActionEvent event){
        /*TextInputDialog nameInputDialog = new TextInputDialog();
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

            StudySessionTemplate newStudySessionTemplate = new StudySessionTemplate(title, curValues.getSubject(), curValues.getDuration(), curValues.getBreakDuration(), curValues.getBlocks());

            // Saves the recently created template to the database
            StudySessionTemplateDAO.saveTemplateInDatabase(newStudySessionTemplate, UserManager.getInstance().getCurrentUser());

            templateChoiceBox.getItems().add(newStudySessionTemplate);
            infoLabel.setStyle("-fx-text-fill: green;");
            infoLabel.setText("Successfully saved template " + title);
            return newStudySessionTemplate;

        } catch (Exception e) {
            infoLabel.setStyle("-fx-text-fill: red;");
            infoLabel.setText(e.getMessage());
            e.printStackTrace();
        }*/
        return null;
    }

}
