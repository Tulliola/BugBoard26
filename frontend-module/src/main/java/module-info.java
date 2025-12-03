module com.bug_board.frontendmodule {
    requires javafx.controls;
    requires com.bug_board.sharedmodule;
    requires java.net.http;
    requires jackson.databind;
    requires static lombok;
    requires java.logging;
    requires jackson.core;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    exports com.bug_board;
}