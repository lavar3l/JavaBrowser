package com.browser.javabrowser.tabs;

import com.browser.javabrowser.BrowserController;
import com.browser.javabrowser.IBrowsable;
import com.browser.javabrowser.tools.URLtools;
import javafx.scene.control.Tab;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.awt.*;

/*
 * Component which encapsulates single browser tab
 */

public class BrowserTab implements IBrowsable {

    //region Component initialization

    private static Integer tabCount = 0;
    private Integer id;
    private Tab fxTab;
    private WebView webView;
    private WebEngine engine;
    private BrowserController controller;

    public BrowserTab(String url, BrowserController controller) {
        // Initialize component
        this.id = ++BrowserTab.tabCount;
        this.controller = controller;
        this.fxTab = new Tab(URLtools.getTabTitle(url));
        this.webView = new WebView();
        this.engine = this.webView.getEngine();
        this.engine.load(url);
        this.fxTab.setContent(this.webView);

        // Set handler on page change event
        this.engine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.onPageChange();
                });

        // Set handler for 'Open in new tab' feature
        this.engine.setCreatePopupHandler(config -> controller.openInNewTab(fxTab));
    }

    //endregion

    //region Browser navigation features

    @Override
    public void navigateForward() {
        this.navigateHistory(1);
    }

    @Override
    public void navigateBackward() {
        this.navigateHistory(-1);
    }

    @Override
    public void navigateURL(String url) {
        this.engine.load(url);
    }

    @Override
    public void reloadPage() {
        this.engine.reload();
    }

    //endregion

    //region Getters

    public Tab getFXTab() {
        return this.fxTab;
    }

    public String getURL() {
        return this.engine.getLocation();
    }

    public Integer getId() {
        return this.id;
    }

    public WebEngine getEngine() {
        return this.engine;
    }

    //endregion

    //region Page change events

    private void onPageChange() {
        // Set current URL in address bar and tab title
        String currentURL = this.getURL();
        String currentTitle = this.engine.getTitle();
        if(currentTitle == null) currentTitle = currentURL;
        this.fxTab.setText(URLtools.getTabTitle(currentTitle));
        this.controller.changeAddressText(currentURL, this.id);

        // Send history entry to history collector
        if(!this.engine.getHistory().getEntries().isEmpty()) {
            Integer currentId = this.engine.getHistory().getCurrentIndex();
            this.controller.archive(this.engine.getHistory().getEntries().get(currentId), this.id);
        }
    }

    //endregion

    //region History

    private void navigateHistory(int step) {
        WebHistory history = engine.getHistory();
        int newIndex = history.getCurrentIndex() + step;
        if(newIndex >= 0 && newIndex < history.getEntries().size()) {
            history.go(step);
            this.onPageChange();
        }
    }

    public WebHistory.Entry getHistoryEntry() {
        Integer currentId = this.engine.getHistory().getCurrentIndex();
        return this.engine.getHistory().getEntries().get(currentId);
    }

    //endregion

}
