module com.browser.javabrowser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires javafx.web;

    opens com.browser.javabrowser to javafx.fxml;
    exports com.browser.javabrowser;
}