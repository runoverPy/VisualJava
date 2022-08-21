module com.visualjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.visualjava to javafx.fxml;
    exports com.visualjava;
    exports com.visualjava.parser;
    opens com.visualjava.parser to javafx.fxml;
}