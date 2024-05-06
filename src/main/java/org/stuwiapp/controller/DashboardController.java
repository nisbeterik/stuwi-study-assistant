package org.stuwiapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.stuwiapp.MQTTManager;
import org.stuwiapp.MQTTManagerSingleton;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class DashboardController extends ParentController {

    public ImageView loudImage;
    public Button studySessionRedirect;
    public Label studyStatusLabel;
    public Button stopSessionButton;
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
    private Label tempReadingLabel;
    @FXML
    private Label humiReadingLabel;
    @FXML
    private Label loudnessReadingLabel;

    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();

    private double currentTemp;
    private double currentHumid;
    private double currentLoudness;
    private boolean isSessionOngoing;



    // Thresholds should not be here, change this later
    private final double humidityFloor = 40;
    private final double humidityRoof = 60;
    private final double temperatureFloor = 21;
    private final double temperatureRoof = 23;

    private final double loudnessFloor = 0;
    private final double loudnessRoof = 1000;

    private String stopSessionTopic = "stuwi/endsession";


    @FXML
    public void initialize() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                readAndUpdateTemperature();
                readAndUpdateHumidity();
                readAndUpdateLoudness();
                readAndUpdateStudyStatus();
            }
        }, 0, 1000);
    }



    public void readAndUpdateTemperature(){
        Platform.runLater(() -> {
            currentTemp = Double.parseDouble(mqttManager.getLatestTemp().trim());
            tempReadingLabel.setText(String.valueOf(currentTemp) + " C");

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
            humiReadingLabel.setText(String.valueOf(currentHumid) + " %");

            if (currentHumid >= humidityFloor && currentHumid <= humidityRoof){
                humiStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/happy-regular-240.png")));
            } else {
                humiStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/sad-regular-240.png")));
            }
        });
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

    private void readAndUpdateStudyStatus() {
        Platform.runLater(() ->  {
            isSessionOngoing = mqttManager.getStudySessionStatus();
            if(isSessionOngoing) {
                studyStatusLabel.setText("Study Session Ongoing");
            } else {

                if (check_break()){

                }
                studyStatusLabel.setText("Not Studying");
            }

        });
    }
    public void stopSession(ActionEvent event) {
        try {
            mqttManager.publish(stopSessionTopic, "Stop Session");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        redirect(event, "stuwi-home.fxml" );
    }

    public void redirectStudySession(MouseEvent mouseEvent) {
        redirect(mouseEvent, "study-session.fxml");
    }


}
