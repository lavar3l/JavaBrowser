package com.browser.javabrowser.tabs;

import com.browser.javabrowser.BrowserController;
import com.browser.javabrowser.IBrowsable;
import com.browser.javabrowser.tools.URLSanitizer;
import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class BrowserTab implements IBrowsable {
    private Tab fxTab;
    private WebView webView;
    private WebEngine engine;
    private BrowserController controller;

    public BrowserTab(String url, BrowserController controller) {
        this.controller = controller;
        this.fxTab = new Tab(URLSanitizer.getTabTitle(url));
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
        String currentURL = this.engine.getLocation();
        System.out.println(currentURL);
        this.fxTab.setText(URLSanitizer.getTabTitle(currentURL));
        this.controller.changeAddressText(currentURL);
        // TO DO: send history entry to History class
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
}