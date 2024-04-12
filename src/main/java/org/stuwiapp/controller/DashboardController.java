package org.stuwiapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.stuwiapp.MQTTManager;
import org.stuwiapp.MQTTManagerSingleton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class DashboardController implements Initializable {

    @FXML
    private Button publishMsgButton;
    @FXML
    private Label tempReadingLabel;
    @FXML
    private Label humiReadingLabel;
    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();
    private String publishTopic = "stuwi/testin"; // topic that WIO subscribes to

    private Timer timer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                readAndUpdateTemperature();
                readAndUpdateHumidity();
            }
        }, 0, 1000);
    }

    public void readAndUpdateTemperature(){
        Platform.runLater(() -> {
            tempReadingLabel.setText(mqttManager.getLatestTemp());
        });
    }

    public void readAndUpdateHumidity(){
        Platform.runLater(() -> {
            humiReadingLabel.setText(mqttManager.getLatestHumidity());
        });
    }

    public void publishMsg(ActionEvent event) {
        try {
            mqttManager.publish(publishTopic, "Message from StuWi app");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }


}
