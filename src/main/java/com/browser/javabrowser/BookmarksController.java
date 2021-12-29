package com.browser.javabrowser;

import com.browser.javabrowser.collectors.ICollectable;
import com.browser.javabrowser.collectors.bookmarks.BookmarkEntry;
import com.browser.javabrowser.collectors.bookmarks.BookmarksCollector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BookmarksController implements Initializable, ICollectable<BookmarksCollector> {
    private BookmarksCollector bookmarksCollector;
    private BrowserController parent;

    @FXML
    TableView<BookmarkEntry> tableView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }


    @Override
    public void setCollector(BookmarksCollector collector)
    {
        this.bookmarksCollector = collector;
        this.loadData();
    }

    @Override
    public void setParent(BrowserController parent)
    {
        this.parent = parent;
    }

    private void loadData()
    {
        this.tableView.setEditable(true);
        this.tableView.getColumns().clear();

        TableColumn<BookmarkEntry, String> titleColumn = new TableColumn<BookmarkEntry, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<BookmarkEntry, String>("title"));
        titleColumn.setMinWidth(100);

        TableColumn<BookmarkEntry, String> urlColumn = new TableColumn<BookmarkEntry, String>("Address");
        urlColumn.setCellValueFactory(new PropertyValueFactory<BookmarkEntry, String>("url"));
        urlColumn.setMinWidth(200);

        this.tableView.getColumns().addAll(titleColumn, urlColumn);
        this.tableView.setItems(this.bookmarksCollector.getData());
    }

    private BookmarkEntry getSelectedEntry() {
        return this.tableView.getSelectionModel().getSelectedItem();
    }

    public void loadSelected(ActionEvent actionEvent) {
        BookmarkEntry selectedEntry = this.getSelectedEntry();
        if(selectedEntry != null) this.parent.navigateURL(selectedEntry.getUrl());
    }

    public void removeSelected(ActionEvent actionEvent) {
        BookmarkEntry selectedEntry = this.getSelectedEntry();
        if(selectedEntry != null)
        {
            this.bookmarksCollector.remove(selectedEntry);
            this.loadData();
        }
    }
}