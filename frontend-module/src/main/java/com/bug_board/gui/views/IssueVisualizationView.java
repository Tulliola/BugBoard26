package com.bug_board.gui.views;

import com.bug_board.dto.IssueSummaryDTO;
import com.bug_board.utilities.MyStage;
import com.bug_board.utilities.TitleBar;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.util.List;

public class IssueVisualizationView extends MyStage {
    private VBox root = new VBox();
    private Scene scene = new Scene(root);
    private TitleBar titleBar;
    private List<IssueSummaryDTO> issueList;

    public IssueVisualizationView(List<IssueSummaryDTO> issueList) {
        this.issueList = issueList;

        this.initialize();
    }

    private void initialize() {
        this.setFullScreen();
        this.setScene();
    }

    private void setScene() {
        scene.getStylesheets().add(getClass().getResource("/css/components_style.css").toExternalForm());

        root.setAlignment(Pos.TOP_CENTER);

        titleBar = new TitleBar(this, 80);

        root.getChildren().addAll(
                titleBar
        );

        this.setScene(scene);
        this.setMaximized(true);
    }
}
