#define BUZZER_PIN WIO_BUZZER

void buzzer_setup() {
    pinMode(BUZZER_PIN, OUTPUT);
}

void play_default_sound(){
        analogWrite(WIO_BUZZER, 128);
        delay(1000);
        analogWrite(WIO_BUZZER, 0);
        delay(1000);
      }



