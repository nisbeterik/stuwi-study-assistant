package org.stuwiapp.controller;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.stuwiapp.Utils.FXMLUtil;

public class ParentController {

    protected void redirect(Event event, String fxml) {
        Parent fxmlFile = FXMLUtil.loadFxml(fxml);
        Scene scene = new Scene(fxmlFile);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
