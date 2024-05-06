package org.stuwiapp;

import org.bson.Document;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class StudySession {

    // For StudySessionManager constructor
    private String startDate;
    private String endDate;
    private ArrayList<Double> tempData;
    private ArrayList<Double> humidData;
    private ArrayList<Double> loudData;
    private int rating;
    private String ratingText;
    private int minutesPaused;
    private String user;

    // For document based constructor
    private double avgLoud;
    private double avgTemp;
    private double avgHumid;
    private double highestTemp;
    private double lowestTemp;
    private double highestHumid;
    private double lowestHumid;
    private double highestLoud;
    private double lowestLoud;

    public StudySession(LocalDateTime startDate){
        this.startDate = startDate.toString();
        this.user = UserManager.getInstance().getCurrentUser();
        this.endDate = null;
        this.minutesPaused = 0;
        this.tempData = new ArrayList<>();
        this.humidData = new ArrayList<>();
        this.loudData = new ArrayList<>();
        this.rating = 0;
        this.ratingText = "";
    }

    public StudySession(Document doc) {
        this.startDate = doc.getString("start_date");
        this.endDate = doc.getString("end_date");
        this.rating = doc.getInteger("rating");
        this.ratingText = doc.getString("rating_text");
        this.minutesPaused = doc.getInteger("minutesPaused");
        this.user = doc.getString("user");

        Document tempDataDoc = (Document) doc.get("temperature");
        this.avgTemp = tempDataDoc.getDouble("average");
        this.highestTemp = tempDataDoc.getDouble("highest");
        this.lowestTemp = tempDataDoc.getDouble("lowest");

        Document humidDataDoc = (Document) doc.get("humidity");
        this.avgHumid = humidDataDoc.getDouble("average");
        this.highestHumid = humidDataDoc.getDouble("highest");
        this.lowestHumid = humidDataDoc.getDouble("lowest");

        Document loudDataDoc = (Document) doc.get("loudness");
        this.avgLoud = loudDataDoc.getDouble("average");
        this.highestLoud = loudDataDoc.getDouble("highest");
        this.lowestLoud = loudDataDoc.getDouble("lowest");
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
        this.endDate = end.toString();
    }

    public void addMinutesPaused(int minutes){
        minutesPaused += minutes;
    }

    public int getDuration(){

        // TODO Calculate duration from startDate and endDate

        return 0;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
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
    public String getUser(){return this.user;}

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
        if (values.isEmpty()){
            return 0;
        }
        double highest = values.get(0);
        for (double value : values){
            if (value > highest){
                highest = value;
            }
        }
        return highest;
    }

    private double findLowest(ArrayList<Double> values){
        if (values.isEmpty()){
            return 0;
        }
        double lowest = values.get(0);
        for (double value : values){
            if (value < lowest){
                lowest = value;
            }
        }
        return lowest;
    }

}
