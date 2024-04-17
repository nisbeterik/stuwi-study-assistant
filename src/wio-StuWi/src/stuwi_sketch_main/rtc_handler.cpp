#include "rtc_handler.h"

WiFiUDP udp;
unsigned int udp_local_port = 2390;
char time_server[] = "ntp.se";
byte packetBuffer[NTP_PACKET_SIZE];

RTC_SAMD51 rtc;

DateTime current_time;
unsigned long device_time;

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
            udp.read(packetBuffer, NTP_PACKET_SIZE); // Read the packet into the buffer

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
            long tzOffset = 7200UL; // 7200 seconds = Swedish

            // WA local time
            unsigned long adjustedTime;
            return adjustedTime = epoch + tzOffset;
        } else {
            // We were not able to parse the UDP packet successfully
            // Clear down the UDP connection
            udp.stop();
            return 0; // Zero indicates a failure
        }

        // Not calling NTP time frequently, so stop releases resources
        udp.stop();
    } else {
        // Network not connected
        return 0;
    }
}


// Source: https://wiki.seeedstudio.com/Wio-Terminal-RTC/
unsigned long send_NTP_packet(const char* address) {
  // set all bytes in the buffer to 0
    for (int i = 0; i < NTP_PACKET_SIZE; ++i) {
        packetBuffer[i] = 0;
    }
    // Initialize values needed to form NTP request
    // (see URL above for details on the packets)
    packetBuffer[0] = 0b11100011;   // LI, Version, Mode
    packetBuffer[1] = 0;     // Stratum, or type of clock
    packetBuffer[2] = 6;     // Polling Interval
    packetBuffer[3] = 0xEC;  // Peer Clock Precision
    // 8 bytes of zero for Root Delay & Root Dispersion
    packetBuffer[12] = 49;
    packetBuffer[13] = 0x4E;
    packetBuffer[14] = 49;
    packetBuffer[15] = 52;

    // all NTP fields have been given values, now
    // you can send a packet requesting a timestamp:
    udp.beginPacket(address, 123); //NTP requests are to port 123
    udp.write(packetBuffer, NTP_PACKET_SIZE);
    udp.endPacket();
}


String get_time(DateTime dt) {
    String timeStr = "";

    if (dt.hour() < 10) timeStr += '0';
    timeStr += String(dt.hour(), DEC);
    timeStr += ':';

    if (dt.minute() < 10) timeStr += '0';
    timeStr += String(dt.minute(), DEC);
    timeStr += ':';

    if (dt.second() < 10) timeStr += '0';
    timeStr += String(dt.second(), DEC);

    return timeStr;
    
    }