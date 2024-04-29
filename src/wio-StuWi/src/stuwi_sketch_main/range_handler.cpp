#include "range_handler.h"
#include "mqtt.h"
#include <sstream>

int temp_max = 100;
int temp_min = 0;
int humid_max = 100; 
int humid_min = 0;
int loud_max = 100;



void update_ranges(char* rangeData){

     std::stringstream rangeDataStream(rangeData);

     rangeDataStream >> temp_max >> temp_min >> humid_max >> humid_min >> loud_max;
}