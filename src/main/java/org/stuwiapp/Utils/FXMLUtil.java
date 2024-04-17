package org.stuwiapp.Utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;


import static java.util.Objects.requireNonNull;


/***
 * Utility class for loading fxml
 */
public final class FXMLUtil {

    private FXMLUtil() {
    }

    public static <T> T loadFxml(String fxmlName) {
        requireNonNull(fxmlName);

        fxmlName = fxmlName.endsWith(".fxml") ? fxmlName : fxmlName + ".fxml"; // appends .fxml if missing in parameter

        try {
            return FXMLLoader.load(requireNonNull(FXMLUtil.class.getResource("/org/stuwiapp/" + fxmlName)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML file (" + fxmlName + ")");
        }
    }
}
