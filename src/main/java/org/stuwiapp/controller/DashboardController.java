package org.stuwiapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.stuwiapp.MQTTManager;
import org.stuwiapp.MQTTManagerSingleton;


public class DashboardController {

    @FXML
    private Button publishMsgButton;
    @FXML
    private Label tempReadingLabel;
    @FXML
    private Label humiReadingLabel;
    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();
    private String publishTopic = "stuwi/testin"; // topic that WIO subscribes to

    public void readAndUpdateTemperature(){

    }

    public void readAndUpdateHumidity(){

    }

    public void publishMsg(ActionEvent event) {
        try {
            mqttManager.publish(publishTopic, "Message from StuWi app");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
