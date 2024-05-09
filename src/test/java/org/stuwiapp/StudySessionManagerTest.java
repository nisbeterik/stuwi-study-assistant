package org.stuwiapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


}
