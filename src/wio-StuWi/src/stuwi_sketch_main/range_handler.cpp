#include "range_handler.h"
#include "mqtt.h"
#include <sstream>

int temp_max = 30;
int temp_min = 26;
int humid_max = 35; 
int humid_min = 32;
int loud_max = 50;



void update_ranges(char* rangeData){

     std::stringstream rangeDataStream(rangeData);

     rangeDataStream >> temp_max >> temp_min >> humid_max >> humid_min >> loud_max;
}