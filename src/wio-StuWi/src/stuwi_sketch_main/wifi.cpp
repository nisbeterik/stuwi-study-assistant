#include "wifi.h"


WiFiClient wioClient;
const char* SSID       = "stuwi";                        // wifi network name
const char* PASSWORD   = "stuwi123";
byte timeout_flag = 0;       

// connects the  terminal to WiFi
// Source: https://wiki.seeedstudio.com/Wio-Terminal-Wi-Fi/
void wifi_setup() {
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();

    Serial.println("Connecting to WiFi..");
    WiFi.begin(SSID, PASSWORD);

    // tries to connect repeatedly if first try fails
    // stops trying when TIMEOUT_LIMIT constant reached
    while ((WiFi.status() != WL_CONNECTED) && (timeout_flag < TIMEOUT_LIMIT)) {
        delay(500);
        Serial.println("Connecting to WiFi..");
        WiFi.begin(SSID, PASSWORD);
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
        Serial.println(SSID);
        Serial.print("IP Address: ");
        Serial.println (WiFi.localIP());
    }
    
}