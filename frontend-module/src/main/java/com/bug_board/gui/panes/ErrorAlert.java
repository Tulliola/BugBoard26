package com.bug_board.gui.panes;

import com.bug_board.navigation_manager.interfaces.INavigationManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ErrorAlert extends Alert {

    private Button logoutBtn;
    private Button closeBtn;

    private final String errorMessage;
    private final String technicalErrorMessage;

    private final INavigationManager navigationManager;

    public ErrorAlert(INavigationManager navigationManager,
                      String errorMessage,
                      String techincalErrorMessage) {
        super(AlertType.ERROR);

        this.navigationManager = navigationManager;
        this.errorMessage = errorMessage;
        this.technicalErrorMessage = techincalErrorMessage;

        initialize();
    }

    private void initialize() {
        Stage alertStage = (Stage) this.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo_bugboard.png")));

        this.initStyle(StageStyle.UNDECORATED);

        this.setContentText(errorMessage);
        this.getDialogPane().setHeader(this.createHeaderPane());
        this.getDialogPane().setContent(this.createContentPane());
        alertStage.initStyle(StageStyle.TRANSPARENT);

        this.getDialogPane().getScene().setFill(Color.TRANSPARENT);

        String css = this.getClass().getResource("/css/components_style.css").toExternalForm();
        this.getDialogPane().getStylesheets().add(css);

        this.setButtons();
    }

    private void setButtons() {
        ButtonType logoutBtnType = new ButtonType("");
        ButtonType closeApplicationBtnType = new ButtonType("");

        this.getButtonTypes().setAll(logoutBtnType, closeApplicationBtnType);

        logoutBtn = (Button) this.getDialogPane().lookupButton(logoutBtnType);
        this.setLogoutButton();

        closeBtn = (Button) this.getDialogPane().lookupButton(closeApplicationBtnType);
        this.setCloseButton();
    }

    private void setLogoutButton() {

        ImageView logoutImage = new ImageView(new Image(getClass().getResourceAsStream("/icons/logout.png")));
        logoutImage.setFitWidth(25);
        logoutImage.setFitHeight(25);

        logoutBtn.setGraphic(logoutImage);

        logoutBtn.addEventFilter(ActionEvent.ACTION, event -> {
            navigationManager.navigateToLoginPage();
        });
    }

    private void setCloseButton() {
        closeBtn.setText("Close");

        closeBtn.addEventFilter(ActionEvent.ACTION, event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private Pane createHeaderPane() {
        VBox headerPane = new VBox();
        headerPane.setAlignment(Pos.CENTER);
        headerPane.setStyle("-fx-background-color: white");

        ImageView errorImage = new ImageView(new Image(getClass().getResourceAsStream("/icons/error.png")));
        errorImage.setFitHeight(100);
        errorImage.setFitWidth(100);

        Label errorLabel = new Label("Error!");
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 30px; -fx-font-weight: normal");

        headerPane.getChildren().addAll(errorImage, errorLabel);

        return headerPane;
    }

    private Pane createContentPane() {
        VBox contentPane = new VBox();
        contentPane.setSpacing(20);
        contentPane.setAlignment(Pos.CENTER);
        contentPane.setStyle("-fx-background-color: white");

        Label lessTechnicalError = new Label(this.errorMessage);

        Label viewMoreDetails = new Label("View more details");
        viewMoreDetails.setId("view-details-label");
        viewMoreDetails.setUnderline(true);

        Tooltip technicalDescription = new Tooltip(technicalErrorMessage);
        technicalDescription.setShowDelay(Duration.millis(200));
        Tooltip.install(viewMoreDetails, technicalDescription);

        contentPane.getChildren().addAll(lessTechnicalError, viewMoreDetails);

        return contentPane;
    }

}
