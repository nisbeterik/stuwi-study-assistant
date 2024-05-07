#include "buzzer_handler.h"
#include "wifi.h"
#include "rtc_handler.h"
#define BUZZER_PIN WIO_BUZZER
#define SECOND 1

DateTime sound_duration;

void buzzer_setup() {
    pinMode(BUZZER_PIN, OUTPUT);
    
}

void play_default_sound(){

long startTime = millis();
        // Turn on the buzzer
        analogWrite(WIO_BUZZER, 128);

        while(millis() - startTime < 1000) {
            // Wait
        }

        // Turn off the buzzer after one second
        analogWrite(WIO_BUZZER, 0);
        
        
      }


