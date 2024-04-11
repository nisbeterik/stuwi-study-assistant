package org.stuwiapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JavaFX App
 */
public class StuWiApp extends Application {

    private MQTTManager mqttManager;
    private String publishTopic = "stuwi/testin"; // topic that WIO subscribes to
    private String subscribeTopic = "stuwi/testout"; // topic that WIO publishes to
    private final ScheduledExecutorService publishScheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void init() throws MqttException {
        mqttManager = new MQTTManager();
        mqttManager.subscribe(subscribeTopic);
    }
    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();

        // publishes message every 10 seconds to publish topic
        publishScheduler.scheduleAtFixedRate(() -> {
            try {
                mqttManager.publish(publishTopic, "Message from StuWi app");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        launch();
    }

}