#include "wio_session_handler.h"
#include "rtc_handler.h"
#include "mqtt.h"



bool session_active = 0;


// is called when session is started through app
void start_session(char* details) {
  if(!session_active) {
    session_active = 1;
    populate_alarm_queue(details);
    set_alarm();
  }
}

// is called when session is ended through app 
// or when alarm for session is over
void end_session() {
  if(session_active) {
    session_active = 0;
    publish_session_over();
    if(alarm_flag) {
      disable_alarm();
    }
  }
}