module com.bug_board.frontendmodule {
    requires javafx.controls;
    requires com.bug_board.sharedmodule;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires static lombok;
    requires java.logging;
    requires com.fasterxml.jackson.core;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires javafx.base;
    requires com.simtechdata.jokeapi;
    requires spring.core;

    exports com.bug_board;
}