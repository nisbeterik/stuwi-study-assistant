package org.stuwiapp;

public class StudySessionTemplate {
    private String title;
    private String subject;
    private int blockDuration;
    private int blocks;
    private int breakDuration;
    final static int MIN_BREAK_INTERVAL = 5;
    final static int MAX_DUR_NO_BREAK = 60;

    public StudySessionTemplate(String title, String subject, int blockDuration, int breakDuration, int blocks) throws Exception{
        if (blocks > 1){
            if (breakDuration < 5){
                throw new Exception("At least 5 min breaks must be set for study sessions with more than 1 block.");
            }
        }
        this.title = title;
        this.subject = subject;
        this.blockDuration = blockDuration;
        this.breakDuration = breakDuration;
        this.blocks = blocks;
    }

    public int getBlocks() {
        //returns break interval in minutes
        return blocks;
    }
    public int getBreakDuration(){
        //returns break interval in minutes
        return breakDuration;
    }
    public int getDuration() {
        //returns duration in minutes
        return blockDuration;
    }
    public String getSubject(){
        return subject;
    }

    @Override
    public String toString(){
        return title;
    }

}
