#include "wio_session_handler.h"

bool session_active = 0;

void start_session() {
  if(!session_active) {
    session_active = 1;
  }
}

void end_session() {
  if(session_active) {
    session_active = 0;
  }
}