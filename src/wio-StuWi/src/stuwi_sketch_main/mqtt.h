#ifndef MQTT_H                                       
#define MQTT_H

#include <PubSubClient.h> //MQTT library
#include "wifi.h"

extern PubSubClient client;

// payloads
extern char temp_payload[50];
extern char humid_payload[50];
extern char msg[50]; // test publish payload

// mqtt server
extern const char* MQTT_SERVER;  // MQTT Broker URL

// subscribe topics
extern const char* TOPIC_SUBSCRIBE; 
// publish topics
extern const char* TOPIC_PUBLISH;
extern const char* TOPIC_TEMP;
extern const char* TOPIC_HUMID;

extern void callback(char* topic, byte* payload, unsigned int length);

extern void reconnect_mqtt();


#endif