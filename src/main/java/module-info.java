module com.browser.javabrowser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens com.browser.javabrowser to javafx.fxml;
    exports com.browser.javabrowser;
}