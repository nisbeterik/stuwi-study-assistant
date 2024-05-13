package org.stuwiapp;

import javafx.event.ActionEvent;
import org.stuwiapp.controller.DashboardController;
import org.stuwiapp.database.LatestSettingsDAO;

public class RangeSettingsTemplate {
    private String id;
    private String title;
    private int tempMax;
    private int tempMin;
    private int humidMax;
    private int humidMin;
    private int loudMax;

    MQTTManager mqttManager = MQTTManagerSingleton.getMqttInstance();
    private final String publicRangeDataTopic = "stuwi/rangeupdate";

    public RangeSettingsTemplate(String id, String title, int tempMax, int tempMin, int humidMax, int humidMin, int loudMax){
        this.id = id;
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
    public String getId(){return id;}


    @Override
    public String toString(){
        return title;
    }

    public void publishRangeSettings() throws Exception{

            //Starts a study session with current setting
            String payload = String.format("%d %d %d %d %d", this.getTempMax(), this.getTempMin(), this.getHumidMax(), this.getHumidMin(), this.getLoudMax());
            mqttManager.publish(publicRangeDataTopic, payload);

        DashboardController.setRanges(this.getTempMax(), this.getTempMin(), this.getHumidMax(), this.getHumidMin(), this.getLoudMax());

    }

}


