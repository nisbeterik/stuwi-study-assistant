#include "wifi.h"
#include "mqtt.h"
#include <DHT.h> //DHT sensor library by Adafruit
#include <stdio.h>

#define DHTPIN D0
#define DHTTYPE DHT11 // DHT 11


//DHT
DHT dht(DHTPIN, DHTTYPE);



long lastMsg = 0; // tracks when last message was sent in relation to millis variable
int value = 0;  // amount of payloads published



void setup() {
  Serial.begin(115200);
  while(!Serial); // Wait for Serial to be ready

  wifi_setup(); 
  client.setServer(MQTT_SERVER, 1883); // Connect the MQTT Server
  client.setCallback(callback);

  dht.begin();

}

void loop() {
  if (!client.connected()) {
    reconnect_mqtt();
  }
  client.loop();

  //
  long now = millis();
  // publishes a message every 10 seconds
  if (now - lastMsg > 10000) {
    lastMsg = now;
    ++value;
    snprintf (msg, 50, "Wio message #%ld", value);
    Serial.print("Publish message: ");
    Serial.println(msg);
    client.publish(TOPIC_PUBLISH, msg);
    read_temperature();
    read_humidity();
    Serial.println(temp_payload);
    client.publish(TOPIC_TEMP, temp_payload);
    Serial.println(humid_payload);
    client.publish(TOPIC_HUMID, humid_payload);
  }
}


void read_temperature(){
  float temp = dht.readTemperature();
  sprintf(temp_payload, "Temperature: %.2f *C", temp);
}

void read_humidity(){
  float humidity = dht.readHumidity();
  sprintf(humid_payload, "Humidity: %.2f %%", humidity);
}

