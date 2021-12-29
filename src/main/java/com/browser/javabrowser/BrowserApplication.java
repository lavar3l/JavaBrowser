package com.browser.javabrowser;

import com.browser.javabrowser.bookmarks.BookmarksCollector;
import com.browser.javabrowser.history.HistoryCollector;
import com.browser.javabrowser.history.ICollectable;
import com.browser.javabrowser.settings.Paths;
import com.browser.javabrowser.settings.Settings;
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
    private BookmarksCollector bookmarksCollector;

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
        // Read settings
        Settings.initialize(Paths.getSettingsPath());

        // Prepare history collector
        this.historyCollector = new HistoryCollector(Paths.getHistoryPath());

        // Prepare bookmarks collector
        this.bookmarksCollector = new BookmarksCollector(Paths.getBookmarksPath());
    }

    private void openWindow(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Browser.fxml"));
        Parent root = loader.load();
        BrowserController controller = loader.getController();
        controller.setHistoryCollector(this.historyCollector);
        controller.setBookmarksController(this.bookmarksCollector);
        Scene scene = new Scene(root);
        //stage.getIcons().add(new Image("icon.png"));
        stage.setTitle("JavaBrowser");

        stage.setScene(scene);

        stage.setOnCloseRequest(windowEvent -> {
            this.onAppClosing();
        });

        stage.show();
    }

    private void onAppClosing()
    {
        this.historyCollector.saveToFile(Paths.getHistoryPath());
        this.bookmarksCollector.saveToFile(Paths.getBookmarksPath());
        Settings.saveToFile(Paths.getSettingsPath());
    }
}