package org.stuwiapp;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * JavaFX App
 */
public class StuWiApp extends Application {

    private MQTTManager mqttManager;
    private String publishTopic = "stuwi/testin"; // topic that WIO subscribes to
    private String subscribeTopic = "stuwi/testout"; // topic that WIO publishes to
    // private final ScheduledExecutorService publishScheduler = Executors.newSingleThreadScheduledExecutor();
    private final String temperatureTopic = "stuwi/temp";
    private final String humidityTopic = "stuwi/humid";

    @Override
    public void init() throws MqttException {
        mqttManager = MQTTManagerSingleton.getMqttInstance();
        mqttManager.subscribe(subscribeTopic);
        mqttManager.subscribe(temperatureTopic);
        mqttManager.subscribe(humidityTopic);
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
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