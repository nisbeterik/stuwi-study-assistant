#include <rpcWiFi.h> // WiFi library
#include <PubSubClient.h> //MQTT library
#include <DHT.h> //DHT sensor library by Adafruit
#include <stdio.h>

#define DHTPIN D0
#define DHTTYPE DHT11 // DHT 11
#define TIMEOUT_LIMIT 10  // max amount of tries for connecting to wifi

// WiFi connection global variables
WiFiClient wioClient; // creates TCP connection for broker
const char* ssid = "myNetwork";
const char* password =  "myPassword";
byte timeout_flag = 0;  

//DHT
DHT dht(DHTPIN, DHTTYPE);
char temp_payload[50];
char humid_payload[50];

// MQTT connection global variables
// Source for MQTT logic: https://www.hackster.io/Salmanfarisvp/mqtt-on-wio-terminal-4ea8f8
PubSubClient client(wioClient); // mqtt client
long lastMsg = 0; // tracks when last message was sent in relation to millis variable
char msg[50]; // publish payload
int value = 0;  // amount of payloads published
const char* mqtt_server = "broker.mqtt-dashboard.com";  // MQTT Broker URL
// subscribe topics
const char* topic_subscribe = "stuwi/testin"; 
// publish topics
const char* topic_publish = "stuwi/testout";
const char* topic_temp = "stuwi/temp";
const char* topic_humid = "stuwi/humid";

void setup() {
  Serial.begin(115200);
  while(!Serial); // Wait for Serial to be ready

  wifi_setup(); 
  client.setServer(mqtt_server, 1883); // Connect the MQTT Server
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
    client.publish(topic_publish, msg);
    read_temperature();
    read_humidity();
    Serial.println(temp_payload);
    client.publish(topic_temp, temp_payload);
    Serial.println(humid_payload);
    client.publish(topic_humid, humid_payload);
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
      client.publish(topic_publish, "First payload published");
      // ... and resubscribe
      client.subscribe(topic_subscribe);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

// connects the  terminal to WiFi
// Source: https://wiki.seeedstudio.com/Wio-Terminal-Wi-Fi/
void wifi_setup() {
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();

    Serial.println("Connecting to WiFi..");
    WiFi.begin(ssid, password);

    // tries to connect repeatedly if first try fails
    // stops trying when TIMEOUT_LIMIT constant reached
    while ((WiFi.status() != WL_CONNECTED) && (timeout_flag < TIMEOUT_LIMIT)) {
        delay(500);
        Serial.println("Connecting to WiFi..");
        WiFi.begin(ssid, password);
        timeout_flag++;
    }
    // if limit reached and wifi not connected
    // print failure
    if((timeout_flag >= TIMEOUT_LIMIT) && (WiFi.status() != WL_CONNECTED)) {
        Serial.println("Connection failed");
    } 
    // success
    else {
        Serial.print("Connected to the WiFi network: ");
        Serial.println(ssid);
        Serial.print("IP Address: ");
        Serial.println (WiFi.localIP());
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

