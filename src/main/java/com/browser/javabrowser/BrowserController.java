package com.browser.javabrowser;

import com.browser.javabrowser.history.HistoryCollector;
import com.browser.javabrowser.history.IArchivable;
import com.browser.javabrowser.history.ICollectable;
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
import javafx.scene.web.WebHistory;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BrowserController implements Initializable, IBrowsable, IArchivable, ICollectable {
    @FXML
    private TextField textField;

    @FXML
    private TabPane tabPane;

    private List<BrowserTab> tabs;
    private BrowserTab activeTab;

    private HistoryCollector historyCollector;

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

    public void loadPage(ActionEvent actionEvent) {
        this.navigateURL(URLtools.getFullURL(this.textField.getText()));
    }

    public void loadHomePage(ActionEvent actionEvent) {
        this.navigateURL(Settings.homePage);
    }

    public void addNewTab(ActionEvent actionEvent) {
        BrowserTab browserTab = new BrowserTab(Settings.homePage, this);
        this.tabs.add(browserTab);
        this.tabPane.getTabs().add(browserTab.getFXTab());
        this.tabPane.getSelectionModel().select(browserTab.getFXTab());
    }

    private BrowserTab getActiveTab() {
        Tab activeTab = this.tabPane.getSelectionModel().getSelectedItem();
        for(BrowserTab browserTab : this.tabs)
        {
            if(activeTab == browserTab.getFXTab())
            {
                return browserTab;
            }
        }
        return null;
    }



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

    public void changeAddressText(String url, Integer tabId) {
        if(tabId == null || tabId.equals(this.activeTab.getId()))
        {
            this.textField.setText(url);
        }
    }

    public void onTabSelectionChange()
    {
        this.activeTab = this.getActiveTab();
        this.changeAddressText(this.activeTab.getURL(), null);
    }

    public void openSettingsWindow(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent root = loader.load();
            SettingsController controller = loader.getController();
            controller.setHistoryCollector(this.historyCollector);
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
            controller.setHistoryCollector(this.historyCollector);
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

    @Override
    public void archive(WebHistory.Entry entry, Integer tabId) {
        if(tabId.equals(this.activeTab.getId()))
        {
            if(this.historyCollector != null) this.historyCollector.archive(entry, tabId);
        }
    }

    public void setHistoryCollector(HistoryCollector collector)
    {
        this.historyCollector = collector;
    }
}