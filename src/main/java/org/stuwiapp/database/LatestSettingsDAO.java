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
        templateJson.put("_id", template.getId());
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
    public static void saveLatestStudyTemplateInDatabase(StudySessionTemplate template, String user){

        if (collection.countDocuments() > 0) {
            Bson filter = Filters.eq("title", "LATESTSESSION");
            collection.deleteOne(filter);
        }

        JSONObject templateJson = new JSONObject();
        templateJson.put("_id", template.getId());
        templateJson.put("user", user);
        templateJson.put("title", "LATESTSESSION");
        templateJson.put("subject", template.getSubject());
        templateJson.put("blockDuration", template.getDuration());
        templateJson.put("breakDuration", template.getBreakDuration());
        templateJson.put("blocks", template.getBlocks());

        Document sessionAsDoc = Document.parse(templateJson.toString());
        collection.insertOne(sessionAsDoc);
    }
    public static StudySessionTemplate getLatestStudyTemplate(String user){

        for (Document doc : collection.find(new Document("title", "LATESTSESSION"))) {
            String id = doc.getString("_id");
            String title = doc.getString("title");
            String subject = doc.getString("subject");
            int blockDuration = doc.getInteger("blockDuration");
            int breakDuration = doc.getInteger("breakDuration");
            int blocks = doc.getInteger("blocks");

            try {
                return new StudySessionTemplate(id, title, subject, blockDuration, breakDuration, blocks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static RangeSettingsTemplate getLatestRangeTemplate(String user){

        for (Document doc : collection.find(new Document("title", "LATESTRANGES"))){
            String id = doc.getString("_id");
            String title = doc.getString("title");
            int tempMax = doc.getInteger("tempMax");
            int tempMin = doc.getInteger("tempMin");
            int humidMax = doc.getInteger("humidMax");
            int humidMin = doc.getInteger("humidMin");
            int loudMax = doc.getInteger("loudMax");


            return new RangeSettingsTemplate(id, title, tempMax, tempMin, humidMax, humidMin, loudMax);
        }
        return null;
    }

}
