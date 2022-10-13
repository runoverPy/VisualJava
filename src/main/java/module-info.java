module com.visualjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.json;
    requires commons.cli;

    requires jdk.unsupported;
    requires java.instrument;

    exports com.visualjava;
    exports com.visualjava.parser;
    exports com.visualjava.data;
    exports com.visualjava.vm;
    exports com.visualjava.data.constants;
    exports com.visualjava.data.attributes;
    exports com.visualjava.ui;

    opens com.visualjava.ui to javafx.fxml;
    exports com.visualjava.invoke;
}