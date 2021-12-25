package com.browser.javabrowser;

import com.browser.javabrowser.history.HistoryCollector;
import com.browser.javabrowser.history.ICollectable;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;


import java.io.IOException;

public class BrowserApplication extends Application {
    private HistoryCollector historyCollector;

    @Override
    public void start(Stage stage) throws IOException {
        this.initialize();
        this.openWindow(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    private void initialize()
    {
        // Prepare history collector
        this.historyCollector = new HistoryCollector();
    }

    private void openWindow(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Browser.fxml"));
        Parent root = loader.load();
        BrowserController controller = loader.getController();
        controller.setHistoryCollector(this.historyCollector);
        Scene scene = new Scene(root);
        //stage.getIcons().add(new Image("icon.png"));
        stage.setTitle("JavaBrowser");

        stage.setScene(scene);

        stage.setOnCloseRequest(windowEvent -> {
            this.historyCollector.saveToFile("test.json");
        });

        stage.show();
    }
}