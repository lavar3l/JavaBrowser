package com.browser.javabrowser.messages;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/*
 * MessageBox styled according to the style of the browser
 */

public class StyledAlert {
    public static void show(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(StyledAlert.class.getResource("/base.css").toExternalForm());
        dialogPane.getStylesheets().add(StyledAlert.class.getResource("/base_extras.css").toExternalForm());
        dialogPane.getStylesheets().add(StyledAlert.class.getResource("/base_other_libraries.css").toExternalForm());
        dialogPane.getStylesheets().add(StyledAlert.class.getResource("/dark_theme.css").toExternalForm());
        alert.show();
    }
}
