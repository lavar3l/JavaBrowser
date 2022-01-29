package com.browser.javabrowser;

import com.browser.javabrowser.collectors.bookmarks.BookmarksCollector;
import com.browser.javabrowser.collectors.history.HistoryCollector;
import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tabs.BrowserTab;
import com.browser.javabrowser.tools.URLtools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/*
 * Browser main window controller
 */

public class BrowserController implements Initializable, IBrowsable {

    //region Controller initialization

    @FXML
    private TextField textField;

    @FXML
    private TabPane tabPane;

    private List<BrowserTab> tabs;
    private BrowserTab activeTab;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Set tab pane policies
        this.tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

        // Prepare tab collection and initial tab
        this.tabs = new ArrayList<BrowserTab>();
        this.addNewTab(null);
        this.activeTab = this.tabs.get(0);
        this.tabPane.getTabs().add(newTabButton(this.tabPane));
        this.tabPane.getSelectionModel().selectedItemProperty().addListener(
                (ov, t, t1) -> this.onTabSelectionChange()
        );
    }

    private Tab newTabButton(TabPane tabPane) {
        Tab newTabButton = new Tab("+");
        newTabButton.setClosable(false);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, activeTab) -> {
            if(activeTab == newTabButton) {
                // Add and select new tab
                this.addNewTab(null);

                // Add new tab button at the end of the tab pane
                this.tabPane.getTabs().remove(newTabButton);
                this.tabPane.getTabs().add(newTabButton);
            }
        });
        return newTabButton;
    }

    //endregion

    //region Collectors handlers

    private HistoryCollector historyCollector;
    private BookmarksCollector bookmarksCollector;

    public void setCollector(HistoryCollector collector) {
        this.historyCollector = collector;
    }

    public void setCollector(BookmarksCollector collector) {
        this.bookmarksCollector = collector;
    }

    public void archive(WebHistory.Entry entry, Integer tabId) {
        if(tabId.equals(this.activeTab.getId())) {
            if(this.historyCollector != null) this.historyCollector.archive(entry);
        }
    }

    public void addToBookmarks(ActionEvent actionEvent) {
        this.bookmarksCollector.archive(this.activeTab.getHistoryEntry());
    }

    //endregion

    //region Page loaders

    public void loadPage(ActionEvent actionEvent) {
        this.navigateURL(URLtools.getFullURL(this.textField.getText()));
    }

    public void loadHomePage(ActionEvent actionEvent) {
        this.navigateURL(Settings.getInstance().getHomePage());
    }

    //endregion

    //region New tab handlers

    public WebEngine addNewTab(ActionEvent actionEvent) {
        int position = this.tabPane.getTabs().size() - 1;
        if(this.tabPane.getTabs().isEmpty()) position = 0;
        return this.createNewTab(position);
    }

    public WebEngine openInNewTab(Tab source) {
        int position = this.tabPane.getTabs().lastIndexOf(source) + 1;
        return this.createNewTab(position);
    }

    private WebEngine createNewTab(int position) {
        // Add new tab at specified position
        BrowserTab browserTab = new BrowserTab(Settings.getInstance().getHomePage(), this);
        this.tabs.add(browserTab);
        this.tabPane.getTabs().add(position, browserTab.getFXTab());

        // Select new tab
        this.tabPane.getSelectionModel().select(browserTab.getFXTab());

        // Return new tab's web engine
        return browserTab.getEngine();
    }

    //endregion

    //region Active tab selection handlers

    public BrowserTab getActiveTab() {
        Tab activeTab = this.tabPane.getSelectionModel().getSelectedItem();
        for(BrowserTab browserTab : this.tabs) {
            if(activeTab == browserTab.getFXTab()) {
                return browserTab;
            }
        }
        return null;
    }

    public void onTabSelectionChange() {
        this.activeTab = this.getActiveTab();
        this.changeAddressText(this.activeTab.getURL(), null);
    }

    public void changeAddressText(String url, Integer tabId) {
        if(tabId == null || tabId.equals(this.activeTab.getId())) {
            this.textField.setText(url);
        }
    }

    public WebEngine navigateActiveTab(String url) {
        WebEngine engine = this.getActiveTab().getEngine();
        engine.load(url);

        return engine;
    }

    //endregion

    //region Browser navigation features

    @Override
    public void navigateForward() {
        this.activeTab.navigateForward();
    }

    @Override
    public void navigateBackward() {
        this.activeTab.navigateBackward();
    }

    @Override
    public void navigateURL(String url) {
        this.activeTab.navigateURL(url);
    }

    @Override
    public void reloadPage() {
        this.activeTab.reloadPage();
    }

    //endregion

    //region External windows

    public void openSettingsWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent root = loader.load();
            SettingsController controller = loader.getController();
            controller.setCollector(this.historyCollector);
            controller.setCollector(this.bookmarksCollector);
            controller.registerController(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Settings");
            stage.setResizable(false);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openHistoryWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("History.fxml"));
            Parent root = loader.load();
            HistoryController controller = loader.getController();
            controller.setCollector(this.historyCollector);
            controller.setParent(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("History");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openBookmarksWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Bookmarks.fxml"));
            Parent root = loader.load();
            BookmarksController controller = loader.getController();
            controller.setCollector(this.bookmarksCollector);
            controller.setParent(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Bookmarks");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //endregion

    // region Dimensions

    private Stage stage = null;
    public void registerStage(Stage stage) { this.stage = stage; }

    public double getWidth() throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        return this.stage.getWidth();
    }

    public double getHeight() throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        return this.stage.getHeight();
    }

    public void setWidth(double width) throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        this.stage.setWidth(width);
    }

    public void setHeight(double height) throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        this.stage.setHeight(height);
    }

    public double getX() throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        return this.stage.getX();
    }

    public double getY() throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        return this.stage.getY();
    }

    public void setX(double x) throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        this.stage.setX(x);
    }

    public void setY(double y) throws Exception {
        if (this.stage == null)
            throw new Exception("stage parameter was null");

        this.stage.setY(y);
    }

    // endregion
}