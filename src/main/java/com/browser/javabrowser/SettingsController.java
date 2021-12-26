package com.browser.javabrowser;

import com.browser.javabrowser.history.HistoryCollector;
import com.browser.javabrowser.history.ICollectable;
import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tools.URLtools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class SettingsController implements Initializable, ICollectable {
    @FXML
    private TextField homePageTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ToggleButton favouritesToggleButton;

    private HistoryCollector historyCollector;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.homePageTextField.setText(Settings.homePage);
    }

    public void setHomePage(ActionEvent actionEvent) {
        String homePageURL = this.homePageTextField.getText();
        if(URLtools.isValidURL(homePageURL))
        {
            Settings.homePage = homePageURL;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Provided home page URL is not valid.");
            alert.show();
        }
    }

    public void clearHistory(ActionEvent actionEvent) {
        this.historyCollector.clear();
    }

    @Override
    public void setHistoryCollector(HistoryCollector collector) {
        this.historyCollector = collector;
    }
}