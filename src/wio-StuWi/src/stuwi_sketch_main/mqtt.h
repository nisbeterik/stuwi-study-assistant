#ifndef MQTT_H                                       
#define MQTT_H

#include <PubSubClient.h> //MQTT library
#include "wifi.h"

extern PubSubClient client;

// payloads
extern char temp_payload[50];
extern char humid_payload[50];
extern char loud_payload[50];
extern char temp_int[50];
extern char humid_int[50];
extern char loud_int[50];


//Buttons:
extern char button_a_payload[18];
extern char button_b_payload[18];
//

// mqtt server
extern const char* MQTT_SERVER;  // MQTT Broker URL



//Buttons
extern const char* TOPIC_START_SESSION_BUTTON;
extern const char* TOPIC_STOP_SESSION_BUTTON;
//

extern void callback(char* topic, byte* payload, unsigned int length);

extern void reconnect_mqtt();

extern void publish_sensor_values();

extern void publish_session_over();

extern void publish_break_active();

extern void publish_break_inactive();

extern void check_topic(char* topic, char* payload);

#endif