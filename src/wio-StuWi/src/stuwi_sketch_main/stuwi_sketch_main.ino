#include "wifi.h"
#include "mqtt.h"
#include <DHT.h> //DHT sensor library by Adafruit
#include <stdio.h>
#include "screen_draw.h"
#include "rtc_handler.h"

#define DHTPIN D0
#define DHTTYPE DHT11 // DHT 11


//DHT
DHT dht(DHTPIN, DHTTYPE);



long last_published = 0; // tracks when last message was sent in relation to millis variable
long sensor_value_update = 0; // tracks when last sensor value update was done
int loud_val = 0;
int loud_percent = 0;



void setup() {
  Serial.begin(115200);
  while(!Serial); // Wait for Serial to be ready
  wifi_setup();
  setup_rtc();
  client.setServer(MQTT_SERVER, 1883); // Connect the MQTT Server
  client.setCallback(callback);

  dht.begin();
  screen_setup();
  draw_background();
}

void loop() {
  if (!client.connected()) {
    reconnect_mqtt();
  }
  client.loop();
  current_time = rtc.now();  //update DateTime object to follow rtc
  long now = millis();
  // continously checks remaining time of alarm to see when its over
  if(alarm_flag){
    check_remaining_time();
  }
  // updates screen and values every second
  if (now - sensor_value_update > 1000) {
    sensor_value_update = now;
    read_temperature();
    read_humidity();
    read_loudness();
    update_screen();
  }
  // publishes a message to broker every 10 seconds
  if (now - last_published > 10000) { //TODO: make it update every second instead. 
    last_published = now;

    publish_sensor_values(); 
    if(activeBreak) {
        publish_break_active();
    } else if (!activeBreak) {
        publish_break_inactive();
    }

  }
}


void read_temperature() {
  temp_int = (int)dht.readTemperature();
  sprintf(temp_payload, "%d", temp_int);
}

void read_humidity() {
  humid_int = (int)dht.readHumidity();
  sprintf(humid_payload, "%d", humid_int);
}

void read_loudness() {

  loud_val = (int)analogRead(A3);
  loud_percent = map(loud_val, 0, 1023, 0, 200);
  if (loud_percent > 100){ //If value is over 100 change it to 100.
    loud_percent = 100;
  }
  loud_int = loud_percent;
  sprintf(loud_payload, "%d", loud_int);

}
