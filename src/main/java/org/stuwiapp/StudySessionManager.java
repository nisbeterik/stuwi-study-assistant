package org.stuwiapp;

import java.util.UUID;

public class StudySessionManager {
    private static StudySessionManager sessionManager;
    private StudySession currentSession;

    public static StudySessionManager getInstance() {
        if (sessionManager == null) {
            sessionManager = new StudySessionManager();
        }
        return sessionManager;
    }

    public void startSession() {
        UUID sessionId = UUID.randomUUID();
        currentSession = new StudySession(sessionId);
    }

    public void endSession() {


        // Implementation of saving sessions here


        currentSession = null;
    }

    public StudySession getCurrentSession() {
        return currentSession;
    }

    public boolean isSessionActive() {
        return currentSession != null;
    }


}
