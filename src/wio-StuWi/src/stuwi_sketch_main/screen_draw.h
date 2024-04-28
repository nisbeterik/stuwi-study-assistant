
#ifndef SCREEN_DRAW_H
#define SCREEN_DRAW_H

#include "TFT_eSPI.h"

extern TFT_eSPI tft;
extern char temp_string[20];
extern char humid_string[20];
extern char loudness_string[20];

extern void screen_setup();
extern void draw_background();
extern void update_screen();

#endif