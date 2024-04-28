#include "mqtt.h"
#include "screen_draw.h"
#include "rtc_handler.h"

TFT_eSPI tft; //initialize TFT LCD

char temp_string[20]; // Allocate memory for temp_string
char humid_string[20]; // Allocate memory for humid_string
char loudness_string[20]; // Allocate memory for loudness_string

byte prev_alarm_flag = 0;

// sets up LCD screen
// called by main setup()
void screen_setup() {
  tft.begin(); //start TFT LCD 
  tft.setRotation(3); //set screen rotation 
  tft.fillScreen(TFT_WHITE); //fill background 
  tft.setTextSize(1);
}

// called by main loop every second
// updates sensor values
// updates current time if no session is ongoing
// updates time remaining of session if session is ongoing (checked with alarm_flag)
void draw_background() {
    tft.fillScreen(TFT_BLACK);
    tft.setFreeFont(&FreeSansBoldOblique18pt7b);
    tft.setTextColor(TFT_WHITE);
    tft.drawString("StuWi", 104, 10 , 1);

    for (int8_t line_index = 0; line_index < 5 ; line_index++) {
      tft.drawLine(0, 50 + line_index, tft.width(), 50 + line_index, TFT_CYAN);
    }
    tft.drawRoundRect(5, 60, (tft.width() / 2) - 5 , (tft.height() - 65) / 2 , 10, TFT_WHITE); // L1
    tft.drawRoundRect((tft.width() / 2) + 5  , 60, (tft.width() / 2) - 10 , (tft.height() - 65) / 2 , 10, TFT_WHITE); // s1
    tft.drawRoundRect(5, (65 + (tft.height() - 65) / 2) , (tft.width() / 2) - 5 , (tft.height() - 70) / 2 , 10, TFT_WHITE); // L1
    tft.drawRoundRect((tft.width() / 2) + 5  , (65 + (tft.height() - 65) / 2), (tft.width() / 2) - 10 , (tft.height() - 70) / 2 , 10, TFT_WHITE); // s1

    tft.setFreeFont(&FreeSansBoldOblique12pt7b);
  tft.setTextColor(TFT_GREEN);
  
  // Draws the TEMP Text in the lower box
  tft.drawString("Temp", 10 , 153 , 1);
  tft.setTextColor(TFT_RED);
  tft.drawString("o", 64, 180, 1);
  tft.drawString("C", 75, 193, 1);
}

void update_screen(){
  sprintf(temp_string, "Temp: %s", temp_payload);
  sprintf(humid_string, "Humid: %s", humid_payload);
  sprintf(loudness_string, "Loud: %s", loud_payload);
  
  if(prev_alarm_flag != alarm_flag) {
    tft.fillScreen(TFT_WHITE);
  }

  tft.drawString(temp_string,0,0); //draw text string 
  tft.drawString(humid_string,0,50);
  tft.drawString(loudness_string,0, 100);
  if(!alarm_flag) {
      tft.drawString("No session", 0, 150);
      tft.drawString(get_time(current_time), 0, 200); // get time from rtc_handler and
  } else if(alarm_flag) {
      tft.drawString("Ongoing sesh", 0, 150);
      tft.drawString(get_remaining_time(), 0, 200);
  }
  prev_alarm_flag = alarm_flag;
}
