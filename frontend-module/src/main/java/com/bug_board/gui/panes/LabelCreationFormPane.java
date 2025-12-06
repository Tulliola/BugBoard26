package com.bug_board.gui.panes;

import com.bug_board.utilities.BugBoardLabel;
import com.bug_board.utilities.PaletteButton;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LabelCreationFormPane extends StackPane {
    private final StackPane parentContainer;
    private BugBoardLabel sampleLabel;
    private String[] colorPalette = {"#FF0000", "#FF8000", "#FFFF00",
            "#80FF00", "#00FF00", "#00FF80", "#00FFFF", "#0080FF"};

    public LabelCreationFormPane(StackPane parentContainer) {
        this.parentContainer = parentContainer;

        this.initalize();
    }

    private void initalize() {
        this.setBackground();

        this.getChildren().add(this.createCreationForm());
    }

    private VBox createCreationForm() {
        VBox form = new VBox();
        form.setAlignment(Pos.TOP_CENTER);
        form.setId("label-creation-form");

        ImageView painterGif = new ImageView(new Image(getClass().getResourceAsStream("/gifs/painter.gif")));
        painterGif.setFitHeight(100);
        painterGif.setFitWidth(100);

        form.getChildren().add(painterGif);

        Text createYourLabelText = new Text("Create your own label!");
        createYourLabelText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");

        sampleLabel = new BugBoardLabel("This is a label", "#FFFFFF");

        form.getChildren().addAll(
                createYourLabelText,
                this.createPalette(),
                sampleLabel
        );

        return form;
    }

    private HBox createPalette() {
        HBox palette = new HBox();
        palette.setSpacing(10);

        for(String color : colorPalette) {
            PaletteButton paletteButton = new PaletteButton(color);

            paletteButton.setOnMouseClicked(event -> {
                sampleLabel.setColor(paletteButton.getColor());
            });

            palette.getChildren().add(paletteButton);
        }

        return palette;
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: rgb(0, 0, 0, 0.4);");

        this.setOnMouseClicked(event -> {
            if(event.getTarget() == this)
                parentContainer.getChildren().remove(this);
        });
    }
}
