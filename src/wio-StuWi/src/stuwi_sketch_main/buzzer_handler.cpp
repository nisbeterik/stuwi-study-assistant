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

        sound_duration = DateTime(current_time + 1);
        while(current_time.unixtime() != sound_duration.unixtime()) {
            analogWrite(WIO_BUZZER, 128);
        }
        
        analogWrite(WIO_BUZZER, 0);
        
        
      }


