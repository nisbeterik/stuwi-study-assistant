package org.stuwiapp;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class StudySession {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int rating;
    private String ratingText;
    private int minutesPaused;
    private String user;
    private int avgLoud;
    private int avgTemp;
    private int avgHumid;
    private int highestTemp;
    private int lowestTemp;
    private int highestHumid;
    private int lowestHumid;
    private int highestLoud;
    private int lowestLoud;

    public StudySession(LocalDateTime startDate, LocalDateTime endDate, String user, int minutesPaused, int avgTemp,
                        int highestTemp, int lowestTemp, int avgHumid, int highestHumid,
                        int lowestHumid, int avgLoud, int highestLoud, int lowestLoud) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.minutesPaused = minutesPaused;

        this.avgTemp = avgTemp;
        this.highestTemp = highestTemp;
        this.lowestTemp = lowestTemp;

        this.avgHumid = avgHumid;
        this.highestHumid = highestHumid;
        this.lowestHumid = lowestHumid;

        this.avgLoud = avgLoud;
        this.highestLoud = highestLoud;
        this.lowestLoud = lowestLoud;

        this.rating = 0;
        this.ratingText = "<no rating>";
    }


    public void setRatingScore(int score) {
        rating = score;
    }

    public void setRatingText(String text) {
        ratingText = text;
    }

    public int getDuration() {
        long minutes = ChronoUnit.MINUTES.between(startDate, endDate);
        return (int) minutes;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public double getAvgLoud() {
        return avgLoud;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getAvgHumid() {
        return avgHumid;
    }

    public double getHighestTemp() {
        return highestTemp;
    }

    public double getLowestTemp() {
        return lowestTemp;
    }

    public double getHighestHumid() {
        return highestHumid;
    }

    public double getLowestHumid() {
        return lowestHumid;
    }

    public double getHighestLoud() {
        return highestLoud;
    }

    public double getLowestLoud() {
        return lowestLoud;
    }

    public int getMinutesPaused() {
        return minutesPaused;
    }

    public int getRating() {
        return rating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public String getUser() {
        return this.user;
    }


    // Methods used in GUI to display data
    public String getTemperature() {
        return "HI: " + highestTemp + " LO: " + lowestTemp + " AVG: " + avgTemp;
    }

    public String getHumidity() {
        return "HI: " + highestHumid + " LO: " + lowestHumid + " AVG: " + avgHumid;
    }

    public String getLoudness() {
        return "HI: " + highestLoud + " LO: " + lowestLoud + " AVG: " + avgLoud;
    }

    public String getFormattedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd" + System.lineSeparator() + "HH:mm");
        return startDate.format(formatter);
    }
    public String getFormattedEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd" + System.lineSeparator() + "HH:mm");
        return endDate.format(formatter);
    }

}
