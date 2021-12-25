package com.browser.javabrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.IOException;

public class BrowserApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Browser.fxml"));
        Parent root = loader.load();
        BrowserController controller = loader.getController();
        Scene scene = new Scene(root);
        //stage.getIcons().add(new Image("icon.png"));
        //stage.setTitle("Bro web browser");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}