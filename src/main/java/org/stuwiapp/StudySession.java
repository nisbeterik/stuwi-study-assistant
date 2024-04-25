package org.stuwiapp;

public class StudySession {

    private static StudySession studySession;
    private boolean sessionActive;

    public static synchronized StudySession getInstance(){
        if (studySession == null){
            studySession = new StudySession();
        }
        return  studySession;
    }

    public void startSession(){
        sessionActive = true;
    }

    public void endSession(){
        sessionActive = false;
    }

    public boolean isStudySessionActive(){
        return sessionActive;
    }















}
