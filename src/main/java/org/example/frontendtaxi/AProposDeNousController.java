package org.example.frontendtaxi;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class AProposDeNousController {

    @FXML
    private ImageView author1Image;
    @FXML
    private Label author1Name;

    @FXML
    private ImageView author2Image;
    @FXML
    private Label author2Name;

    @FXML
    private ImageView author3Image;
    @FXML
    private Label author3Name;

    @FXML
    private TextFlow aboutUsTextFlow;

    @FXML
    private TextFlow missionTextFlow;

    @FXML
    private TextFlow featuresTextFlow;

    @FXML
    public void initialize() {
        // Set author 1 details
        author1Image.setImage(new Image(getClass().getResourceAsStream("Images/dakio.png.jpg")));
        author1Name.setText("DAKIO Sounsin Isaac Tychique");

        // Set author 2 details
        author2Image.setImage(new Image(getClass().getResourceAsStream("Images/kote.png.jpg")));
        author2Name.setText("KOTE Seydou");

        // Set author 3 details
        author3Image.setImage(new Image(getClass().getResourceAsStream("Images/arsalane.png.jpg")));
        author3Name.setText("ARSALANE Aymane");
        // Set text content
        setTextContent(aboutUsTextFlow, "My Taxi Driver is a JavaFX taxi booking prototype created as a team project.");
        setTextContent(missionTextFlow, "The project explores how passengers and drivers can connect through a desktop application.");
        setTextContent(featuresTextFlow, "Passenger and driver profiles\nMap-based pickup and destination selection\nDriver matching and in-app messaging\nAdministration interface");
    }

    private void setTextContent(TextFlow textFlow, String content) {
        textFlow.getChildren().clear();
        String[] paragraphs = content.split("\n");
        for (String paragraph : paragraphs) {
            textFlow.getChildren().add(new Text(paragraph + "\n"));
        }
    }

    public static class AProposDeNous extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("AProposDeNous-view.fxml"));
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            primaryStage.setTitle("About this project");
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}
