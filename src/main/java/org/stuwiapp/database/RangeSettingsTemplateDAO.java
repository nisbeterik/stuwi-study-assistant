package org.stuwiapp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.stuwiapp.StudySessionTemplate;
import org.stuwiapp.RangeSettingsTemplate;

import java.util.ArrayList;

public class RangeSettingsTemplateDAO {

    public static void saveRangeTemplateInDatabase(RangeSettingsTemplate template, String user){
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("rangeTemplates");

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
    public static ArrayList<RangeSettingsTemplate> getUserRangeTemplates(String user){
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("rangeTemplates");

        ArrayList<RangeSettingsTemplate> templates = new ArrayList<>();
        for (Document doc : collection.find(new Document("user", user))){
            String title = doc.getString("title");
            int tempMax = doc.getInteger("tempMax");
            int tempMin = doc.getInteger("tempMin");
            int humidMax = doc.getInteger("humidMax");
            int humidMin = doc.getInteger("humidMin");
            int loudMax = doc.getInteger("loudMax");

            RangeSettingsTemplate template = new RangeSettingsTemplate(title, tempMax, tempMin, humidMax, humidMin, loudMax);

            templates.add(template);
        }
        return templates;
    }
}
