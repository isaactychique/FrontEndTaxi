package org.example.frontendtaxi;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;

public class AccueilController implements Serializable{
    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private void onGetStarted() {
        sceneManager.addScene("ChoixConducteurOuPassagerApp", "ChoixConducteurOuPassager-view.fxml");
        sceneManager.switchTo("ChoixConducteurOuPassagerApp");
    }

    public static class AccueilApp extends Application implements Serializable {

        public static void main(String[] args) throws InterruptedException {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(AccueilApp.class.getResource("Accueil-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("My Taxi Driver");
            stage.setScene(scene);
            stage.show();
        }
    }
}
