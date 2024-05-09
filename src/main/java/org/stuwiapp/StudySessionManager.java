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
    private boolean sessionPaused = false;
    private LocalDateTime sessionStart;
    private LocalDateTime pauseStart;
    private int minutesPaused = 0;
    private StudySessionTemplate currentTemplate;
    private final ArrayList<Double> tempData = new ArrayList<>();
    private final ArrayList<Double> humidData = new ArrayList<>();
    private static final ArrayList<Double> loudData = new ArrayList<>();


    public static StudySessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new StudySessionManager();
        }
        return sessionManager;
    }

    public void startSession() {
        if (!sessionActive) {
            sessionStart = LocalDateTime.now();

            sessionActive = true;
            System.out.println("New session started");
        }
    }

    // Store information about the session so that it can be saved in the database later
    public void setCurrentTemplate(StudySessionTemplate template) {
        currentTemplate = template;
        System.out.println("Template with subject " + template.getSubject() + " set for session");
    }

    public void endSession() {
        if (sessionActive) {
            LocalDateTime sessionEnd = LocalDateTime.now();
            String user = UserManager.getInstance().getCurrentUser();

            StudySession session = new StudySession(sessionStart, sessionEnd, user, minutesPaused,
                    calculateAvg(tempData), findHighest(tempData), findLowest(tempData),
                    calculateAvg(humidData), findHighest(humidData), findLowest(humidData),
                    calculateAvg(loudData), findHighest(loudData), findLowest(loudData), currentTemplate);

            // TODO: Make this better. We do not want to call controller methods.
            // Can we make endSession() be called inside popup method? What happens if session is ended on terminal?
            // Also, we might not want to depend on JavaFx import "Pair" here.

            Pair<Integer, String> ratingData = DashboardController.showFeedbackPopup();
            session.setRatingScore(ratingData.getKey());
            session.setRatingText(ratingData.getValue());

            StudySessionDAO.saveSessionInDatabase(session);

            tempData.clear();
            humidData.clear();
            loudData.clear();
            minutesPaused = 0;
            currentTemplate = null;

            sessionActive = false;
            System.out.println("Session stopped");

        }
    }

    public void pauseSession() {
        sessionPaused = true;
        pauseStart = LocalDateTime.now();
    }

    public void unpauseSession() {
        sessionPaused = false;
        LocalDateTime pauseEnd = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(pauseStart, pauseEnd);
        minutesPaused += (int) minutes;
    }

    public boolean isSessionActive() {
        return sessionActive;
    }


    // Converting from Strings to Double because payload comes as strings from sensors/terminal
    public void addTemperatureData(String tempDataString) {
        try {
            Double tempDataDouble = Double.parseDouble(tempDataString);
            if (!tempDataDouble.isNaN()) {
                tempData.add(tempDataDouble);
            } else {
                System.out.println("Invalid temperature 'NaN'");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid value");
        }


    }

    public void addHumidityData(String humidDataString) {
        Double humidDataDouble = Double.parseDouble(humidDataString);
        humidData.add(humidDataDouble);
    }

    public void addLoudnessData(String loudDataString) {
        Double loudDataDouble = Double.parseDouble(loudDataString);
        loudData.add(loudDataDouble);
    }


    // Helper methods for calculating average, highest and lowest values from sensor data
    private int calculateAvg(ArrayList<Double> values) {
        double total = 0;
        for (Double value : values) {
            total += value;
        }
        double average = total / values.size();
        System.out.println(average);

        return (int) Math.round(average);
    }

    private int findHighest(ArrayList<Double> values) {
        if (values.isEmpty()) {
            return 0;
        }
        double highest = values.get(0);
        for (double value : values) {
            if (value > highest) {
                highest = value;
            }
        }
        return (int) Math.round(highest);
    }

    private int findLowest(ArrayList<Double> values) {
        if (values.isEmpty()) {
            return 0;
        }
        double lowest = values.get(0);
        for (double value : values) {
            if (value < lowest) {
                lowest = value;
            }
        }
        return (int) Math.round(lowest);
    }

    public ArrayList<Double> getTemperatureDataList() {
        return tempData;
    }

    public ArrayList<Double> getHumidityDataList() {
        return humidData;
    }
}
