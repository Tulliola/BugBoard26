package com.bug_board.utilities;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.awt.*;

public class BugBoardLabel extends StackPane {
    private Text textInShape;
    private SVGPath shape;

    public BugBoardLabel(String text, String fillColor) {
        this.createBugBoardLabel(text, fillColor);
    }

    private void createBugBoardLabel(String text, String fillColor) {
        this.setMaxWidth(Region.USE_PREF_SIZE);

        String borderColor = calculateBorderColorFromFillColor(fillColor);

        String shapePath = "M0 0 H200 L215 20 L200 40 H0 Z ";
        shape = new SVGPath();
        shape.setContent(shapePath);
        shape.getStyleClass().add("bugboard-label");
        shape.setStyle("-fx-fill: " + fillColor + "; " +
                "-fx-stroke: " + borderColor + "; " +
                "-fx-stroke-width: 1px;");

        textInShape = new Text(text);
        textInShape.setStyle("-fx-fill: white");

        Circle pin = new Circle(10);
        pin.setStyle("-fx-fill: white; -fx-stroke: " + borderColor + "; -fx-stroke-width: 1px");
        pin.translateXProperty().set(10);

        this.getChildren().addAll(shape, textInShape, pin);

        this.setAlignment(pin, Pos.CENTER_LEFT);
    }

    private String calculateBorderColorFromFillColor(String fillColor) {
        Color darkerColor = Color.valueOf(fillColor).darker();

        return String.format("#%02x%02x%02x",
                (int) (darkerColor.getRed() * 255),
                (int) (darkerColor.getGreen() * 255),
                (int) (darkerColor.getBlue() * 255)
        );
    }

    public void setText(String newText) {
        textInShape.setText(newText);
    }

    public void setColor(String newColor) {
        shape.setStyle("-fx-fill: " + newColor + "; -fx-stroke: " + calculateBorderColorFromFillColor(newColor));
    }
}
