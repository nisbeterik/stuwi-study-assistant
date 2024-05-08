package org.stuwiapp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.stuwiapp.StudySessionTemplate;
import org.stuwiapp.RangeSettingsTemplate;

import java.util.ArrayList;

public class LatestSettingsDAO {

    public static void saveLatestRangeSettings(RangeSettingsTemplate template, String user){
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("latestTemplates");

        JSONObject templateJson = new JSONObject();
        templateJson.put("user", user);
        templateJson.put("title", template.getTitle());
        templateJson.put("tempMax", template.getTempMax());
        templateJson.put("tempMin", template.getTempMin());
        templateJson.put("humidMax", template.getHumidMax());
        templateJson.put("humidMin", template.getHumidMin());
        templateJson.put("loudMax", template.getLoudMax());

        Document sessionAsDoc = Document.parse(templateJson.toString());
        collection.insertOne(sessionAsDoc);
    }
}
