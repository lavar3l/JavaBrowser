package com.browser.javabrowser;

import com.browser.javabrowser.history.HistoryCollector;
import com.browser.javabrowser.history.HistoryEntry;
import com.browser.javabrowser.history.ICollectable;
import com.browser.javabrowser.settings.Settings;
import com.browser.javabrowser.tools.URLtools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable, ICollectable {
    private HistoryCollector historyCollector;

    @FXML
    TableView<HistoryEntry> tableView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }


    public void setHistoryCollector(HistoryCollector collector)
    {
        this.historyCollector = collector;
        this.loadData();
    }

    private void loadData()
    {
        this.tableView.setEditable(true);

        TableColumn<HistoryEntry, String> dateColumn = new TableColumn<HistoryEntry, String>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<HistoryEntry, String>("date"));
        dateColumn.setSortType(TableColumn.SortType.DESCENDING);

        TableColumn<HistoryEntry, String> titleColumn = new TableColumn<HistoryEntry, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<HistoryEntry, String>("title"));

        TableColumn<HistoryEntry, String> urlColumn = new TableColumn<HistoryEntry, String>("Address");
        urlColumn.setCellValueFactory(new PropertyValueFactory<HistoryEntry, String>("url"));

        this.tableView.getColumns().addAll(dateColumn, titleColumn, urlColumn);
        this.tableView.setItems(this.historyCollector.getData());
        this.tableView.getSortOrder().add(dateColumn);
    }
}