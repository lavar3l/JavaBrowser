package com.browser.javabrowser;

import com.browser.javabrowser.collectors.bookmarks.BookmarksCollector;
import com.browser.javabrowser.collectors.history.HistoryCollector;
import com.browser.javabrowser.macro.IMacro;
import com.browser.javabrowser.messages.StyledAlert;
import com.browser.javabrowser.searchers.ISearcher;
import com.browser.javabrowser.searchers.SearcherEnum;
import com.browser.javabrowser.searchers.SearcherFactory;
import com.browser.javabrowser.macro.MacroManager;
import com.browser.javabrowser.settings.Paths;
import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tools.URLtools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/*
 * Settings window controller
 */

public class SettingsController implements Initializable {
    //region Controller initialization

    @FXML
    private TextField homePageTextField;

    @FXML
    private ChoiceBox<ISearcher> searcherChoiceBox;

    @FXML
    private ChoiceBox<IMacro> macroChoiceBox;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Load available macros
        this.macroManager = new MacroManager(Paths.getMacroPath());
        this.loadMacros();

        // Load current home page
        this.homePageTextField.setText(Settings.getInstance().getHomePage());

        // Load currently selected and available search engines
        for (SearcherEnum searcherEnum : SearcherEnum.values()) {
            this.searcherChoiceBox.getItems().add(SearcherFactory.create(searcherEnum));
        }
        this.searcherChoiceBox.getSelectionModel().select(SearcherFactory.create(Settings.getInstance().getSearcher()));
    }

    //endregion

    //region Collectors handlers

    private HistoryCollector historyCollector;
    private BookmarksCollector bookmarksCollector;

    public void setCollector(HistoryCollector collector) {
        this.historyCollector = collector;
    }

    public void setCollector(BookmarksCollector collector) {
        this.bookmarksCollector = collector;
    }

    //endregion

    //region Action handlers

    public void setHomePage(ActionEvent actionEvent) {
        String homePageURL = this.homePageTextField.getText();
        if (URLtools.isValidURL(homePageURL)) {
            Settings.getInstance().setHomePage(homePageURL);
            StyledAlert.show(Alert.AlertType.INFORMATION, "Settings has been saved.");
        } else {
            StyledAlert.show(Alert.AlertType.ERROR, "Provided home page URL is not valid.");
        }
    }

    public void setSearcher(ActionEvent actionEvent) {
        Settings.getInstance().setSearcher(this.searcherChoiceBox.getSelectionModel().getSelectedItem().toEnum());
        StyledAlert.show(Alert.AlertType.INFORMATION, "Settings has been saved.");
    }

    public void clearHistory(ActionEvent actionEvent) {
        this.historyCollector.clear();
    }

    public void clearBookmarks(ActionEvent actionEvent) {
        this.bookmarksCollector.clear();
    }

    //endregion

    //region Macros handlers

    private MacroManager macroManager = null;
    private BrowserController browserController = null;
    private IMacro selectedMacro = null;

    public void registerController(BrowserController browserController) {
        this.browserController = browserController;
    }

    public void runMacro() throws Exception {
        if (this.browserController == null)
            throw new Exception("BrowserController object was null");

        if (this.selectedMacro == null)
            throw new Exception("No macro was selected");

        ((Stage)this.homePageTextField.getScene().getWindow()).close();
        this.selectedMacro.execute(this.browserController);
    }

    private void loadMacros() {
        // Load macros from the macro directory
        this.macroManager.loadMacros();

        // Add a listener for the item selection event
        this.macroChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IMacro>() {
            @Override
            public void changed(ObservableValue<? extends IMacro> observableValue, IMacro iMacro, IMacro t1) {
                selectedMacro = t1;
            }
        });

        // Load macros
        this.macroChoiceBox.getItems().addAll(this.macroManager.getMacros());
    }

    //endregion
}