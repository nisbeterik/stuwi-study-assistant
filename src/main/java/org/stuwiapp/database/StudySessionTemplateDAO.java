package org.stuwiapp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.stuwiapp.StudySessionTemplate;

import java.util.ArrayList;

public class StudySessionTemplateDAO {

    // TODO Implement methods for template deletion

    public static void saveTemplateInDatabase(StudySessionTemplate template, String user){
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("sessionTemplates");

        JSONObject templateJson = new JSONObject();
        templateJson.put("user", user);
        templateJson.put("title", template.getTitle());
        templateJson.put("subject", template.getSubject());
        templateJson.put("blockDuration", template.getDuration());
        templateJson.put("breakDuration", template.getBreakDuration());
        templateJson.put("blocks", template.getBlocks());

        Document sessionAsDoc = Document.parse(templateJson.toString());
        collection.insertOne(sessionAsDoc);
    }

    public static ArrayList<StudySessionTemplate> getUserTemplates(String user){
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("sessionTemplates");

        ArrayList<StudySessionTemplate> templates = new ArrayList<>();
        for (Document doc : collection.find(new Document("user", user))){
            String title = doc.getString("title");
            String subject = doc.getString("subject");
            int blockDuration = doc.getInteger("blockDuration");
            int breakDuration = doc.getInteger("breakDuration");
            int blocks = doc.getInteger("blocks");
            StudySessionTemplate template = null;
            try {
                template = new StudySessionTemplate(title, subject, blockDuration, breakDuration, blocks);
            } catch (Exception e) {
                e.printStackTrace();
            }
            templates.add(template);
        }
        return templates;
    }

}
