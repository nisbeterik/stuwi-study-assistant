module org.openjfx {
    requires javafx.controls;
    requires org.eclipse.paho.client.mqttv3;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    exports org.stuwiapp;
    exports org.stuwiapp.controller;
    opens org.stuwiapp.controller;

}
