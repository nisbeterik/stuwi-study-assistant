package org.stuwiapp;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/***
 * This class creates a client and manages connections to the client.
 * To make use of the singleton instance, inject the instance into a MQTTManager
 * using MQTTManager = MQTTManagerSingleton.getMqttInstance()
 */

public class MQTTManager {
    private final String BROKER ="tcp://broker.hivemq.com:1883" ;
    private String clientId;
    private final int QOS = 0;  // 0 sends message at most once
    private MqttClient client;

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
        client.publish(topic, message);
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
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery complete. ");
            }
        };
    }
}