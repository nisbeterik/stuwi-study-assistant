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
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("users");

        // Check if user exists
        Document usernameQuery = new Document("_id", username);
        Document usernameResult = collection.find(usernameQuery).first();
        String foundUsername = usernameResult.getString("_id");

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

    public static boolean registerUser(String username, String password) throws Exception {
        MongoClient client = MongoConnectionManager.getMongoClient();
        MongoDatabase db = client.getDatabase("stuwi");
        MongoCollection<Document> collection = db.getCollection("users");

        // Check if username is of valid format
        checkUsernameValidity(username);

        // Check if username is taken
        Document usernameQuery = new Document("_id", username);
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
            System.out.println("New user " + username + " successfully created!");
            UserManager.setCurrentUser(username);
            return true;
        } else{
            throw new Exception("Username is already in use");
        }
    }

    private static void checkUsernameValidity(String username) throws Exception {
        if (username.length() > 16 || username.length() < 5){
            throw new Exception("Username must be between 5 and 16 character");
        }
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            throw new Exception("Username must only contain digits and letters, no special characters");
        }
    }

    private static void checkPasswordValidity(String password) throws Exception {
        if (password.length() > 24 || password.length() < 8){
            throw new Exception("Password must be between 8 and 24 characters");
        }
        // Check if password contains at least one special character and one uppercase letter
        if (!password.matches("^(?=.*[!@#$%^&*()_+\\[\\]{}|;:,.<>?])(?=.*[A-Z]).+$")) {
            throw new Exception("Password must contain at least one special character and one uppercase letter");
        }
    }



}
