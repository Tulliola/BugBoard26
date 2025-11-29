module com.bug_board.sharedmodule {
    exports com.bug_board.dto;
    exports com.bug_board.enum_classes;

    requires static lombok;
    requires jakarta.persistence;
}