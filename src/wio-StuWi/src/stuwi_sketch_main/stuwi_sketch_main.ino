#include "wifi.h"
#include "mqtt.h"
#include <DHT.h> //DHT sensor library by Adafruit
#include <stdio.h>
#include "screen_draw.h"

#define DHTPIN D0
#define DHTTYPE DHT11 // DHT 11


//DHT
DHT dht(DHTPIN, DHTTYPE);



long lastMsg = 0; // tracks when last message was sent in relation to millis variable
int value = 0;  // amount of payloads published
float loudVal = 0;



void setup() {
  Serial.begin(115200);
  while(!Serial); // Wait for Serial to be ready
  wifi_setup();
  client.setServer(MQTT_SERVER, 1883); // Connect the MQTT Server
  client.setCallback(callback);

  dht.begin();
  screen_setup();

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
    publish_testmessage();
    read_temperature();
    read_humidity();
    read_loudness();
    publish_sensor_values();
    
    update_screen();
  }
}


void read_temperature(){
  float temp = dht.readTemperature();
  sprintf(temp_payload, "%.2f", temp);
}

void read_humidity(){
  float humidity = dht.readHumidity();
  sprintf(humid_payload, "%.2f", humidity);
}

void read_loudness(){

  loudVal = analogRead(A3);
  sprintf(loud_payload, "%.2f ", loudVal);}
