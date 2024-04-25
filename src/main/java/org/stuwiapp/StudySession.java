package org.stuwiapp;

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
    private Date sessionStart;
    private Date sessionEnd;
    private int rating;
    private String ratingText;

    public StudySession(UUID sessionId){
        this.startDate = LocalDateTime.now();
        this.endDate = null;
        this.tempData = new ArrayList<>();
        this.humidData = new ArrayList<>();
        this.loudData = new ArrayList<>();
        this.rating = 0;
        this.ratingText = "";
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

    public void addRatingScore(int score){
        rating = score;
    }

    public void addRatingText(String text){
        ratingText = text;
    }

    public void addEndDate(LocalDateTime end){
        this.endDate = end;
    }
















}
