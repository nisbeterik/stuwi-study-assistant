#ifndef BUZZER_HANDLER_H 
#define BUZZER_HANDLER_H
#include "wifi.h"
#include <Arduino.h> 
#define BUZZER_PIN WIO_BUZZER

extern void buzzer_setup();
extern void play_default_sound();

#endif