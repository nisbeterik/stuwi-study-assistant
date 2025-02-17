#include "mqtt.h"
#include "wio_session_handler.h"
#include "range_handler.h"

PubSubClient client(wioClient);

// payloads
char temp_payload[50];
char humid_payload[50];
char loud_payload[50];
char session_over_payload[13] = "Session over";
char break_active_payload[6] = "Break";
char break_inactive_payload[9] = "No break";

// Start sessions payloads
char start_session_payload[34] = "Session started from Wio Terminal";

char msg[50]; // test publish payload

// mqtt server
const char* MQTT_SERVER = "broker.mqtt-dashboard.com";  // MQTT Broker URL

// subscribe topics
const char* TOPIC_STARTSESSION = "stuwi/startsession"; 
const char* TOPIC_ENDSESSION = "stuwi/endsession";
// publish topics
const char* TOPIC_TEMP = "stuwi/temp";
const char* TOPIC_HUMID = "stuwi/humid";
const char* TOPIC_LOUD = "stuwi/loudness";
const char* TOPIC_BREAK_ACTIVE = "stuwi/breakactive";
const char* TOPIC_BREAK_INACTIVE = "stuwi/breakinactive";
const char* TOPIC_RANGE_UPDATE = "stuwi/rangeupdate";


//Buttons publish topics
const char* TOPIC_START_SESSION_BUTTON = "stuwi/wiostartsession";

const char* TOPIC_SESSION_OVER = "stuwi/sessionover"; // topic used when time of session runs out


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
      // ... and resubscribe
      client.subscribe(TOPIC_STARTSESSION);
      client.subscribe(TOPIC_ENDSESSION);
      client.subscribe(TOPIC_RANGE_UPDATE);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

// callback function listening for incoming payloads
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
  check_topic(topic, buff_p);


}



// publishes sensor values to app every 10 seconds
// from main loop
void publish_sensor_values() {
  Serial.println(temp_payload);
  client.publish(TOPIC_TEMP, temp_payload);
  Serial.println(humid_payload);
  client.publish(TOPIC_HUMID, humid_payload);
  Serial.println(loud_payload);
  client.publish(TOPIC_LOUD, loud_payload);
}

// publishes that session is over when remaining time of session is 0
void publish_session_over() {
    client.publish(TOPIC_SESSION_OVER, session_over_payload);
}

void publish_start_session() {
    client.publish(TOPIC_START_SESSION_BUTTON, start_session_payload);
}

void publish_break_active() {
    client.publish(TOPIC_BREAK_ACTIVE, break_active_payload);
}

void publish_break_inactive() {
    client.publish(TOPIC_BREAK_INACTIVE, break_inactive_payload);
}

// checks incoming payload topic and directs program accordingly
void check_topic(char* topic, char* payload) {
  if( strcmp(topic, TOPIC_STARTSESSION) == 0) {
    start_session(payload);
    Serial.println("Session started");
  }
  else if( strcmp(topic, TOPIC_ENDSESSION) == 0) {
    end_session();
    Serial.println("Session ended");
  }
  else if( strcmp(topic, TOPIC_RANGE_UPDATE) == 0) {
    update_ranges(payload);
    Serial.println("Ranges Updated");
  }
}

