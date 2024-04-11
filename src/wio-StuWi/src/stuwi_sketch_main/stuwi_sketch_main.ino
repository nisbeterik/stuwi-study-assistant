#include <rpcWiFi.h> // WiFi library

#define TIMEOUT_LIMIT 10  // max amount of tries for connecting to wifi

const char* ssid = "myNetwork";
const char* password =  "myPassword";
byte timeout_flag = 0;  

void setup() {
  Serial.begin(115200);
  while(!Serial); // Wait for Serial to be ready

  wifi_setup(); 
  

}

void loop() {
  
}

// connects the  terminal to WiFi
// Source: https://wiki.seeedstudio.com/Wio-Terminal-Wi-Fi/
void wifi_setup() {
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();

    Serial.println("Connecting to WiFi..");
    WiFi.begin(ssid, password);

    // tries to connect repeatedly if first try fails
    // stops trying when TIMEOUE_LIMIT constant reached
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