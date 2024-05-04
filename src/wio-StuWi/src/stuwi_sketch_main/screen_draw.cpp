#include "mqtt.h"
#include "screen_draw.h"
#include "rtc_handler.h"

TFT_eSPI tft; //initialize TFT LCD
TFT_eSprite spr = TFT_eSprite(&tft);  //sprite

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
  
  // Draws the TEMP Text in the lower box
  tft.setTextColor(TFT_CYAN);
  tft.drawString("Temperature", 10 , 153 , 1);
  tft.setTextColor(TFT_RED);
  tft.drawString("o", 64, 180, 1);
  tft.drawString("C", 75, 193, 1);

  tft.setTextColor(TFT_CYAN);
  tft.drawString("Humidity", 10 , 65 , 1);
  tft.setTextColor(TFT_RED);
  tft.drawString("%", 65, 105, 1);

  tft.setTextColor(TFT_CYAN);
  tft.drawString("Loudness", 10 + (tft.width() / 2) , 65 , 1);
  tft.setTextColor(TFT_RED);
  tft.drawString("%", 65 + (tft.width() / 2), 105, 1);

  
  
}

void update_screen(){
  sprintf(temp_string, "%s", temp_int);
  sprintf(humid_string, "%s", humid_int);
  sprintf(loudness_string, "%s", loud_int);


  //We use sprites so the updated text is not drawn ontop of the old data
  spr.setTextColor(TFT_WHITE);
  update_sprite(temp_int, 20, 193, 40);
  update_sprite(humid_int, 20, 105, 40);
  update_sprite(loud_int, ((tft.width() / 2) + 25) , 105, 40);

  if(!alarm_flag) {
      //tft.drawString("No session", 0, 150);
      spr.setTextColor(TFT_RED);
      update_sprite("No Session", ((tft.width() / 2) + 10) , 153, 135);¢¢
      spr.setTextColor(TFT_WHITE);
      update_sprite(get_time(current_time), ((tft.width() / 2) + 25) , 193, 100); // get time from rtc_handler and
  } else if(alarm_flag && !activeBreak) {
      spr.setTextColor(TFT_CYAN);
      update_sprite("Studying", ((tft.width() / 2) + 10) , 153, 135);
      spr.setTextColor(TFT_WHITE);
      update_sprite(get_remaining_time(), ((tft.width() / 2) + 25) , 193, 100);
  } else if(alarm_flag && activeBreak){
      spr.setTextColor(TFT_RED);
      update_sprite("Active break", ((tft.width() / 2) + 10) , 153, 135);
      spr.setTextColor(TFT_WHITE);
      update_sprite(get_remaining_time(), ((tft.width() / 2) + 25) , 193, 100);
  }
  prev_alarm_flag = alarm_flag;
}

void update_sprite(String data, int x, int y, int width){
  spr.createSprite(width, 30);
  spr.setFreeFont(&FreeSansBoldOblique12pt7b);
  spr.drawString(data, 0, 0, 1);
  spr.pushSprite(x, y);
  spr.deleteSprite();
}

