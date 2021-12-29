package com.browser.javabrowser.tabs;

import com.browser.javabrowser.BrowserController;
import com.browser.javabrowser.IBrowsable;
import com.browser.javabrowser.tools.URLtools;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class BrowserTab implements IBrowsable {
    private static Integer tabCount = 0;
    private Integer id;
    private Tab fxTab;
    private WebView webView;
    private WebEngine engine;
    private BrowserController controller;

    public BrowserTab(String url, BrowserController controller) {
        this.id = ++BrowserTab.tabCount;
        this.controller = controller;
        this.fxTab = new Tab(URLtools.getTabTitle(url));
        this.webView = new WebView();
        this.engine = this.webView.getEngine();
        this.engine.load(url);
        this.fxTab.setContent(this.webView);

        this.engine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.onPageChange();
                });
    }

    public Tab getFXTab() {
        return this.fxTab;
    }

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

    private void onPageChange() {
        // Set current URL in address bar and tab title
        String currentURL = this.getURL();
        this.fxTab.setText(URLtools.getTabTitle(currentURL));
        this.controller.changeAddressText(currentURL, this.id);

        // Send history entry to history collector
        Integer currentId = this.engine.getHistory().getCurrentIndex();
        this.controller.archive(this.engine.getHistory().getEntries().get(currentId), this.id);
    }

    private void navigateHistory(int step) {
        WebHistory history = engine.getHistory();
        int newIndex = history.getCurrentIndex() + step;
        if(newIndex >= 0 && newIndex < history.getEntries().size())
        {
            history.go(step);
            this.onPageChange();
        }
    }

    public String getURL()
    {
        return this.engine.getLocation();
    }

    public Integer getId()
    {
        return this.id;
    }

    public WebHistory.Entry getHistoryEntry() {
        Integer currentId = this.engine.getHistory().getCurrentIndex();
        return this.engine.getHistory().getEntries().get(currentId);
    }
}
