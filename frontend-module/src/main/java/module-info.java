module com.bug_board.frontendmodule {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bug_board.frontendmodule to javafx.fxml;
    exports com.bug_board.frontendmodule;
}