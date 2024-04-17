#include "mqtt.h"
#include "screen_draw.h"

TFT_eSPI tft; //initialize TFT LCD
char* temp_string = "0,00";
char* humid_string = "0,00";

void screen_setup() {
  tft.begin(); //start TFT LCD 
  tft.setRotation(3); //set screen rotation 
  tft.fillScreen(TFT_WHITE); //fill background 
  tft.setTextSize(4);
}

void update_screen(){
  sprintf(temp_string, "Temp: %s", temp_payload);
  sprintf(humid_string, "Humid: %s", humid_payload);
  tft.drawString(temp_string,0,0); //draw text string 
  tft.drawString(humid_string,0,50);
}
