#ifndef RTC_HANDLER_H
#define RTC_HANDLER_H


#include "RTC_SAMD51.h"
#include "DateTime.h"
#include "wifi.h"

#define NTP_PACKET_SIZE 48

extern RTC_SAMD51 rtc;

extern unsigned int udp_local_port;

extern char time_server[];

extern DateTime current_time;

extern unsigned long device_time;

extern void setup_rtc();

extern unsigned long get_NTP_time();

extern unsigned long send_NTP_packet(const char* address);

extern String get_time(DateTime dt);

#endif