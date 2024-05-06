package org.stuwiapp;

import org.stuwiapp.database.StudySessionDAO;

import java.time.LocalDateTime;
import java.util.UUID;

public class StudySessionManager {
    private static StudySessionManager sessionManager;
    private StudySession currentSession;
    private boolean sessionActive = false;
    private LocalDateTime pauseStart;

    public static StudySessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new StudySessionManager();
        }
        return sessionManager;
    }

    public void startSession() {
        if (!sessionActive){
            currentSession = new StudySession(LocalDateTime.now());
            sessionActive = true;
            System.out.println("New session started");
        }
    }

    public void endSession() {

        if (sessionActive){
            sessionActive = false;
            System.out.println("Session stopped");
            currentSession.setEndDate(LocalDateTime.now());

            // TODO Implementation for adding ratings from prompting user here
            // TODO Make sure session is valid before saving (can be done in StudySessionDAO)

            StudySessionDAO.saveSessionInDatabase(currentSession);

            currentSession = null;
        }


    }

    public void pauseSession(){
        pauseStart = LocalDateTime.now();
    }

    public void unpauseSession(){
        LocalDateTime pauseEnd = LocalDateTime.now();

        // TODO Calculate minutes paused here
        // TODO Need to handle case where it's never unpaused

        int minutes = 0; // Placeholder zero

        currentSession.addMinutesPaused(minutes);

    }


    public StudySession getCurrentSession() {
        return currentSession;
    }

    public boolean isSessionActive() {
        return sessionActive;
    }


}
