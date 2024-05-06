package org.stuwiapp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.stuwiapp.StudySession;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import com.mongodb.client.MongoCursor;
import org.stuwiapp.StudySessionTemplate;

import java.util.ArrayList;

public class StudySessionDAO {

    public static void saveSessionInDatabase(StudySession session){

        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("sessions");

        JSONObject studySessionJson = new JSONObject();
        studySessionJson.put("user", session.getUser());
        studySessionJson.put("start_date", session.getStartDate().toString());
        studySessionJson.put("end_date", session.getEndDate().toString());
        studySessionJson.put("duration", session.getDuration());
        studySessionJson.put("minutesPaused", session.getMinutesPaused());

        // Temperature data
        JSONObject temperatureJson = new JSONObject();
        temperatureJson.put("highest", session.getHighestTemp());
        temperatureJson.put("lowest", session.getLowestTemp());
        temperatureJson.put("average", session.getAvgTemp());
        studySessionJson.put("temperature", temperatureJson);

        // Humidity data
        JSONObject humidityJson = new JSONObject();
        humidityJson.put("highest", session.getHighestHumid());
        humidityJson.put("lowest", session.getLowestHumid());
        humidityJson.put("average", session.getAvgHumid());
        studySessionJson.put("humidity", humidityJson);

        // Loudness data
        JSONObject loudnessJson = new JSONObject();
        loudnessJson.put("highest", session.getHighestLoud());
        loudnessJson.put("lowest", session.getLowestLoud());
        loudnessJson.put("average", session.getAvgLoud());
        studySessionJson.put("loudness", loudnessJson);

        // Template info
        JSONObject templateJson = new JSONObject();
        templateJson.put("subject", session.getTemplateSubject());
        templateJson.put("duration", session.getTemplateDuration());
        templateJson.put("breakDuration", session.getTemplateBreakDuration());
        templateJson.put("blocks", session.getTemplateBlocks());
        studySessionJson.put("template", templateJson);

        // Rating info
        studySessionJson.put("rating", session.getRating());
        studySessionJson.put("rating_text", session.getRatingText());

        Document sessionAsDoc = Document.parse(studySessionJson.toString());
        collection.insertOne(sessionAsDoc);

    }


    public static ArrayList<StudySession> getUserSessions(String username) throws Exception {
        ArrayList<StudySession> sessions = new ArrayList<>();
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("sessions");

        Document query = new Document("user", username);
        MongoCursor<Document> cursor = collection.find(query).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();

            Document tempDataDoc = (Document) doc.get("temperature");
            Document humidDataDoc = (Document) doc.get("humidity");
            Document loudDataDoc = (Document) doc.get("loudness");
            Document templateDoc = (Document) doc.get("template");

            StudySessionTemplate template = new StudySessionTemplate(
                    null,
                    templateDoc.getString("subject"),
                    templateDoc.getInteger("duration"),
                    templateDoc.getInteger("breakDuration"),
                    templateDoc.getInteger("blocks")
            );

            StudySession session = new StudySession(
                    LocalDateTime.parse(doc.getString("start_date")),
                    LocalDateTime.parse(doc.getString("end_date")),
                    doc.getString("user"),
                    doc.getInteger("minutesPaused"),
                    tempDataDoc.getInteger("average"),
                    tempDataDoc.getInteger("highest"),
                    tempDataDoc.getInteger("lowest"),
                    humidDataDoc.getInteger("average"),
                    humidDataDoc.getInteger("highest"),
                    humidDataDoc.getInteger("lowest"),
                    loudDataDoc.getInteger("average"),
                    loudDataDoc.getInteger("highest"),
                    loudDataDoc.getInteger("lowest"),
                    template
            );

            session.setRatingScore(doc.getInteger("rating"));
            session.setRatingText(doc.getString("rating_text"));

            sessions.add(session);
        }
        return sessions;
    }



}
