module org.openjfx {
    requires javafx.controls;
    requires org.eclipse.paho.client.mqttv3;
    requires javafx.fxml;
    exports org.stuwiapp;
    exports org.stuwiapp.controller;
    opens org.stuwiapp.controller;

}
