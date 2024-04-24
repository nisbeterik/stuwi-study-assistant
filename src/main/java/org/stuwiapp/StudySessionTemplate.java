package org.stuwiapp;

public class StudySessionTemplate {
    private String title;
    private String subject;
    private int duration;
    private int breakInterval;
    private int breakDuration;
    final static int MINUTE_MULTIPLIER = 60;
    final static int MAX_BREAK_INTERVAL = 60;
    final static int MAX_DUR_NO_BREAK = 60;

    public StudySessionTemplate(String title, String subject, int durationMinutes, int breakIntervalMinutes, int breakDuration) throws Exception{
        if (breakIntervalMinutes > durationMinutes){
            throw new Exception("Break interval longer than study sessions total duration");
        } if (breakIntervalMinutes > MAX_BREAK_INTERVAL) {
            throw new Exception("Break interval longer than max break interval of 60 minutes");
        } if (durationMinutes > MAX_DUR_NO_BREAK){
            if (breakIntervalMinutes == 0){
                throw new Exception("breaks must be set for study sessions longer than 60 minutes");
            }
        }
        this.title = title;
        this.subject = subject;
        this.duration = durationMinutes;
        this.breakInterval = breakIntervalMinutes;
        this.breakDuration = breakDuration;
    }

    public int getBreakInterval() {
        //returns break interval in minutes
        return breakInterval;
    }
    public int getBreakDuration(){
        //returns break interval in minutes
        return breakDuration;
    }
    public int getDuration() {
        //returns duration in minutes
        return duration;
    }
    public String getSubject(){
        return subject;
    }

    @Override
    public String toString(){
        return title;
    }

}
