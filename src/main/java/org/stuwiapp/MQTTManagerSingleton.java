package org.stuwiapp;

import org.eclipse.paho.client.mqttv3.MqttException;

/***
 * This class ensures theres only one instance of the MQTTManager
 * as to not create several connections
 */

public class MQTTManagerSingleton {
    private static final MQTTManager MQTT_INSTANCE;

    private MQTTManagerSingleton() {
    }

    static {
        try {
            MQTT_INSTANCE = new MQTTManager();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    // function to get singleton instance of MQTTManager
    public static MQTTManager getMqttInstance() {
        return MQTT_INSTANCE;
    }
}
