package org.stuwiapp;

import java.time.LocalDateTime;
import java.util.UUID;


// Temporary class to experiment with database.
public class DatabaseTesting {

    public static void main(String[] args) {
        UUID id = UUID.randomUUID();
        StudySession ss = new StudySession(id, LocalDateTime.now());

        ss.addTemperatureData("42.6");
        ss.addTemperatureData("49.6");
        ss.addTemperatureData("22.6");

        ss.addLoudnessData("12.6");
        ss.addLoudnessData("42.6");
        ss.addLoudnessData("72.6");

        ss.addHumidityData("22.6");
        ss.addHumidityData("62.6");
        ss.addHumidityData("42.6");

        ss.setRatingScore(10);
        ss.setRatingText("Great session!");
        ss.setEndDate(LocalDateTime.now());

        StudySessionDAO.saveSessionInDatabase(ss);
    }
}
