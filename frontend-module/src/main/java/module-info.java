module com.bug_board.frontendmodule {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.bug_board.sharedmodule;
    requires java.net.http;
    requires jackson.databind;
    requires static lombok;
    requires java.logging;
    requires jackson.core;


    opens com.bug_board.frontendmodule to javafx.fxml;
    exports com.bug_board;
    opens com.bug_board to javafx.fxml;
}