package com.browser.javabrowser;

import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tabs.BrowserTab;
import com.browser.javabrowser.tools.URLSanitizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BrowserController implements Initializable, IBrowsable {
    @FXML
    private TextField textField;

    @FXML
    private TabPane tabPane;

    private List<BrowserTab> tabs;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.tabs = new ArrayList<BrowserTab>();
        this.addNewTab(null);
    }

    public void loadPage(ActionEvent actionEvent) {
        this.navigateURL(URLSanitizer.getFullURL(this.textField.getText()));
    }

    public void loadHomePage(ActionEvent actionEvent) {
        this.navigateURL(Settings.homePage);
    }

    public void addNewTab(ActionEvent actionEvent) {
        int numTabs = this.tabPane.getTabs().size();
        BrowserTab browserTab = new BrowserTab(Settings.homePage, this);
        this.tabs.add(browserTab);
        this.tabPane.getTabs().add(browserTab.getFXTab());
        this.tabPane.getSelectionModel().selectLast();
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
        BrowserTab activeTab = this.getActiveTab();
        if(activeTab != null) activeTab.navigateForward();
    }

    @Override
    public void navigateBackward() {
        BrowserTab activeTab = this.getActiveTab();
        if(activeTab != null) activeTab.navigateBackward();
    }

    @Override
    public void navigateURL(String url) {
        BrowserTab activeTab = this.getActiveTab();
        if(activeTab != null) activeTab.navigateURL(url);
    }

    @Override
    public void reloadPage() {
        BrowserTab activeTab = this.getActiveTab();
        if(activeTab != null) activeTab.reloadPage();
    }

    public void changeAddressText(String url) {
        this.textField.setText(url);
    }
}