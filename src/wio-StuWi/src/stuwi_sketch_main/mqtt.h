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
extern char session_over_payload[13];


// mqtt server
extern const char* MQTT_SERVER;  // MQTT Broker URL

// subscribe topics
extern const char* TOPIC_STARTSESSION;
extern const char* TOPIC_ENDSESSION; 
// publish topics
extern const char* TOPIC_PUBLISH;
extern const char* TOPIC_TEMP;
extern const char* TOPIC_HUMID;
extern const char* TOPIC_LOUD;
extern const char* TOPIC_SESSION_OVER;

extern void callback(char* topic, byte* payload, unsigned int length);

extern void reconnect_mqtt();

extern void publish_testmessage();

extern void publish_sensor_values();

extern void publish_session_over();

extern void publish_break_active();

extern void publish_break_inactive();

extern void check_topic(char* topic, char* payload);

#endif