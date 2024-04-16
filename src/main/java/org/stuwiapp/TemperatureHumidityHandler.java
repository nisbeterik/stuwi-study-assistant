package org.stuwiapp;

import org.eclipse.paho.client.mqttv3.MqttException;

public class TemperatureHumidityHandler {
    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();

    private final String temperatureTopic = "stuwi/temp";
    private final String humidityTopic = "stuwi/humid";

    private float tempData;
    private float humidityData;
    public TemperatureHumidityHandler() throws MqttException {
        mqttManager.subscribe(temperatureTopic);
        mqttManager.subscribe(humidityTopic);
    }
}
