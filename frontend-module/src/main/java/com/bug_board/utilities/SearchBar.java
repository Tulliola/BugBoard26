package com.bug_board.utilities;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SearchBar extends HBox {
    protected TextField searchField = new TextField();
    protected Button searchButton = new Button();
    protected boolean isExpanded = false;
    protected StackPane container;

    public SearchBar() {
        this.initialize();
    }

    public void initialize() {
        setButton();
        setButtonImage();
        setTextField();
        setStackPane();

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(container);
    }

    private void setStackPane() {
        container = new StackPane(searchField, searchButton);
        container.setAlignment(Pos.CENTER);
        container.setAlignment(searchButton, Pos.CENTER_RIGHT);
    }

    private void setTextField() {
        searchField.setPromptText("Search project");
        searchField.setId("search-field");
        searchField.setPadding(new Insets(10, 55, 10, 10));
    }

    protected void setButton() {
        searchButton.setId("search-button");;
        searchButton.setTranslateX(-10);
    }

    protected void setButtonImage(){
        Image searchIcon = new Image(getClass().getResourceAsStream("/icons/search_icon.png"), 20, 20, true, true);
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.setFitHeight(20);
        searchIconView.setFitWidth(20);
        searchIconView.setSmooth(true);
        searchIconView.setPreserveRatio(true);
        searchIconView.setSmooth(true);
        searchButton.setGraphic(searchIconView);
    }


    public void setButtonAction(Runnable action){
        searchButton.setOnAction(e -> {
            action.run();
        });
    }

    public String getBarText(){
        return searchField.getText();
    }
}
