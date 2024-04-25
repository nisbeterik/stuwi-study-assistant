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
    private StudySessionManager studySessionManager;
    private String publishTopic = "stuwi/testin"; // topic that WIO subscribes to
    private String subscribeTopic = "stuwi/testout"; // topic that WIO publishes to
    // private final ScheduledExecutorService publishScheduler = Executors.newSingleThreadScheduledExecutor();
    private final String temperatureTopic = "stuwi/temp";
    private final String humidityTopic = "stuwi/humid";
    private final String loudnessTopic = "stuwi/loudness";
    private final String sessionOverTopic = "stuwi/sessionover";

    @Override
    public void init() throws MqttException {
        studySessionManager = StudySessionManager.getInstance();
        mqttManager = MQTTManagerSingleton.getMqttInstance();
        mqttManager.subscribe(subscribeTopic);
        mqttManager.subscribe(temperatureTopic);
        mqttManager.subscribe(humidityTopic);
        mqttManager.subscribe(loudnessTopic);
        mqttManager.subscribe(sessionOverTopic);
    }
    @Override
    public void start(Stage stage) throws IOException {
        Object dashboard = FXMLUtil.loadFxml("dashboard.fxml");
        Scene scene = new Scene((Parent) dashboard);
        stage.setScene(scene);
        stage.show();


        /*publishScheduler.scheduleAtFixedRate(() -> {
            try {
                mqttManager.publish(publishTopic, "Message from StuWi app");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);*/
    }

    // stop app and disconnects from mqtt
    @Override
    public void stop() throws Exception {
        if (mqttManager != null) {
            mqttManager.close();
        }
        // publishScheduler.shutdown();
        super.stop();
    }


    public static void main(String[] args) {
        launch();
    }

}