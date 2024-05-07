package org.stuwiapp;

public class RangeSettingsTemplate {

    private String title;
    private int tempMax;
    private int tempMin;

    private int humidMax;
    private int humidMin;


    private int loudMax;

    public RangeSettingsTemplate(String title, int tempMax, int tempMin, int humidMax, int humidMin, int loudMax){
        this.title = title;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.humidMax = humidMax;
        this.humidMin = humidMin;
        this.loudMax = loudMax;
    }
    public String getTitle() {
        return title;
    }
    public int getTempMax() {
        return tempMax;
    }
    public int getTempMin() {
        return tempMin;
    }
    public int getHumidMax() {
        return humidMax;
    }
    public int getHumidMin() {
        return humidMin;
    }
    public int getLoudMax() {
        return loudMax;
    }


    @Override
    public String toString(){
        return title;
    }

}


