package org.stuwiapp;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class StudySession {
    private UUID sessionId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList<Double> tempData;
    private ArrayList<Double> humidData;
    private ArrayList<Double> loudData;
    private int rating;
    private String ratingText;
    private int minutesPaused;

    // private StudySessionTemplate template;

    public StudySession(UUID sessionId){
        this.sessionId = sessionId;
        this.startDate = LocalDateTime.now();
        this.endDate = null;
        this.minutesPaused = 0;
        this.tempData = new ArrayList<>();
        this.humidData = new ArrayList<>();
        this.loudData = new ArrayList<>();
        this.rating = 0;
        this.ratingText = "";

        // this.template = template;

    }

    // Converting from Strings to Double because payload comes as strings from sensors/terminal
    public void addTemperatureData(String tempDataString){
        Double tempDataDouble = Double.parseDouble(tempDataString);
        tempData.add(tempDataDouble);
    }

    public void addHumidityData(String humidDataString){
        Double humidDataDouble = Double.parseDouble(humidDataString);
        humidData.add(humidDataDouble);
    }

    public void addLoudnessData(String loudDataString){
        Double loudDataDouble = Double.parseDouble(loudDataString);
        loudData.add(loudDataDouble);
    }

    public void setRatingScore(int score){
        rating = score;
    }

    public void setRatingText(String text){
        ratingText = text;
    }

    public void setEndDate(LocalDateTime end){
        this.endDate = end;
    }

    public void addMinutesPaused(int minutes){
        minutesPaused += minutes;
    }

    public int getDuration(){

        // TODO Calculate duration from startDate and endDate --OR-- Gather it from the template composition

        return 0;
    }

    public UUID getSessionId() {
        return sessionId;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public double getHighestTemp(){
        return findHighest(tempData);
    }
    public double getLowestTemp(){
        return findLowest(tempData);
    }
    public double getAvgTemp(){return calculateAvg(tempData);}

    public double getHighestHumid(){return findHighest(humidData);}

    public double getLowestHumid(){
        return findLowest(humidData);
    }
    public double getAvgHumid(){
        return calculateAvg(humidData);
    }
    public double getHighestLoud(){
        return findHighest(loudData);
    }
    public double getLowestLoud(){
        return findLowest(loudData);
    }
    public double getAvgLoud(){
        return calculateAvg(loudData);
    }
    public int getMinutesPaused() {return minutesPaused;}
    public int getRating() {return rating;}
    public String getRatingText() {return ratingText;}

    private double calculateAvg(ArrayList<Double> values){
        double total = 0;
        for (Double value : values) {
            total += value;
        }
        double average = total / values.size();
        System.out.println(average);

        // TODO Make this return with only two decimals and make sure it can be parsed by getters without errors
        return average;
    }

    private double findHighest(ArrayList<Double> values){
        double highest = values.get(0);
        for (double value : values){
            if (value > highest){
                highest = value;
            }
        }
        return highest;
    }

    private double findLowest(ArrayList<Double> values){
        double lowest = values.get(0);
        for (double value : values){
            if (value < lowest){
                lowest = value;
            }
        }
        return lowest;
    }

}
