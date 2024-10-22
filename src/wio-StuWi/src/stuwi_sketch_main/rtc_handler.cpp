#include "rtc_handler.h"
#include "wio_session_handler.h"
#include "ArduinoQueue.h"
#include <sstream>
#include "buzzer_handler.h"

WiFiUDP udp;
unsigned int udp_local_port = 2390;
char time_server[] = "ntp.se";  // swedish server to get time
byte packetBuffer[NTP_PACKET_SIZE];

RTC_SAMD51 rtc; // rtc library object

bool activeBreak = false; // Flag for if break is currently active

DateTime current_time; // object to track current time
DateTime alarm_time;  // object to track potential alarm time
unsigned long device_time;
byte alarm_flag = 0; // flag to indicate whether alarm is active
ArduinoQueue<unsigned long> alarm_queue(50);
unsigned int alarm_queue_size = 0;
// called in setup() of main class
// gets time from NTP server and injects into RTC
void setup_rtc() {
  rtc.begin();
  device_time = get_NTP_time();
  
  rtc.adjust(DateTime(device_time));
  current_time = rtc.now();
  // print time on boot
  Serial.print("RTC time on boot: ");
  Serial.println(get_time(current_time));
}

// Source: https://wiki.seeedstudio.com/Wio-Terminal-RTC/
// if WiFi is connected, send NTP time request to server
unsigned long get_NTP_time() {
  // Module returns an unsigned long time value as seconds since Jan 1, 1970 (Unix time) or 0 if a problem encountered

  // Only send data when connected
  if (WiFi.status() == WL_CONNECTED) {
    // Initializes the UDP state and buffer
    udp.begin(WiFi.localIP(), udp_local_port);

    // Send an NTP packet to the time server
    send_NTP_packet(time_server);

    // Wait to see if a reply is available
    delay(1000);
    if (udp.parsePacket()) {
      Serial.println("UDP packet received");
      Serial.println("");

      // We've received a packet, read the data from it
      udp.read(packetBuffer, NTP_PACKET_SIZE);  // Read the packet into the buffer

      // The timestamp starts at byte 40 of the received packet and is four bytes,
      // or two words, long. First, extract the two words:
      unsigned long highWord = word(packetBuffer[40], packetBuffer[41]);
      unsigned long lowWord = word(packetBuffer[42], packetBuffer[43]);

      // Combine the four bytes (two words) into a long integer.
      // This is NTP time (seconds since Jan 1, 1900):
      unsigned long secsSince1900 = highWord << 16 | lowWord;

      // Unix time starts on Jan 1, 1970. In seconds, that's 2208988800:
      const unsigned long seventyYears = 2208988800UL;

      // Subtract seventy years:
      unsigned long epoch = secsSince1900 - seventyYears;

      // Adjust time for timezone offset in seconds +/- from UTC
      // WA time offset from UTC is +8 hours (28,800 secs)
      // + East of GMT
      // - West of GMT
      long tzOffset = 7200UL;  // 7200 seconds = Swedish

      // WA local time
      unsigned long adjustedTime;
      return adjustedTime = epoch + tzOffset;
    } else {
      // We were not able to parse the UDP packet successfully
      // Clear down the UDP connection
      udp.stop();
      return 0;  // Zero indicates a failure
    }

    // Not calling NTP time frequently, so stop releases resources
    udp.stop();
  } else {
    // Network not connected
    return 0;
  }
}


// Source: https://wiki.seeedstudio.com/Wio-Terminal-RTC/
// packet to send to NTP server
unsigned long send_NTP_packet(const char* address) {
  // set all bytes in the buffer to 0
  for (int i = 0; i < NTP_PACKET_SIZE; ++i) {
    packetBuffer[i] = 0;
  }
  // Initialize values needed to form NTP request
  // (see URL above for details on the packets)
  packetBuffer[0] = 0b11100011;  // LI, Version, Mode
  packetBuffer[1] = 0;           // Stratum, or type of clock
  packetBuffer[2] = 6;           // Polling Interval
  packetBuffer[3] = 0xEC;        // Peer Clock Precision
  // 8 bytes of zero for Root Delay & Root Dispersion
  packetBuffer[12] = 49;
  packetBuffer[13] = 0x4E;
  packetBuffer[14] = 49;
  packetBuffer[15] = 52;

  // all NTP fields have been given values, now
  // you can send a packet requesting a timestamp:
  udp.beginPacket(address, 123);  //NTP requests are to port 123
  udp.write(packetBuffer, NTP_PACKET_SIZE);
  udp.endPacket();
}

