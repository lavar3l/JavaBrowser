package com.browser.javabrowser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;


import java.net.URL;
import java.util.ResourceBundle;

public class BrowserController implements Initializable {
    @FXML
    private WebView webView;

    @FXML
    private TextField textField;

    private WebEngine engine;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        engine = webView.getEngine();
        String homePage = "www.google.com";
        textField.setText(homePage);
    }

    public void loadPage(ActionEvent actionEvent) {
        engine.load("http://"+textField.getText());

    }
}