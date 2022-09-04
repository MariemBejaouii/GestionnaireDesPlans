package GUI;

import entities.Plan;
import entities.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import services.UtilisateurService;
import static services.UtilisateurService.currentUser;
import services.planService;
import tools.MyConnexion;
import tools.StorageLocale;

public class ListeUtilisateursController implements Initializable {

    @FXML
    private Button btnLogout;
    @FXML
    private AnchorPane anchoreUpdate;
    @FXML
    private TextField txtRole;
    @FXML
    private Button brtn_fin;
    @FXML
    private AnchorPane anchorpane;
    private TextField txtAtelier;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupprimer;
    @FXML
    private TextField txtChercher;
    @FXML
    private TableView<Utilisateur> TableUtilisateurs;
    @FXML
    private TableColumn<Utilisateur, String> nomCol;
    @FXML
    private TableColumn<Utilisateur, String> prenomCol;
    @FXML
    private TableColumn<Utilisateur, String> adresseCol;
    @FXML
    private TableColumn<Utilisateur, String> emailCol;
    @FXML
    private TableColumn<Utilisateur, String> passwordCol;
    @FXML
    private TableColumn<Utilisateur, String> roleCol;
    @FXML
    private TableColumn<Utilisateur, String> cinCol;
    @FXML
    private TableColumn<Utilisateur, String> telephoneCol;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtAdresse;
    @FXML
    private Button btnModifier;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    private boolean update;

    String query;
    Connection cnx;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    @FXML
    private TextField txtCin;
    @FXML
    private TextField txtTelephone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();

    }

    ObservableList<Utilisateur> listeUtilisateurs = FXCollections.observableArrayList();

    public void loadData() {
        cnx = MyConnexion.getInstance().getCnx();
        refreshTable();
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

    }

    private void refreshTable() {

        try {
            listeUtilisateurs.clear();

            query = "SELECT * FROM `utilisateur`  ";
            preparedStatement = cnx.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Utilisateur u = new Utilisateur();
                u.setIdUser(resultSet.getInt("idUser"));
                u.setNom(resultSet.getString("nom"));
                u.setPrenom(resultSet.getString("prenom"));
                u.setAdressse(resultSet.getString("adresse"));
                u.setEmail(resultSet.getString("email"));
                u.setPassword(resultSet.getString("password"));
                u.setRole("user");
                u.setCin(resultSet.getString("cin"));
                u.setTelephone(resultSet.getString("telephone"));
                listeUtilisateurs.add(u);
                TableUtilisateurs.setItems(listeUtilisateurs);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onTableItemSelect(MouseEvent event) {

        this.setUpdate(true);
        Utilisateur user = TableUtilisateurs.getSelectionModel().getSelectedItem();
        this.setTextField(user.getNom(), user.getPrenom(), user.getAdressse(), user.getEmail(), user.getPassword(), user.getRole(), user.getCin(), user.getTelephone());
        Utilisateur tableUser = TableUtilisateurs.getSelectionModel().getSelectedItem();

    }

    void setTextField(String nom, String prenom, String adresse, String email, String password, String role, String cin, String telephone) {

        txtPassword.setText(password);
        txtNom.setText(nom);
        txtAdresse.setText(adresse);
        txtPrenom.setText(prenom);
        txtEmail.setText(email);
        txtCin.setText(cin);
        txtTelephone.setText(telephone);
    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    @FXML
    private void AjouterUtilisateur(ActionEvent event) {

        if ((txtPassword.getText()).isEmpty() || (txtNom.getText()).isEmpty() || (txtAdresse.getText()).isEmpty() || (txtPrenom.getText()).isEmpty() || (txtEmail.getText()).isEmpty() || (txtCin.getText()).isEmpty() || (txtTelephone.getText()).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Merci d'entré tous les coordonnées qui concerne l'utilisateur");
            alert.showAndWait();
        } else {
            Plan p = new Plan();
            Utilisateur u = new Utilisateur();

            u.setNom(txtNom.getText());
            u.setPrenom(txtPrenom.getText());
            u.setAdressse(txtAdresse.getText());
            u.setEmail(txtEmail.getText());
            u.setPassword(txtPassword.getText());
            u.setRole("user");
            u.setCin(txtCin.getText());
            u.setTelephone(txtTelephone.getText());

            UtilisateurService us = new UtilisateurService();
            us.ajouterUtilisateurs(u);
            JOptionPane.showMessageDialog(null, "L'utilisateur est ajouté avec succès !!");
            refreshTable();

        }
    }

    @FXML
    private void ModifierUtilisateur(ActionEvent event) {
        Utilisateur utilisateur = TableUtilisateurs.getSelectionModel().getSelectedItem();
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String adresse = txtAdresse.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = "user";
        String cin = txtCin.getText();
        String telephone = txtTelephone.getText();

        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setAdressse(adresse);
        u.setEmail(email);
        u.setPassword(password);
        u.setRole(role);
        u.setCin(cin);
        u.setTelephone(telephone);

        UtilisateurService us = new UtilisateurService();
        us.modifierUtilisateur(u, utilisateur.getCin());
        JOptionPane.showMessageDialog(null, "L'utilisateur est modifié  avec succès !!");

        refreshTable();

    }

    @FXML
    private void SupprimerUtilisateur(ActionEvent event) {

        Connection cnx = MyConnexion.getInstance().getCnx();
        Utilisateur TableUtilisateur = TableUtilisateurs.getSelectionModel().getSelectedItem();
        UtilisateurService us = new UtilisateurService();
        us.SupprimerUtilisateur(TableUtilisateur);
        JOptionPane.showMessageDialog(null, "Le plan est supprimer avec succès !!");
        refreshTable();
    }

    @FXML
    private void Logout(ActionEvent event) throws IOException {

        StorageLocale storageLocale = new StorageLocale();
        storageLocale.deleteStorage();
        UtilisateurService us = new UtilisateurService();
        us.CurrentuserLogOut();
        JOptionPane.showMessageDialog(null, "Utilisateur déconnecté(e) avec succès !!");

        FXMLLoader Loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = Loader.load();

        btnLogout.getScene().setRoot(root);

    }

}
