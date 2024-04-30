package org.stuwiapp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.stuwiapp.UserManager;

import java.time.LocalDateTime;

public class UserDAO {

    public static boolean loginUser(String username, String password) throws Exception {
        MongoClientConnection clientConnection = MongoClientConnection.getInstance();
        MongoDatabase db = clientConnection.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("users");

        // Check if user exists
        Document usernameQuery = new Document("username", username);
        Document usernameResult = collection.find(usernameQuery).first();
        String foundUsername = usernameResult.getString("username");

        if (username.equals(foundUsername)){

            // Check if password is correct
            Document passwordQuery = new Document("password", password);
            Document passwordResult = collection.find(passwordQuery).first();
            String foundPassword = passwordResult.getString("password");

            if (password.equals(foundPassword)){
                UserManager.setCurrentUser(username);
                return true;
            } else{
                throw new Exception("Password is incorrect");
            }
        } else {
            throw new Exception("User does not exist");
        }

    }

    public void registerUser(String username, String password) throws Exception {
        MongoClientConnection clientConnection = MongoClientConnection.getInstance();
        MongoDatabase db = clientConnection.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("users");

        // Check if username is of valid format
        checkUsernameValidity(username);

        // Check if username is taken
        Document usernameQuery = new Document("username", username);
        Document usernameResult = collection.find(usernameQuery).first();

        if (usernameResult == null){
            System.out.println("Username is available");

            // Check if password is of valid format
            checkPasswordValidity(password);

            JSONObject userJson = new JSONObject();
            userJson.put("_id", username);
            userJson.put("password", password); // TODO Use some sort of encryption?
            userJson.put("dateRegistered", LocalDateTime.now().toString());

            Document userAsDoc = Document.parse(userJson.toString());
            collection.insertOne(userAsDoc);
            System.out.println("New user " + username + "successfully created!");
        } else{
            throw new Exception("Username is already in use");
        }
    }

    public void deleteUser(String username){
        // TODO
    }

    private void checkUsernameValidity(String username){
        // TODO
    }

    private void checkPasswordValidity(String password){
        // TODO
    }



}
