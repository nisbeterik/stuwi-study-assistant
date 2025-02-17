package org.stuwiapp;

import javafx.util.Pair;
import org.stuwiapp.controller.DashboardController;
import org.stuwiapp.database.StudySessionDAO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class StudySessionManager {
    private static StudySessionManager sessionManager;
    private boolean sessionActive = false;
    private LocalDateTime sessionStart;
    private StudySessionTemplate currentTemplate;
    private final ArrayList<Double> tempData = new ArrayList<>();
    private final ArrayList<Double> humidData = new ArrayList<>();
    private final ArrayList<Double> loudData = new ArrayList<>();


    public static StudySessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new StudySessionManager();
        }
        return sessionManager;
    }

    public void startSession() {
        if (!sessionActive){
            sessionStart = LocalDateTime.now();

            sessionActive = true;
            System.out.println("New session started");
        }
    }

    // Store information about the session so that it can be saved in the database later
    public void setCurrentTemplate(StudySessionTemplate template){
        currentTemplate = template;
        System.out.println("Template with subject " + template.getSubject() + " set for session");
    }

    public void endSession() {
        if (sessionActive){
            LocalDateTime sessionEnd = LocalDateTime.now();
            String user = UserManager.getInstance().getCurrentUser();

            StudySession session = new StudySession(sessionStart, sessionEnd, user,
                    calculateAvg(tempData), findHighest(tempData), findLowest(tempData),
                    calculateAvg(humidData), findHighest(humidData), findLowest(humidData),
                    calculateAvg(loudData), findHighest(loudData), findLowest(loudData), currentTemplate);

            tempData.clear();
            humidData.clear();
            loudData.clear();
            currentTemplate = null;
            sessionActive = false;

            // It is not good practice to call Controller methods from a different class, but we made an exception here
            // Since it was the least convoluted way to get the feedback from the user

            Pair<Integer, String> ratingData = DashboardController.showFeedbackPopup();
            if (ratingData == null) {
                // User closed the popup without providing feedback
                System.out.println("Session ended but was not saved");
                return;
            }
            session.setRatingScore(ratingData.getKey());
            session.setRatingText(ratingData.getValue());
            StudySessionDAO.saveSessionInDatabase(session);
            System.out.println("Session ended and saved in database");


        }
    }

    public boolean isSessionActive() {
        return sessionActive;
    }


    // Converting from Strings to Double because payload comes as strings from sensors/terminal
    public void addTemperatureData(String tempDataString) {
        try {
            Double tempDataDouble = Double.parseDouble(tempDataString);
            if (!tempDataDouble.isNaN() && tempDataDouble >= -20 && tempDataDouble <= 60) {
                tempData.add(tempDataDouble);
            } else {
                System.out.println("Invalid temperature");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid value");
        }


    }

    public void addHumidityData(String humidDataString) {
        try{
            Double humidDataDouble = Double.parseDouble(humidDataString);
            if (!humidDataDouble.isNaN() && humidDataDouble >= 0 && humidDataDouble <= 100) {
                humidData.add(humidDataDouble);
            } else {
                System.out.println("Invalid humidity");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid value");
        }


    }

    public void addLoudnessData(String loudDataString) {
        try{
            Double loudDataDouble = Double.parseDouble(loudDataString);
            if(!loudDataDouble.isNaN() && loudDataDouble >= 0 && loudDataDouble <= 100) {
                loudData.add(loudDataDouble);
            } else {
                System.out.println("Invalid loudness");
            }

        } catch(NumberFormatException e) {
            System.out.println("Invalid value");
        }

    }


    // Helper methods for calculating average, highest and lowest values from sensor data
    private int calculateAvg(ArrayList<Double> values){
        double total = 0;
        for (Double value : values) {
            total += value;
        }
        double average = total / values.size();
        System.out.println(average);

        return (int) Math.round(average);
    }

    private int findHighest(ArrayList<Double> values){
        if (values.isEmpty()){
            return 0;
        }
        double highest = values.get(0);
        for (double value : values){
            if (value > highest){
                highest = value;
            }
        }
        return (int) Math.round(highest);
    }

    private int findLowest(ArrayList<Double> values){
        if (values.isEmpty()){
            return 0;
        }
        double lowest = values.get(0);
        for (double value : values){
            if (value < lowest){
                lowest = value;
            }
        }
        return (int)Math.round(lowest);
    }

    public ArrayList<Double> getTemperatureDataList() {
        return tempData;
    }

    public ArrayList<Double> getHumidityDataList() {
        return humidData;
    }

    public ArrayList<Double> getLoudnessDataList() {
        return loudData;
    }
}
