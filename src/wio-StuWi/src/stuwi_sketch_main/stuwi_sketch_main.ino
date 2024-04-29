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
int value = 0;  // amount of payloads published
float loudVal = 0;

static bool a_button_pressed = false;
static bool b_button_pressed = false;
void check_button_press();
void check_button_released();

void setup() {
  Serial.begin(115200);
  while(!Serial); // Wait for Serial to be ready
  wifi_setup();
  setup_rtc();
  client.setServer(MQTT_SERVER, 1883); // Connect the MQTT Server
  client.setCallback(callback);



  dht.begin();
  screen_setup();

  pinMode(WIO_KEY_A, INPUT_PULLUP); //Key A = Button A
  pinMode(WIO_KEY_B, INPUT_PULLUP); //Key B = Button B
 

}

void loop() {

  if (!client.connected()) {
    reconnect_mqtt();
  }
  client.loop();
  current_time = rtc.now(); //update DateTime object to follow rtc
  long now = millis();
  // continously checks remaining time of alarm to see when its over
  if(alarm_flag){
    check_remaining_time();
  }

  check_button_press();
  check_button_released();


  // updates screen and values every second
  if(now - sensor_value_update > 1000) {
      sensor_value_update = now;
      read_temperature();
      read_humidity();
      read_loudness();
      update_screen();
  }
  // publishes a message to broker every 10 seconds
  if (now - last_published > 10000) {
    last_published = now;
    ++value;
    snprintf (msg, 50, "Wio message #%ld", value);
    publish_testmessage();
    publish_sensor_values();
    
    
  }
}

void check_button_press(){
  if (digitalRead(WIO_KEY_A) == LOW && !a_button_pressed) {
    Serial.println("A Key pressed");
    a_button_pressed = true; // Set button state
  }

  // Check for B button press
  if (digitalRead(WIO_KEY_B) == LOW && !b_button_pressed) {
    Serial.println("B Key pressed");
    b_button_pressed = true; // Set button state
  }
}

void check_button_released(){
   if (digitalRead(WIO_KEY_A) == HIGH && a_button_pressed) {
    Serial.println("A Key released");
    a_button_pressed = false;

    // Publish a message to the MQTT topic indicating button A press:
    client.publish(TOPIC_START_SESSION_BUTTON, button_a_payload);

   }
   if (digitalRead(WIO_KEY_B) == HIGH && b_button_pressed) {
    Serial.println("B Key released");
    b_button_pressed = false;

    // Publish a message to the MQTT topic indicating button A press:
    client.publish(TOPIC_STOP_SESSION_BUTTON, button_b_payload);

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
  sprintf(loud_payload, "%.2f ", loudVal);
}
