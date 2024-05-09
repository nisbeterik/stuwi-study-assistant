package org.stuwiapp;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class StudySession {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private int rating;
    private String ratingText;
    private final String user;
    private final int avgLoud;
    private final int avgTemp;
    private final int avgHumid;
    private final int highestTemp;
    private final int lowestTemp;
    private final int highestHumid;
    private final int lowestHumid;
    private final int highestLoud;
    private final int lowestLoud;
    private final StudySessionTemplate template;

    public StudySession(LocalDateTime startDate, LocalDateTime endDate, String user, int avgTemp,
                        int highestTemp, int lowestTemp, int avgHumid, int highestHumid,
                        int lowestHumid, int avgLoud, int highestLoud, int lowestLoud, StudySessionTemplate template) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;

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

        this.template = template;
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

    // Getters for all fields
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

    public int getRating() {
        return rating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public String getUser() {
        return this.user;
    }

    public int getTemplateDuration() {
        return template.getDuration();
    }

    public int getTemplateBreakDuration() {
        return template.getBreakDuration();
    }

    public int getTemplateBlocks() {
        return template.getBlocks();
    }

    public String getTemplateSubject() {
        return template.getSubject();
    }


    // Methods used in GUI to display data
    public String getTemperature() {
        return lowestTemp + " / " + highestTemp + " / " + avgTemp;
    }

    public String getHumidity() {
        return lowestHumid + " / " + highestHumid + " / " + avgHumid;
    }

    public String getLoudness() {
        return lowestLoud + " / " + highestLoud + " / " + avgLoud;
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
