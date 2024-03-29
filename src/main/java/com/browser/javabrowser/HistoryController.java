package com.browser.javabrowser;

import com.browser.javabrowser.collectors.history.HistoryCollector;
import com.browser.javabrowser.collectors.history.HistoryEntry;
import com.browser.javabrowser.collectors.ICollectable;
import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tabs.BrowserTab;
import com.browser.javabrowser.tools.URLtools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/*
 * History window controller
 */

public class HistoryController implements Initializable, ICollectable<HistoryCollector> {

    //region Controller initialization

    @FXML
    TableView<HistoryEntry> tableView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    //endregion

    //region Collector handlers

    private HistoryCollector historyCollector;
    private BrowserController parent;

    @Override
    public void setCollector(HistoryCollector collector) {
        this.historyCollector = collector;
        this.loadData();
    }

    @Override
    public void setParent(BrowserController parent) {
        this.parent = parent;
    }

    //endregion

    //region Data loader

    private void loadData() {
        // Setup tableview layout
        this.tableView.setEditable(true);
        this.tableView.getColumns().clear();

        // Prepare date converter and column properties
        TableColumn<HistoryEntry, String> dateColumn = new TableColumn<HistoryEntry, String>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<HistoryEntry, String>("date"));
        dateColumn.setSortType(TableColumn.SortType.DESCENDING);
        dateColumn.setMinWidth(200);

        // Prepare title converter and column properties
        TableColumn<HistoryEntry, String> titleColumn = new TableColumn<HistoryEntry, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<HistoryEntry, String>("title"));
        titleColumn.setMinWidth(100);

        // Prepare url converter and column properties
        TableColumn<HistoryEntry, String> urlColumn = new TableColumn<HistoryEntry, String>("Address");
        urlColumn.setCellValueFactory(new PropertyValueFactory<HistoryEntry, String>("url"));
        urlColumn.setMinWidth(200);

        // Add columns to tableview and load data
        this.tableView.getColumns().addAll(dateColumn, titleColumn, urlColumn);
        this.tableView.setItems(this.historyCollector.getData());
        this.tableView.getSortOrder().add(dateColumn);
    }

    //endregion

    //region Selection handlers

    private HistoryEntry getSelectedEntry() {
        return this.tableView.getSelectionModel().getSelectedItem();
    }

    public void loadSelected(ActionEvent actionEvent) {
        HistoryEntry selectedEntry = this.getSelectedEntry();
        if(selectedEntry != null) this.parent.navigateURL(selectedEntry.getUrl());
    }

    public void removeSelected(ActionEvent actionEvent) {
        HistoryEntry selectedEntry = this.getSelectedEntry();
        if (selectedEntry != null) {
            this.historyCollector.remove(selectedEntry);
            this.loadData();
        }
    }

    //endregion
}