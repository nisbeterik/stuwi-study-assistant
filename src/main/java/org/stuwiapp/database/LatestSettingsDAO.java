package org.stuwiapp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;
import org.stuwiapp.StudySessionTemplate;
import org.stuwiapp.RangeSettingsTemplate;

import java.util.ArrayList;

public class LatestSettingsDAO {

    private static MongoClient client = MongoConnectionManager.getMongoClient();
    private static MongoDatabase db = client.getDatabase("stuwi");
    private static MongoCollection<Document> collection = db.getCollection("latestTemplates");

    public static void saveLatestRangeSettings(RangeSettingsTemplate template, String user){


        if (collection.countDocuments() > 0) {
            Bson filter = Filters.eq("title", "LATESTRANGES");
            collection.deleteOne(filter);
        }

        JSONObject templateJson = new JSONObject();
        templateJson.put("user", user);
        templateJson.put("title", "LATESTRANGES");
        templateJson.put("tempMax", template.getTempMax());
        templateJson.put("tempMin", template.getTempMin());
        templateJson.put("humidMax", template.getHumidMax());
        templateJson.put("humidMin", template.getHumidMin());
        templateJson.put("loudMax", template.getLoudMax());

        Document sessionAsDoc = Document.parse(templateJson.toString());
        collection.insertOne(sessionAsDoc);
    }
}
