package org.stuwiapp;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoConnectionManager {
    private static MongoClient mongoClient;

    private static void createConnection() {
        String connectionString = "mongodb+srv://stuwiuser:stuwipass@stuwi.tonjfsu.mongodb.net/?retryWrites=true&w=majority&appName=StuWi";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        mongoClient = MongoClients.create(settings);
    }

    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            createConnection();
        }
        return mongoClient;
    }
}