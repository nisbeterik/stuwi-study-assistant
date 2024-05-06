package org.stuwiapp;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;
import javafx.fxml.FXMLLoader;
import org.stuwiapp.Utils.FXMLUtil;

import java.io.IOException;

/**
 * JavaFX App
 */
public class StuWiApp extends Application {

    private MQTTManager mqttManager;

    private final String temperatureTopic = "stuwi/temp";
    private final String humidityTopic = "stuwi/humid";

    private final String loudnessTopic = "stuwi/loudness";

    private final String sessionOverTopic = "stuwi/sessionover";

    private final String activebreakTopic = "stuwi/breakactive";
    private final String inactivebreakTopic = "stuwi/breakinactive";

    @Override
    public void init() throws MqttException {
        mqttManager = MQTTManagerSingleton.getMqttInstance();
        mqttManager.subscribe(temperatureTopic);
        mqttManager.subscribe(humidityTopic);
        mqttManager.subscribe(loudnessTopic);
        mqttManager.subscribe(sessionOverTopic);
        mqttManager.subscribe(activebreakTopic);
        mqttManager.subscribe(inactivebreakTopic);
    }
    @Override
    public void start(Stage stage) throws IOException {
        Object home = FXMLUtil.loadFxml("stuwi-home.fxml");
        Scene scene = new Scene((Parent) home);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            try {
                onCloseCallback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.show();
    }

    // stop app and disconnects from mqtt

    public void onCloseCallback() throws Exception {
        if (mqttManager != null) {
            mqttManager.close();
        }
        System.exit(0);
    }


    public static void main(String[] args) {
        launch();
    }

}