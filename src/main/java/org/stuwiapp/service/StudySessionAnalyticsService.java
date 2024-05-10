package org.stuwiapp.service;

import org.stuwiapp.StudySession;
import org.stuwiapp.UserManager;
import org.stuwiapp.database.StudySessionDAO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudySessionAnalyticsService {


    String currentUser;

    List<Integer> totalStudyTimePerDay = new ArrayList<>(30);

    public StudySessionAnalyticsService() {
        this.currentUser = UserManager.getInstance().getCurrentUser();
        initListValues();
    }

    public List<Integer> calculateTotalStudyTimePerDay() {


        try {

            List<StudySession> studySessions = StudySessionDAO.getUserSessions(currentUser);

            for (StudySession session : studySessions) {
                LocalDate sessionDate = session.getStartDate().toLocalDate();
                LocalDate today = LocalDate.now();
                long daysAgo = ChronoUnit.DAYS.between(sessionDate, today);

                // accumulates time for specific day
                if (daysAgo >= 0 && daysAgo < 30) {
                    int dayIndex = (int) daysAgo;
                    int currentTotal = totalStudyTimePerDay.get(dayIndex);
                    totalStudyTimePerDay.set(dayIndex, currentTotal + session.getDuration());
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            Collections.reverse(totalStudyTimePerDay);
            return totalStudyTimePerDay;
        }

        return totalStudyTimePerDay;
    }
    
    public String totalStudiedInHoursAndMinutes() {
        int totalMinutes = 0;
        for(Integer duration : totalStudyTimePerDay) {
            totalMinutes = totalMinutes + duration;
        }
        int totalHours = totalMinutes / 60;
        int remainingMinutes = totalMinutes % 60;

        return String.format("%d hours %d minutes", totalHours, remainingMinutes);
    }

    public String averageRating() {
        try {
            List<StudySession> studySessions = StudySessionDAO.getUserSessions(currentUser);

            if (studySessions.isEmpty()) {
                return "0.0";
            }

            int totalRating = 0;
            int sessionCount = 0;

            for (StudySession session : studySessions) {
                LocalDate sessionDate = session.getStartDate().toLocalDate();
                LocalDate today = LocalDate.now();
                long daysAgo = ChronoUnit.DAYS.between(sessionDate, today);
                if (daysAgo >= 0 && daysAgo < 30) {
                    totalRating += session.getRating();
                    sessionCount++;
                }

            }
            if (sessionCount > 0) {
                return String.format("%.2f", ((double)totalRating / sessionCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0.0";
    }
    
    private void initListValues() {
        for (int i = 0; i < 30; i++) {
            totalStudyTimePerDay.add(0);
        }
    }

}