package GUI;

import entities.Utilisateur;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import services.UtilisateurService;
import tools.AttributeValide;
import tools.notification;

public class RegistrationController implements Initializable {

    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtadresse;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtNom;
    @FXML
    private Button btn_inscrit;
    @FXML
    private ComboBox<String> combo;
    private PasswordField txtConfmdp;
    @FXML
    private PasswordField txtmdp;
    @FXML
    private TextField txtcin;
    @FXML
    private Label close;

    /**
     * Initializes the controller class.
     */
    UtilisateurService us = new UtilisateurService();
    notification nf = new notification();

    AttributeValide at = new AttributeValide();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> Role = FXCollections.observableArrayList("admin", "user");
        combo.setItems(Role);
        combo.getSelectionModel().selectFirst();

    }

    @FXML
    private void Inscription(ActionEvent event) {

        UtilisateurService us = new UtilisateurService();
        if (at.verifierNumeroCin(txtcin.getText()) == false) {
            JOptionPane.showMessageDialog(null, "Le Cin doit contenir 8 chiffres exactement", "Error", ERROR_MESSAGE);
        }
        if ((txtNom.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le nom est un champs obligatoire", "Error", ERROR_MESSAGE);
        }
        if ((txtPrenom.getText()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le prenom est un champs obligatoire", "Error", ERROR_MESSAGE);
        } else if (at.verifierNumeroTel(txtTel.getText()) == false) {
            JOptionPane.showMessageDialog(null, "Le numéro de telephone doit contenir 8 chiffres exactement", "Error", ERROR_MESSAGE);
        } else if (at.EmailVerified(txtemail.getText()) == false) {
            JOptionPane.showMessageDialog(null, "l'email doit etre comme xyz@abc.com", "Error", ERROR_MESSAGE);
        } else if (us.getUtilisateurByEmail(txtemail.getText()) == true) {
            JOptionPane.showMessageDialog(null, "l'email est utilisé déjà ! Inserer un autre", "Error", ERROR_MESSAGE);
        } else if (us.getRoleUtilisateur(txtcin.getText()) == true) {
            JOptionPane.showMessageDialog(null, " Numero Cin existe dejà! Inserer un autre", "Error", ERROR_MESSAGE);

        } else {

            if (txtcin.getText().isEmpty() == false && txtadresse.getText().isEmpty() == false
                    && txtemail.getText().isEmpty() == false && txtNom.getText().isEmpty() == false && txtPrenom.getText().isEmpty() == false
                    && txtmdp.getText().isEmpty() == false
                    && txtTel.getText().isEmpty() == false) {
//                if (txtConfmdp.getText().equals(txtmdp.getText()) == true) {
                String role = (String) combo.getSelectionModel().getSelectedItem();
                if (role.equals("admin")) {
                    Utilisateur admin = new Utilisateur();
                    admin.setNom(this.txtNom.getText());
                    admin.setPrenom(this.txtPrenom.getText());
                    admin.setCin(this.txtcin.getText());
                    admin.setTelephone(this.txtTel.getText());
                    admin.setAdressse(this.txtadresse.getText());
                    admin.setEmail(this.txtemail.getText());
                    admin.setPassword(this.txtmdp.getText().toString());

                    admin.setRole(role);

                    int rsultat = us.ajouterUtilisateurs(admin);

                    if (rsultat == 1) {
                        //  nf.addNotifications("Confirmation", "Admin ajouté avec succées");
                        JOptionPane.showMessageDialog(null, "Admin ajouté avec succées");

                    }
                } else if (role.equals("user")) {
                    Utilisateur userNormal = new Utilisateur();
                    userNormal.setNom(this.txtNom.getText());
                    userNormal.setPrenom(this.txtPrenom.getText());
                    userNormal.setAdressse(this.txtadresse.getText());
                    userNormal.setEmail(this.txtemail.getText());
                    userNormal.setPassword(txtmdp.getText());
                    userNormal.setRole(role);
                    userNormal.setCin(this.txtcin.getText());
                    userNormal.setTelephone(this.txtTel.getText());

                    int rsultat = us.ajouterUtilisateurs(userNormal);

                    if (rsultat == 1) {
                        //    nf.addNotifications("Confirmation", "Utilisateur ajouté avec succées");
                        JOptionPane.showMessageDialog(null, "Utilisateur ajouté avec succées");

                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Les mots de passe ne sont identiques!", "Error", ERROR_MESSAGE);

                }

            } else {
                JOptionPane.showMessageDialog(null, " Inserer le champ  vide", "Error", ERROR_MESSAGE);

            }
        }
    }

    @FXML
    private void Close(javafx.scene.input.MouseEvent event) {

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();

    }

}
