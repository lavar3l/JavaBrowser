package com.browser.javabrowser;

import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tools.URLtools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class SettingsController implements Initializable {
    @FXML
    private TextField homePageTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ToggleButton favouritesToggleButton;

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
}