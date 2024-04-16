#include "mqtt.h"

PubSubClient client(wioClient);

// payloads
char temp_payload[50];
char humid_payload[50];
char msg[50]; // test publish payload

// mqtt server
const char* MQTT_SERVER = "broker.mqtt-dashboard.com";  // MQTT Broker URL

// subscribe topics
const char* TOPIC_SUBSCRIBE = "stuwi/testin"; 
// publish topics
const char* TOPIC_PUBLISH = "stuwi/testout";
extern const char* TOPIC_TEMP = "stuwi/temp";
const char* TOPIC_HUMID = "stuwi/humid";

void reconnect_mqtt() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "WioTerminal-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish(TOPIC_PUBLISH, "First payload published");
      // ... and resubscribe
      client.subscribe(TOPIC_SUBSCRIBE);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
  
  // prints that message arrived in specific topic
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  char buff_p[length]; // payload buffer
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    buff_p[i] = (char)payload[i];
  }
  Serial.println();
  buff_p[length] = '\0';  // null terminate buffer
  String msg_p = String(buff_p);
  Serial.println(msg_p);  // print payload as string

}