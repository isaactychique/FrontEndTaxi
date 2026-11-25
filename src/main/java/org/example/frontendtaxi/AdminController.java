package org.example.frontendtaxi;

import Metier.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.Serializable;
import java.util.List;

public class AdminController implements Serializable {
    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private TableView<Conducteur> conducteursTable;
    @FXML
    private TableColumn<Conducteur, String> conducteurNomColumn;
    @FXML
    private TableColumn<Conducteur, String> conducteurPrenomColumn;
    @FXML
    private TableColumn<Conducteur, String> conducteurTelephoneColumn;
    @FXML
    private TableColumn<Conducteur, String> conducteurVilleColumn;
    @FXML
    private TableColumn<Conducteur, String> conducteurEmailColumn;

    @FXML
    private TableView<Passager> passagersTable;
    @FXML
    private TableColumn<Passager, String> passagerNomColumn;
    @FXML
    private TableColumn<Passager, String> passagerPrenomColumn;
    @FXML
    private TableColumn<Passager, String> passagerTelephoneColumn;
    @FXML
    private TableColumn<Passager, String> passagerVilleColumn;
    @FXML
    private TableColumn<Passager, String> passagerEmailColumn;

    private EntityManagerFactory emf;
    private EntityManager em;
    private ImplDeclarationMethodes id = new ImplDeclarationMethodes();

    @FXML
    public void initialize() {
        emf = Persistence.createEntityManagerFactory("DonneesTaxi");
        em = emf.createEntityManager();
        // Initialize table columns
        initTableColumns();

        // Load data into tables
        loadConducteurs();
        loadPassagers();
    }

    private void initTableColumns() {
        conducteurNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        conducteurPrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        conducteurTelephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        conducteurVilleColumn.setCellValueFactory(new PropertyValueFactory<>("ville"));
        conducteurEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        passagerNomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        passagerPrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        passagerTelephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        passagerVilleColumn.setCellValueFactory(new PropertyValueFactory<>("ville"));
        passagerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadConducteurs() {
        List<Conducteur> conducteurs = id.getConducteurs();
        ObservableList<Conducteur> conducteursData = FXCollections.observableArrayList(conducteurs);
        conducteursTable.setItems(conducteursData);
    }

    private void loadPassagers() {
        List<Passager> passagers = id.getPassagers();
        ObservableList<Passager> passagersData = FXCollections.observableArrayList(passagers);
        passagersTable.setItems(passagersData);
    }

    @FXML
    private void handleEditConducteur() {
        // Logique pour modifier un conducteur
    }

    @FXML
    private void handleDeleteConducteur() {
        Conducteur selectedConducteur = conducteursTable.getSelectionModel().getSelectedItem();
        if (selectedConducteur != null) {
            EntityTransaction et = em.getTransaction();
            et.begin();
            try {
                Conducteur conducteurToDelete = em.find(Conducteur.class, selectedConducteur.getIdentifiant_C());
                if (conducteurToDelete != null) {
                    em.remove(conducteurToDelete);
                    et.commit();
                    loadConducteurs();
                } else {
                    et.rollback();
                    showAlert("Error", "The selected driver was not found in the database.");
                }
            } catch (Exception e) {
                et.rollback();
                showAlert("Error", "Could not delete the driver.");
                e.printStackTrace();
            }
        } else {
            showAlert("No driver selected", "Select a driver to delete.");
        }
    }

    @FXML
    private void handleEditPassager() {
        // Logique pour modifier un passager
    }

    @FXML
    private void handleDeletePassager() {
        Passager selectedPassager = passagersTable.getSelectionModel().getSelectedItem();
        if (selectedPassager != null) {
            EntityTransaction et = em.getTransaction();
            et.begin();
            try {
                Passager passagerToDelete = em.find(Passager.class, selectedPassager.getIdendifiant_P());
                if (passagerToDelete != null) {
                    em.remove(passagerToDelete);
                    et.commit();
                    loadPassagers();
                } else {
                    et.rollback();
                    showAlert("Error", "The selected passenger was not found in the database.");
                }
            } catch (Exception e) {
                et.rollback();
                showAlert("Error", "Could not delete the passenger.");
                e.printStackTrace();
            }
        } else {
            showAlert("No passenger selected", "Select a passenger to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void handleRafraîchir(ActionEvent actionEvent) {
        emf = Persistence.createEntityManagerFactory("DonneesTaxi");
        em = emf.createEntityManager();
        // Initialize table columns
        initTableColumns();

        // Load data into tables
        loadConducteurs();
        loadPassagers();
    }

    // Ensure resources are closed when the application is closed
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
