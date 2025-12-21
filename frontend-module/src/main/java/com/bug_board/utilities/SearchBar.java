package com.bug_board.utilities;

import javafx.application.Platform;
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
    protected StackPane container;
    protected Button clearButton = new Button();

    public SearchBar() {
        this.initialize();
    }

    public void initialize() {
        this.setSpacing(10);

        setSearchButton();

        setSearchButtonImage();

        setStackPane();

        this.getChildren().addAll(container);

        setClearButton();

        this.setAlignment(Pos.CENTER);
    }

    private void setClearButton() {
        clearButton.setId("clear-button");
        setClearButtonImage();
        this.getChildren().add(clearButton);
    }

    private void setClearButtonImage() {
        Image searchIcon = new Image(getClass().getResourceAsStream("/icons/clear_filters.png"), 20, 20, true, true);
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.setFitHeight(20);
        searchIconView.setFitWidth(20);
        searchIconView.setSmooth(true);
        searchIconView.setPreserveRatio(true);
        searchIconView.setSmooth(true);
        clearButton.setGraphic(searchIconView);
    }

    private void setStackPane() {
        container = new StackPane(searchField, searchButton);
        container.setAlignment(Pos.CENTER);
        container.setAlignment(searchButton, Pos.CENTER_RIGHT);
        searchField.setId("search-field");
    }

    public void setTextFieldPrompt(String searchBarPrompt) {
        searchField.setPromptText(searchBarPrompt);
        searchField.setPadding(new Insets(10, 55, 10, 10));
    }

    protected void setSearchButton() {
        searchButton.setId("search-button");;
        searchButton.setTranslateX(-10);
    }

    protected void setSearchButtonImage(){
        Image searchIcon = new Image(getClass().getResourceAsStream("/icons/search_icon.png"), 20, 20, true, true);
        ImageView searchIconView = new ImageView(searchIcon);
        searchIconView.setFitHeight(20);
        searchIconView.setFitWidth(20);
        searchIconView.setSmooth(true);
        searchIconView.setPreserveRatio(true);
        searchIconView.setSmooth(true);
        searchButton.setGraphic(searchIconView);
    }


    public void setSearchButtonAction(Runnable action){
        searchButton.setOnAction(e -> action.run());
    }

    public void setClearButtonAction(Runnable action){
        clearButton.setOnAction(e -> {
            action.run();
        });
    }

    public String getBarText(){
        return searchField.getText();
    }
}
