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

import java.util.ArrayList;

public class StudySessionDAO {

    public static void saveSessionInDatabase(StudySession session){

        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("sessions");

        JSONObject studySessionJson = new JSONObject();
        studySessionJson.put("user", session.getUser());
        studySessionJson.put("start_date", session.getStartDate()); // Saving as strings to avoid database issues
        studySessionJson.put("end_date", session.getEndDate());
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

        // Rating info
        studySessionJson.put("rating", session.getRating());
        studySessionJson.put("rating_text", session.getRatingText());

        Document sessionAsDoc = Document.parse(studySessionJson.toString());
        collection.insertOne(sessionAsDoc);

    }


    public static ArrayList<StudySession> getUserSessions(String username){
    ArrayList<StudySession> sessions = new ArrayList<>();
    MongoClient client = MongoConnectionManager.getMongoClient();
    MongoDatabase db = client.getDatabase("stuwi");
    MongoCollection<Document> collection = db.getCollection("sessions");

    Document query = new Document("user", username);
    MongoCursor<Document> cursor = collection.find(query).iterator();

    while (cursor.hasNext()) {
        Document doc = cursor.next();
        StudySession session = new StudySession(doc);
        sessions.add(session);
    }
    return sessions;
    }



}
