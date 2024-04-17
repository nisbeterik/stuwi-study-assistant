package org.stuwiapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.stuwiapp.MQTTManager;
import org.stuwiapp.MQTTManagerSingleton;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class DashboardController implements Initializable {

    public ImageView loudImage;
    @FXML
    private ImageView tempImage;
    @FXML
    private ImageView humiImage;
    @FXML
    private ImageView tempStatusImage;
    @FXML
    private ImageView humiStatusImage;
    @FXML
    private ImageView loudStatusImage;
    @FXML
    private Button publishMsgButton;
    @FXML
    private Label tempReadingLabel;
    @FXML
    private Label humiReadingLabel;
    @FXML
    private Label loudnessReadingLabel;

    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();
    private String publishTopic = "stuwi/startsession"; // topic that WIO subscribes to
    private double currentTemp;
    private double currentHumid;
    private double currentLoudness;



    // Thresholds should not be here, change this later
    private final double humidityFloor = 40;
    private final double humidityRoof = 60;
    private final double temperatureFloor = 21;
    private final double temperatureRoof = 23;

    private final double loudnessFloor = 0;
    private final double loudnessRoof = 1000;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                readAndUpdateTemperature();
                readAndUpdateHumidity();
                readAndUpdateLoudness();
            }
        }, 0, 1000);
    }

    public void readAndUpdateTemperature(){
        Platform.runLater(() -> {
            currentTemp = Double.parseDouble(mqttManager.getLatestTemp().trim());
            tempReadingLabel.setText(String.valueOf(currentTemp));

            if (currentTemp >= temperatureFloor && currentTemp <= temperatureRoof){
                tempStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/happy-regular-240.png")));
            } else {
                tempStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/sad-regular-240.png")));
            }
        });
    }

    public void readAndUpdateHumidity(){
        Platform.runLater(() -> {
            currentHumid = Double.parseDouble(mqttManager.getLatestHumidity().trim());
            humiReadingLabel.setText(String.valueOf(currentHumid));

            if (currentHumid >= humidityFloor && currentHumid <= humidityRoof){
                humiStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/happy-regular-240.png")));
            } else {
                humiStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/sad-regular-240.png")));
            }
        });
    }

    public void publishMsg(ActionEvent event) {
        try {
            mqttManager.publish(publishTopic, "Start Session");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void readAndUpdateLoudness(){
        Platform.runLater(() -> {
            currentLoudness = Double.parseDouble(mqttManager.getLatestSound().trim());
            loudnessReadingLabel.setText(String.valueOf(currentLoudness));

            if (currentLoudness >= loudnessFloor && currentLoudness <= loudnessRoof) {
                loudStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/happy-regular-240.png")));
            } else {
                loudStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/sad-regular-240.png")));
            }
        });
    }

}
