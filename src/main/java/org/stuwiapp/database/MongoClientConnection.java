package org.stuwiapp.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoClientConnection {

    private static volatile MongoClientConnection instance;
    private final MongoClient mongoClient;

    private MongoClientConnection() {
        // Create MongoClientSettings. NOTE: <password> needs to be replaced with actual password to access database
        String connectionString = "mongodb+srv://stuwiuser:<password>@stuwi.tonjfsu.mongodb.net/?retryWrites=true&w=majority&appName=StuWi";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        // Create a new client and connect to the server
        try {
            mongoClient = MongoClients.create(settings);
        } catch (MongoClientException e) {
            throw new RuntimeException("Error connecting to MongoDB: " + e.getMessage());
        }
    }

    public static MongoClientConnection getInstance() {
        if (instance == null) {
            synchronized (MongoClientConnection.class) {
                if (instance == null) {
                    instance = new MongoClientConnection();
                }
            }
        }
        return instance;
    }

    public MongoDatabase getDatabase(String databaseName) {
        return mongoClient.getDatabase(databaseName);
    }

    public void close() {
        mongoClient.close();
    }
}