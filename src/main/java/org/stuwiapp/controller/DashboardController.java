package org.stuwiapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.stuwiapp.MQTTManager;
import org.stuwiapp.MQTTManagerSingleton;
import org.stuwiapp.StudySessionManager;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javafx.util.Pair;


public class DashboardController extends ParentController {

    public ImageView loudImage;
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
    private boolean isBreakActive = false;


    // Thresholds should not be here, change this later
    private static int humidityFloor = 40;
    private static int humidityRoof = 60;
    private static int temperatureFloor = 21;
    private static int temperatureRoof = 23;
    private static int loudnessRoof = 1000;

    private String stopSessionTopic = "stuwi/endsession";


    @FXML
    public void initialize() {
        // initListener();
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
                tempStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/greenCircle.png")));
            } else {
                tempStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/redCircle.png")));
            }
        });
    }

    public void readAndUpdateHumidity(){
        Platform.runLater(() -> {
            currentHumid = Double.parseDouble(mqttManager.getLatestHumidity().trim());
            humiReadingLabel.setText(String.valueOf(currentHumid) + " %");

            if (currentHumid >= humidityFloor && currentHumid <= humidityRoof){
                humiStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/greenCircle.png")));
            } else {
                humiStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/redCircle.png")));
            }
        });
    }



    public void readAndUpdateLoudness(){
        Platform.runLater(() -> {
            currentLoudness = Double.parseDouble(mqttManager.getLatestSound().trim());
            loudnessReadingLabel.setText(String.valueOf(currentLoudness));

            if (currentLoudness <= loudnessRoof) {
                loudStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/greenCircle.png")));
            } else {
                loudStatusImage.setImage(new Image(getClass().getResourceAsStream("/org/stuwiapp/images/redCircle.png")));
            }
        });
    }

    private void readAndUpdateStudyStatus() {
        Platform.runLater(() ->  {
            isSessionOngoing = StudySessionManager.getInstance().isSessionActive();
            isBreakActive = mqttManager.getBreakStatus();
            if(isSessionOngoing && !isBreakActive) {
                studyStatusLabel.setText("Study Session Ongoing");
            } else if (isSessionOngoing) {
                studyStatusLabel.setText("Break");


            } else {
                studyStatusLabel.setText("Not Studying");
            }

        });
    }
    public void stopSession(ActionEvent event) {
        try {
            mqttManager.publish(stopSessionTopic, "Stop Session");
            StudySessionManager.getInstance().endSession();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        redirect(event, "stuwi-home.fxml" );
    }


    public static Pair<Integer, String> showFeedbackPopup() {

    ButtonType saveButton = new ButtonType("Save Session", ButtonBar.ButtonData.OK_DONE);
    ButtonType discardButton = new ButtonType("Discard Session", ButtonBar.ButtonData.CANCEL_CLOSE);

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", saveButton, discardButton);
    alert.setTitle("Study Session Ended");
    alert.setHeaderText("Please provide your rating to save the session");

    DialogPane dialogPane = alert.getDialogPane();

    Slider slider = new Slider();
    slider.setMin(1);
    slider.setMax(5);
    slider.setValue(1);
    slider.setMajorTickUnit(1);
    slider.setMinorTickCount(0);
    slider.setSnapToTicks(true);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);

    TextArea textArea = new TextArea();
    textArea.setPrefColumnCount(20);
    textArea.setPrefRowCount(5);
    textArea.setWrapText(true);

    VBox vbox = new VBox(slider, textArea);
    dialogPane.setContent(vbox);

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == saveButton) {
        return new Pair<>((int) slider.getValue(), textArea.getText());
    } else {
        return null;
    }
}

    public static void setRanges(int tempMax, int tempMin, int humidMax, int humidMin, int loudMax){ //updates the ranges when new setting is loaded.
        humidityFloor = humidMin;
        humidityRoof = humidMax;
        temperatureFloor = tempMin;
        temperatureRoof = tempMax;
        loudnessRoof = loudMax;
    }

}


