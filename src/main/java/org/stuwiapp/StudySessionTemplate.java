package org.stuwiapp;

public class StudySessionTemplate {
    private String subject;
    private int duration;
    private int breakInterval;
    private int breakDuration;
    final static int MINUTE_MULTIPLIER = 60;
    final static int MAX_BREAK_INTERVAL = 60;
    final static int MAX_DUR_NO_BREAK = 60;

    public StudySessionTemplate(String subject, int durationMinutes, int breakIntervalMinutes, int breakDuration) throws Exception{
        if (breakIntervalMinutes > durationMinutes){
            throw new Exception("Break interval longer than study sessions total duration");
        } if (breakIntervalMinutes > MAX_BREAK_INTERVAL) {
            throw new Exception("Break interval longer than max break interval of 60 minutes");
        } if (durationMinutes > MAX_DUR_NO_BREAK){
            if (breakIntervalMinutes == 0){
                throw new Exception("breaks must be set for study sessions longer than 60 minutes");
            }
        }
        this.subject = subject;
        this.duration = durationMinutes * MINUTE_MULTIPLIER;
        this.breakInterval = breakIntervalMinutes * MINUTE_MULTIPLIER;
    }

    public int getBreakInterval() {
        //returns break interval in seconds
        return breakInterval;
    }
    public int getDuration() {
        //returns duration in seconds
        return duration;
    }
    public String getSubject(){
        return subject;
    }

}
