package org.stuwiapp;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoClientConnection {

    private static MongoClient mongoClient;

    public MongoClientConnection() {
        // Create MongoClientSettings. NOTE: <password> needs to be replaced with actual password to access database
        String connectionString = "mongodb+srv://stuwiuser:<password>@stuwi.tonjfsu.mongodb.net/?retryWrites=true&w=majority&appName=StuWi";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        // Create a new client and connect to the server
        try {
            mongoClient = MongoClients.create(settings);
        } catch (MongoClientException e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
        }
    }

    public MongoDatabase getDatabase(String databaseName) {
        if (mongoClient != null) {
            return mongoClient.getDatabase(databaseName);
        }
        return null;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}