package org.stuwiapp.controller;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.stuwiapp.MQTTManager;
import org.stuwiapp.MQTTManagerSingleton;

public class StudySessionController extends ParentController {
    public Button backToDashboard;
    public Button startSessionButton;
    public Button stopSessionButton;

    private String startSessionTopic = "stuwi/startsession"; // topic that WIO subscribes to
    private String stopSessionTopic = "stuwi/endsession";
    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();

    public void redirectDashboard(MouseEvent mouseEvent) {
        redirect(mouseEvent, "dashboard.fxml");
    }

    public void publishStartSession(MouseEvent event) {
        try {
            mqttManager.publish(startSessionTopic, "Start Session");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void publishStopSession(MouseEvent event) {
        try {
            mqttManager.publish(stopSessionTopic, "Stop session");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
