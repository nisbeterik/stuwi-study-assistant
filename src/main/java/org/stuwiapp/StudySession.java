package org.stuwiapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class StudySession {
    private UUID sessionId;
    private ArrayList<Double> tempData;
    private ArrayList<Double> humidData;
    private ArrayList<Double> loudData;
    private Date sessionStart;
    private Date sessionEnd;
    private int rating;
    private String ratingText;

    public StudySession(UUID sessionId){
        this.tempData = new ArrayList<>();
        this.humidData = new ArrayList<>();
        this.loudData = new ArrayList<>();
        this.rating = 0;
        this.ratingText = "";
    }

    public void addTemperatureData(double tempDataPoint){
        tempData.add(tempDataPoint);
    }

    public void addHumidityData(double humidDataPoint){
        humidData.add(humidDataPoint);

    }

    public void addLoudnessData(double loudDataPoint){
        loudData.add(loudDataPoint);
    }

    public void addRatingScore(int score){
        rating = score;
    }

    public void addRatingText(String text){
        ratingText = text;
    }
















}
