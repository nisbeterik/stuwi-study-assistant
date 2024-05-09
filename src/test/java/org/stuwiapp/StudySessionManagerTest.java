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
}
