package org.example.frontendtaxi;

import Metier.Conducteur;
import Metier.Passager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class InscriptionConducteurController implements Serializable {
    @FXML
    private SceneManager sceneManager;
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telephoneField;

    @FXML
    private DatePicker dateDeNaissancePicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField numeroPermisField;

    @FXML
    private ChoiceBox<String> sexeChoiceBox;

    @FXML
    private Button suivantButton;

    @FXML
    private void initialize() {
        // Initialisation du choix par défaut pour le sexe
        sexeChoiceBox.setValue("Male");
        Passager passager=null;
        try {
            FileInputStream fileInputStream = new FileInputStream("Passager.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            passager = (Passager) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }
        catch (Exception e){
            System.out.println("Passager non inscris");
        }
        if (passager != null) {
            nomField.setText(passager.getNom());
            prenomField.setText(passager.getPrenom());
            telephoneField.setText(passager.getTelephone());
            villeField.setText(passager.getVille());
        }
    }

    @FXML
    private void siSuivant(ActionEvent event) throws IOException {
        if (isInputValid()) {
            // Création de l'objet Conducteur
            Conducteur conducteur = new Conducteur(nomField.getText(), prenomField.getText(), dateDeNaissancePicker.getValue(),telephoneField.getText(),villeField.getText(), emailField.getText(), numeroPermisField.getText(), sexeChoiceBox.getValue());
            // Envoyer l'objet Conducteur via un socket
            try (Socket socket = new Socket("localhost", 12346);
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                oos.writeObject(conducteur);
            }
            // Sauvegarder l'objet Conducteur pour une utilisation ultérieure
            FileOutputStream fileOutputStream = new FileOutputStream("Conducteur.txt");
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(conducteur);
            fileOutputStream.close();
            objectOutputStream.close();


            // Continuer à la scène suivante
            System.out.println("Formulaire valide. Continuer...");
            sceneManager.addScene("MenuConducteurApp", "MenuConducteur-view.fxml");
            sceneManager.switchTo("MenuConducteurApp");
        }
    }

    @FXML
    private void siAnnuler(ActionEvent event) {
        sceneManager.addScene("ChoixConducteurOuPassagerApp", "ChoixConducteurOuPassager-view.fxml");
        sceneManager.switchTo("ChoixConducteurOuPassagerApp");
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomField.getText() == null || nomField.getText().length() == 0) {
            errorMessage += "Last name is required.\n";
        }
        if (prenomField.getText() == null || prenomField.getText().length() == 0) {
            errorMessage += "First name is required.\n";
        }
        if (telephoneField.getText() == null || !telephoneField.getText().matches("\\d+")) {
            errorMessage += "Phone number must contain digits only.\n";
        }
        if (dateDeNaissancePicker.getValue() == null) {
            errorMessage += "Date of birth is required.\n";
        }
        if (emailField.getText() == null || !emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage += "Please enter a valid email address.\n";
        }
        if (villeField.getText() == null || villeField.getText().length() == 0) {
            errorMessage += "City is required.\n";
        }
        if (numeroPermisField.getText() == null || numeroPermisField.getText().length() == 0) {
            errorMessage += "Licence number is required.\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Affiche un message d'alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct the following fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    public static class InscriptionConducteurApp extends Application implements Serializable {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(InscriptionConducteurApp.class.getResource("InscriptionConducteur-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Driver registration");
            stage.setScene(scene);
            stage.show();
        }
    }
}
