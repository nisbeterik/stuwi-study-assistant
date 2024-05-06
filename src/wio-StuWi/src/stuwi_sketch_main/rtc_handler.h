#ifndef RTC_HANDLER_H
#define RTC_HANDLER_H


#include "RTC_SAMD51.h"
#include "DateTime.h"
#include "wifi.h"

#define NTP_PACKET_SIZE 48

extern RTC_SAMD51 rtc;

extern bool activeBreak;

extern DateTime current_time;

extern byte alarm_flag;

extern void setup_rtc();

extern unsigned long get_NTP_time();

extern unsigned long send_NTP_packet(const char* address);

extern String get_time(DateTime dt);

extern void set_alarm();

extern void disable_alarm();

extern void alarm_over();

extern String get_remaining_time();

extern void check_remaining_time();

extern void populate_alarm_queue(char* details);

#endif