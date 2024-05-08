#include "range_handler.h"
#include "mqtt.h"
#include <sstream>

int temp_max = 23;
int temp_min = 21;
int humid_max = 60; 
int humid_min = 30;
int loud_max = 70;



void update_ranges(char* rangeData){

     std::stringstream rangeDataStream(rangeData);

     rangeDataStream >> temp_max >> temp_min >> humid_max >> humid_min >> loud_max;
}