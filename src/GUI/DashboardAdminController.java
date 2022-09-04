package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import services.UtilisateurService;
import static services.UtilisateurService.currentUser;
import tools.StorageLocale;

public class DashboardAdminController implements Initializable {

    @FXML
    private AnchorPane Dashboradadmin;
    @FXML
    private Label txtUsername;
    @FXML
    private AnchorPane Content;
    @FXML
    private Button btnAfficherPlans;
    @FXML
    private Button btnUsers;
    @FXML
    private Button btnDeconnecter;
    @FXML
    private PieChart pieChart;

    public AnchorPane getDashboradadmin() {
        return Dashboradadmin;
    }

    public void setDashboradadmin(AnchorPane Dashboradadmin) {
        this.Dashboradadmin = Dashboradadmin;
    }

    UtilisateurService us = new UtilisateurService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtUsername.setText(currentUser.getPrenom() + " " + currentUser.getNom());

    }

    public void loadUI(String ui) {
        AnchorPane root;
        try {
            root = FXMLLoader.load(getClass().getResource(ui));
            Content.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void GestionPlans(ActionEvent event) {

        loadUI("GestionPlan.fxml");
    }

    @FXML
    private void GestionUtilisateurs(ActionEvent event) {
        loadUI("listeUtilisateurs.fxml");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {

        StorageLocale storageLocale = new StorageLocale();
        storageLocale.deleteStorage();
        UtilisateurService us = new UtilisateurService();
        us.CurrentuserLogOut();
        JOptionPane.showMessageDialog(null, "Utilisateur déconnecté(e) avec succès !!");

        FXMLLoader Loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = Loader.load();

        btnDeconnecter.getScene().setRoot(root);

    }

}
