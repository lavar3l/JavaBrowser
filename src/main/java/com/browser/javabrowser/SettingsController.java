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


public class SettingsController implements Initializable {
    @FXML
    private TextField homePageTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ToggleButton favouritesToggleButton;

    @FXML
    private ChoiceBox<ISearcher> searcherChoiceBox;

    @FXML
    private ChoiceBox<IMacro> macroChoiceBox;


    private HistoryCollector historyCollector;
    private BookmarksCollector bookmarksCollector;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.macroManager = new MacroManager(Paths.getMacroPath());
        this.loadMacros();

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

    // region Macros

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
        // load macros from the macro directory
        this.macroManager.loadMacros();

        // add a listener for the item selection event
        this.macroChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IMacro>() {
            @Override
            public void changed(ObservableValue<? extends IMacro> observableValue, IMacro iMacro, IMacro t1) {
                selectedMacro = t1;
            }
        });

        // load macros
        this.macroChoiceBox.getItems().addAll(this.macroManager.getMacros());
    }

    // endregion
}