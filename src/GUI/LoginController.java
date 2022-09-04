package GUI;

import entities.Utilisateur;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.UtilisateurService;
import static services.UtilisateurService.currentUser;
import tools.AttributeValide;
import tools.notification;
import javafx.scene.paint.Color;

public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private Button btn_seConnecter;
    @FXML
    private Button btnSinscrire;
    @FXML
    private Button btn_fb;
    @FXML
    private Label Error;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private CheckBox remember;
    @FXML
    private Label close;
    @FXML
    private AnchorPane loginAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //loginAnchorPane.setMaxSize(839, 736);

    }
    notification nf = new notification();

    AttributeValide at = new AttributeValide();

    @FXML
    private void signIn() throws SQLException, IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty()) {
            setLblError(Color.RED, "Entrer le champs email!");

        } else if (password.isEmpty()) {
            setLblError(Color.RED, "Entrer le champs mot de passe!");
        }
        if (email.isEmpty() && password.isEmpty()) {
            setLblError(Color.RED, "Entrer le champs email et le champs mot de passe");
        } else if (password.isEmpty()) {
            setLblError(Color.RED, "Entrer le champs password!");
        } else if (at.EmailVerified(email) == false) {
            nf.addNotifications("Erreur", "Email format est invalide exemple : xyz@gmail.com");
        } else {
            UtilisateurService us = new UtilisateurService();
            Error.setVisible(false);

            String output = us.Seconnecter(email, password, remember.isSelected());
            if (output.isEmpty() == false) {

            }
            if (currentUser.getRole().equals("admin")) {
                Stage stage = (Stage) txtPassword.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardAdmin.fxml"));
                Parent root = loader.load();
                Stage stageprofil = new Stage();
                // stageprofil.setTitle("Dashborad Admin");
                stageprofil.setScene(new Scene(root, 1200, 1000));
                stageprofil.show();
            } else if (currentUser.getRole().equals("user")) {
                Stage stage = (Stage) txtPassword.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardUtilisateur.fxml"));
                Parent root = loader.load();
                Stage stageprofil = new Stage();
                // stageprofil.setTitle("Dashborad user");
                stageprofil.setScene(new Scene(root, 1100, 800));
                stageprofil.show();
            }
        }

    }

    private void setLblError(Color color, String text) {
        Error.setVisible(true);
        Error.setTextFill(color);
        Error.setText(text);
        System.out.println(text);
    }

    @FXML

    private void Sinscrire(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));

        try {
            Parent root = loader.load();
            txtEmail.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void Close(javafx.scene.input.MouseEvent event) {

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

}
