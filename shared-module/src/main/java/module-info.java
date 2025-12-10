module com.bug_board.sharedmodule {
    exports com.bug_board.dto;
    exports com.bug_board.enum_classes;
    exports com.bug_board.dto.email;
    requires com.fasterxml.jackson.annotation;

    requires static lombok;
    requires jakarta.persistence;
}