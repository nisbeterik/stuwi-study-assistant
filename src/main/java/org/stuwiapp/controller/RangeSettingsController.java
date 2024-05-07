package org.stuwiapp.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import org.controlsfx.control.RangeSlider;
import javafx.scene.layout.HBox;

public class RangeSettingsController implements Initializable {

    public Slider loudSlider;
    public RangeSlider humiSlider;
    public RangeSlider tempSlider;

    public void initialize(URL url, ResourceBundle resourceBundle){

        tempSlider.setMax();

        tempSlider.skinProperty().addListener(new ChangeListener<Skin<?>>() {
            @Override
            public void changed(ObservableValue<? extends Skin<?>> observableValue, Skin<?> skin, Skin<?> t1) {
                tempSlider.getHighValue()

            }
        });
    }

}
