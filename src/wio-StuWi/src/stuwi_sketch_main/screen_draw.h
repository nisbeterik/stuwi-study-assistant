
#ifndef SCREEN_DRAW_H
#define SCREEN_DRAW_H

#include "TFT_eSPI.h"

extern TFT_eSPI tft;
extern char* temp_string;
extern char* humid_string;

extern void screen_setup();
extern void update_screen();

#endif