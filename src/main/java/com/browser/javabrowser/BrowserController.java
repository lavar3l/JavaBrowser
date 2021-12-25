package com.browser.javabrowser;

import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tabs.BrowserTab;
import com.browser.javabrowser.tools.URLSanitizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private BrowserTab activeTab;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.tabs = new ArrayList<BrowserTab>();
        this.addNewTab(null);
        this.activeTab = this.tabs.get(0);
        this.tabPane.getSelectionModel().selectedItemProperty().addListener(
                (ov, t, t1) -> this.onTabSelectionChange()
        );
    }

    public void loadPage(ActionEvent actionEvent) {
        this.navigateURL(URLSanitizer.getFullURL(this.textField.getText()));
    }

    public void loadHomePage(ActionEvent actionEvent) {
        this.navigateURL(Settings.homePage);
    }

    public void addNewTab(ActionEvent actionEvent) {
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
        if(tabId == null || tabId == this.activeTab.getId())
        {
            this.textField.setText(url);
        }
    }

    public void onTabSelectionChange()
    {
        this.activeTab = this.getActiveTab();
        this.changeAddressText(this.activeTab.getURL(), null);
    }
}