// returns time as string
// called from screen_draw passing current_time object for real-time display of time
String get_time(DateTime dt) {
  String time_str = "";

  if (dt.hour() < 10) time_str += '0';
  time_str += String(dt.hour(), DEC);
  time_str += ':';

  if (dt.minute() < 10) time_str += '0';
  time_str += String(dt.minute(), DEC);
  time_str += ':';

  if (dt.second() < 10) time_str += '0';
  time_str += String(dt.second(), DEC);

  return time_str;
}

// gets remaining time of alarm
// called by screen_draw to display remaining time of session
String get_remaining_time() {
  String remaining_time_str = "";
  if(alarm_flag) {
    
    unsigned long remaining_seconds = alarm_time.unixtime() - current_time.unixtime();

    unsigned long hours = remaining_seconds / 3600;
    unsigned long minutes = (remaining_seconds % 3600) / 60;
    unsigned long seconds = remaining_seconds % 60;

  

    if (hours < 10) remaining_time_str += '0';
    remaining_time_str += String(hours, DEC);
    remaining_time_str += ':';

    if (minutes < 10) remaining_time_str += '0';
    remaining_time_str += String(minutes, DEC);
    remaining_time_str += ':';

    if (seconds < 10) remaining_time_str += '0';
    remaining_time_str += String(seconds, DEC);

  
  }
  return remaining_time_str;
  
}

// sets alarm (length of study session)
void set_alarm() {
  if (!alarm_flag) {
    unsigned long duration = alarm_queue.dequeue();
    alarm_flag = 1;
    alarm_time = DateTime(current_time + TimeSpan(duration));  // 10 second alarm as placeholder
  }
}

// disables alarm
// called when session is prematurely ended through app
// empties alarm_queue
void disable_alarm() {
  alarm_flag = 0;
  activeBreak = false;
  while(!alarm_queue.isEmpty()) {
        alarm_queue.dequeue();
        alarm_queue_size--;
  }
  alarm_time = current_time;
}

void check_break(){

    if (alarm_queue_size > 0 && alarm_queue_size % 2 == 0 && !activeBreak){
    activeBreak = true;}
    else {
        activeBreak = false;
    }
}

// called by main loop
// checks to see if alarm is still ongoing
// when its over, call alarm_over()
void check_remaining_time() {
  unsigned long remaining_seconds = alarm_time.unixtime() - current_time.unixtime();
  if(remaining_seconds <= 0) {
    alarm_over();
  }
}

// alarm_over is called to indicate that an alarm is over
// calls end_session which will publish that the session is over to app if there's no alarms left in queue
void alarm_over() {
  play_default_sound();
  check_break();

  alarm_flag = 0;
  alarm_time = current_time;
  Serial.println("Alarm ended");
  if(!alarm_queue.isEmpty()) {
    set_alarm();
  }
  else {
    end_session();
  }

}

void populate_alarm_queue(char* details) {

     unsigned long study_time;
     unsigned long break_time;
     int num_of_blocks;


     std::stringstream ss(details);

     ss >> num_of_blocks >> study_time >> break_time; // parsing from payload string with the format "num_of_blocks study_time break_time"
     // convert to seconds
     study_time = study_time*60;
     break_time = break_time*60;
     alarm_queue_size = 0;

     for(num_of_blocks; num_of_blocks>0; num_of_blocks--) {
        alarm_queue.enqueue(study_time);
        alarm_queue.enqueue(break_time);
        alarm_queue_size = alarm_queue_size + 2;
     }

}