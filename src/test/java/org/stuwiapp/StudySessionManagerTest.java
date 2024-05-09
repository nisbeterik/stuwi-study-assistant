package org.stuwiapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class StudySessionManagerTest {

    StudySessionManager m;

    String normalData = "10";
    String negativeData = "-10";
    String notANumber = "NaN";


    @BeforeEach
    void setup() {
        m = new StudySessionManager();
    }


    /***
     * addTemperatureData() tests
     */

    @Test
    void addTemperatureData_Normal() {
        m.addTemperatureData(normalData);
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(10.0);
        assertEquals(expected, m.getTemperatureDataList());
    }

    @Test
    void addTemperatureData_Negative(){
        m.addTemperatureData(negativeData);
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(-10.0);
        assertEquals(expected, m.getTemperatureDataList());
    }
    @Test
    void addTemperatureData_OutOfRange() {
        // DHT11 has a range of -20C - 60C
        String tooLow = "-21";
        String tooHigh = "61";
        m.addTemperatureData(tooHigh);
        m.addTemperatureData(tooLow);
        ArrayList<Double> expected = new ArrayList<>();
        //should be empty
        assertEquals(expected, m.getTemperatureDataList());

    }
    @Test
    void addTemperatureData_NaN() {
        m.addTemperatureData(notANumber);
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getTemperatureDataList());
    }

    @Test
    void addTemperatureData_Empty() {
        String empty = "";
        m.addTemperatureData(empty); // caught in try-catch
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getTemperatureDataList());
    }

    @Test
    void addTemperatureData_WrongFormat() {
        String wrong = "10 C";
        m.addTemperatureData(wrong); // caught in try-catch
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getTemperatureDataList());
    }

    /***
     * addHumidityData() tests
     */

    @Test
    void addHumidityData_Normal() {
        m.addHumidityData(normalData);
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(10.0);
        assertEquals(expected, m.getHumidityDataList());
    }

    @Test
    void addHumidityData_Negative(){
        m.addHumidityData(negativeData);
        ArrayList<Double> expected = new ArrayList<>();
        // you cant have negative humidity so should be empty
        assertEquals(expected, m.getHumidityDataList());
    }

    @Test
    void addHumidityData_OverHundred() {
        String over = "101";
        m.addHumidityData(over);
        ArrayList<Double> expected = new ArrayList<>();
        // you cant have humidity over 100%, should be empty
        assertEquals(expected, m.getHumidityDataList());
    }

    @Test
    void addHumidityData_NaN() {
        m.addHumidityData(notANumber);
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getHumidityDataList());
    }

    @Test
    void addHumidityData_Empty() {
        String empty = "";
        m.addHumidityData(empty);
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getHumidityDataList());
    }

    @Test
    void addHumidityData_WrongFormat() {
        String wrong = "70 %";
        m.addHumidityData(wrong);
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getHumidityDataList());
    }

    /***
     * addLoudnessData() tests
     */
    @Test
    void addLoudnessData_Normal() {
        m.addLoudnessData(normalData);
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(10.0);
        assertEquals(expected, m.getLoudnessDataList());
    }

    @Test
    void addLoudnessData_Negative(){
        m.addLoudnessData(negativeData);
        ArrayList<Double> expected = new ArrayList<>();
        // Loudness cannot be negative, should result in an empty list
        assertEquals(expected, m.getLoudnessDataList());
    }

    @Test
    void addLoudnessData_OverHundred() {
        m.addLoudnessData(overHundredData);
        ArrayList<Double> expected = new ArrayList<>();
        // Loudness cannot exceed 100, should result in an empty list
        assertEquals(expected, m.getLoudnessDataList());
    }

    @Test
    void addLoudnessData_NaN() {
        m.addLoudnessData(notANumber);
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getLoudnessDataList());
    }

    @Test
    void addLoudnessData_Empty() {
        String empty = "";
        m.addLoudnessData(empty);
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getLoudnessDataList());
    }

    @Test
    void addLoudnessData_WrongFormat() {
        String wrong = "85 dB";
        m.addLoudnessData(wrong);
        ArrayList<Double> expected = new ArrayList<>();
        assertEquals(expected, m.getLoudnessDataList());
    }
}
