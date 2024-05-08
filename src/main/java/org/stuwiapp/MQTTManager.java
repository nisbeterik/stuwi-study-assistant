package org.stuwiapp;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.stuwiapp.Utils.PomodoroMappingDefault;

/***
 * This class creates a client and manages connections to the client.
 * To make use of the singleton instance, inject the instance into a MQTTManager
 * using MQTTManager = MQTTManagerSingleton.getMqttInstance()
 */

public class MQTTManager {
    private final String BROKER ="tcp://broker.hivemq.com:1883" ;
    private String clientId;
    private final int QOS = 1;  // 2 sends message at most once
    private MqttClient client;

    private String latestTemp = "0";
    private String latestHumidity = "0";
    private String latestSound = "0";

    private boolean isStudyActive = false;
    private boolean isBreakActive = false;



    public MQTTManager() throws MqttException{
        this.clientId = "StuWiApp";
        initiateMQTTClient();
    }

    public void initiateMQTTClient() throws MqttException{
        this.client = new MqttClient(BROKER, clientId, new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true); // sets client to automatically reconnect
        options.setCleanSession(true);  // remembers previous state after reconnects

        client.setCallback(callback());

        System.out.println("Connecting to broker: " + BROKER);
        client.connect(options);    // connects with connect options
        System.out.println("Connected");
    }

    public void publish(String topic, String payload) throws MqttException{
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(QOS);
        if(topic.equals("stuwi/startsession")) {
            StudySessionManager.getInstance().startSession();
            isBreakActive = false;
        }

        if(topic.equals("stuwi/endsession")) {
            StudySessionManager.getInstance().endSession();
        }
        try {
        client.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) throws MqttException{
        this.client.subscribe(topic);
    }

    public void close() throws MqttException{

        client.disconnect();
        client.close();
    }

    // enables asynchronous events related to mqttclient
    private MqttCallback callback() {
        return new MqttCallback() {
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("Message arrived. Topic: " + topic + " Message: " + message.toString());

                if (topic.equals("stuwi/temp")) {
                    latestTemp = message.toString();
                    StudySessionManager.getInstance().addTemperatureData(message.toString());
                }
                if (topic.equals("stuwi/humid")) {
                    latestHumidity = message.toString();
                    StudySessionManager.getInstance().addHumidityData(message.toString());
                }
                if (topic.equals("stuwi/loudness")) {
                    latestSound = message.toString();
                    StudySessionManager.getInstance().addLoudnessData(message.toString());
                }
                if(topic.equals("stuwi/sessionover")){
                    StudySessionManager.getInstance().endSession();
                    isBreakActive = false;
                }
                if(topic.equals("stuwi/breakactive")){
                    isBreakActive = true;
                }
                if(topic.equals("stuwi/breakinactive")){
                    isBreakActive = false;
                }

                if (topic.equals("stuwi/wiostartsession")) {

                    if (StudySessionManager.getInstance().isSessionActive()) {
                        System.out.println("Already ongoing session");
                    } else {

                        publish("stuwi/startsession", PomodoroMappingDefault.getPomodoroDefault());

                        System.out.println("Starting session...");
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery complete. ");
            }
        };
    }
    public String getLatestTemp() {
        return latestTemp;
    }

    public String getLatestHumidity() {
        return latestHumidity;
    }

    public String getLatestSound() {
        return latestSound;
    }

    public boolean getBreakStatus() {
        return isBreakActive;
    }
}
