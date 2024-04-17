#ifndef WIFI_H                                       
#define WIFI_H

#include <rpcWiFi.h> // WiFi library
#define TIMEOUT_LIMIT 10  // max amount of tries for connecting to wifi

extern WiFiClient wioClient;

extern WiFiUDP udp;

extern const char* SSID;
extern const char* PASSWORD;


extern void wifi_setup();

#endif  