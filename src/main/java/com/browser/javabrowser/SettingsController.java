package com.browser.javabrowser;

import com.browser.javabrowser.collectors.bookmarks.BookmarksCollector;
import com.browser.javabrowser.collectors.history.HistoryCollector;
import com.browser.javabrowser.collectors.ICollectable;
import com.browser.javabrowser.messages.StyledAlert;
import com.browser.javabrowser.searchers.ISearcher;
import com.browser.javabrowser.searchers.SearcherEnum;
import com.browser.javabrowser.searchers.SearcherFactory;
import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tools.URLtools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private TextField homePageTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ToggleButton favouritesToggleButton;

    @FXML ChoiceBox<ISearcher> searcherChoiceBox;

    private HistoryCollector historyCollector;
    private BookmarksCollector bookmarksCollector;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.homePageTextField.setText(Settings.homePage);

        for (SearcherEnum searcherEnum : SearcherEnum.values()) {
            this.searcherChoiceBox.getItems().add(SearcherFactory.create(searcherEnum));
        }

        this.searcherChoiceBox.getSelectionModel().select(SearcherFactory.create(Settings.searcher));
    }

    public void setHomePage(ActionEvent actionEvent) {
        String homePageURL = this.homePageTextField.getText();
        if (URLtools.isValidURL(homePageURL))
        {
            Settings.homePage = homePageURL;
            StyledAlert.show(Alert.AlertType.INFORMATION, "Settings has been saved.");
        }
        else
        {
            StyledAlert.show(Alert.AlertType.ERROR, "Provided home page URL is not valid.");
        }
    }

    public void setSearcher(ActionEvent actionEvent) {
        Settings.searcher = this.searcherChoiceBox.getSelectionModel().getSelectedItem().toEnum();
        StyledAlert.show(Alert.AlertType.INFORMATION, "Settings has been saved.");
    }

    public void clearHistory(ActionEvent actionEvent) {
        this.historyCollector.clear();
    }

    public void setCollector(HistoryCollector collector) {
        this.historyCollector = collector;
    }

    public void clearBookmarks(ActionEvent actionEvent) {
        this.bookmarksCollector.clear();
    }

    public void setCollector(BookmarksCollector collector) {
        this.bookmarksCollector = collector;
    }
}