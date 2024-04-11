package org.stuwiapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    // private final ScheduledExecutorService publishScheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void init() throws MqttException {
        mqttManager = MQTTManagerSingleton.getMqttInstance();
        mqttManager.subscribe(subscribeTopic);
    }
    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Button publishButton = new Button("Publish message");
        var scene = new Scene(new VBox(label, publishButton), 640, 480);
        stage.setScene(scene);
        stage.show();

        // sets button to publish message when clicked
        publishButton.setOnAction(event -> {
            try {
                mqttManager.publish(publishTopic, "Message from StuWi app");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });

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