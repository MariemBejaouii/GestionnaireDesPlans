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
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import services.UtilisateurService;
import tools.StorageLocale;

public class DashboardUtilisateurController implements Initializable {

    @FXML
    private Button btnAfficherPlans;
    @FXML
    private Button btnDeconnecter;
    @FXML
    private AnchorPane Content;
    @FXML
    private PieChart pieChart;
    @FXML
    private AnchorPane DashboardUtilisateurr;

    public AnchorPane getDashboradUtilisateur() {
        return DashboardUtilisateurr;
    }

    public void setDashboradUtilisateur(AnchorPane DashboardUtilisateurr) {
        this.DashboardUtilisateurr = DashboardUtilisateurr;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void logout(ActionEvent event)
            throws IOException {

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
