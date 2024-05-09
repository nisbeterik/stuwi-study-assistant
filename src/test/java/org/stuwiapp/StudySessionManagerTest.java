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
}
