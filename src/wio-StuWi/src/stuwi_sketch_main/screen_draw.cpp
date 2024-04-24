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
  tft.setTextSize(4);
}

// called by main loop every second
// updates sensor values
// updates current time if no session is ongoing
// updates time remaining of session if session is ongoing (checked with alarm_flag)
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
