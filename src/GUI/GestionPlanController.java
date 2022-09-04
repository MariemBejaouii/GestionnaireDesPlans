package GUI;

import entities.Plan;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import services.UtilisateurService;
import static services.UtilisateurService.currentUser;
import services.planService;
import tools.MyConnexion;
import tools.StorageLocale;
import tools.notification;

public class GestionPlanController implements Initializable {

    @FXML
    private AnchorPane anchoreUpdate;
    @FXML
    private TextField txtRole;
    @FXML
    private Button brtn_fin;
    @FXML
    private Button btnAjouter;
    @FXML
    private TableView<Plan> TablePlan;
    @FXML
    private TableColumn<Plan, String> NumPlanCol;
    @FXML
    private TableColumn<Plan, String> dateCol;
    @FXML
    private TableColumn<Plan, String> pdfCol;
    @FXML
    private TableColumn<Plan, String> codeCol;
    @FXML
    private TableColumn<Plan, String> BTCol;
    @FXML
    private TableColumn<Plan, String> atelierCol;

    String query;
    Connection cnx;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtCodeArticle;
    @FXML
    private TextField txtNumeroPlan;
    @FXML
    private TextField txtBT;
    @FXML
    private TextField txtAtelier;
    @FXML
    private Button btnUpload;
    @FXML
    private TextField pdfPath;
    @FXML
    private AnchorPane anchorpane;
    private boolean update;
    @FXML
    private Button btnSupprimer;
    notification nf = new notification();
    private Button btnLogout;
    @FXML
    private TextField txtChercher;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        recherche();
    }
    ObservableList<Plan> PlansList = FXCollections.observableArrayList();

    public void loadData() {
        cnx = MyConnexion.getInstance().getCnx();
        refreshTable();
        NumPlanCol.setCellValueFactory(new PropertyValueFactory<>("NumeroDePlan"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        pdfCol.setCellValueFactory(new PropertyValueFactory<>("designationPdff"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("codeArticle"));
        BTCol.setCellValueFactory(new PropertyValueFactory<>("BT"));
        atelierCol.setCellValueFactory(new PropertyValueFactory<>("Atelier"));
        refreshTable();
    }

    private void refreshTable() {

        try {
            PlansList.clear();

            query = "SELECT * FROM `plans` where idUser ='" + currentUser.getIdUser() + "'";
            preparedStatement = cnx.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PlansList.add(new Plan(
                        resultSet.getInt("idPlan"),
                        resultSet.getString("designationPdff"),
                        resultSet.getString("Atelier"),
                        resultSet.getString("BT"),
                        resultSet.getString("NumeroDePlan"),
                        resultSet.getString("codeArticle"),
                        resultSet.getDate("Date")));

                TablePlan.setItems(PlansList);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void uploaadPdf(ActionEvent event) throws IOException {

        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Open file dialog");
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        File file = filechooser.showOpenDialog(stage);
        pdfPath.setText(file.getPath());

    }

    @FXML
    private void AjouterPlan(ActionEvent event) {

        if ((pdfPath.getText()).isEmpty() || (txtCodeArticle.getText()).isEmpty() || (txtNumeroPlan.getText()).isEmpty() || (txtBT.getText()).isEmpty() || (txtAtelier.getText()).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Merci d'entré tous les coordonnées qui concerne le plan");
            alert.showAndWait();
        } else {
            Plan p = new Plan();

            p.setDate(java.sql.Date.valueOf(txtDate.getValue()));
            p.setDesignationPdff(this.pdfPath.getText());
            p.setCodeArticle(txtCodeArticle.getText());
            p.setNumeroDePlan(txtNumeroPlan.getText());
            p.setBT(txtBT.getText());
            p.setAtelier(txtAtelier.getText());
            planService ps = new planService();
            ps.ajouterPlan(p);
            JOptionPane.showMessageDialog(null, "Le plan est ajouté avec succès !!");
            //  nf.addNotifications("Confirmation", "Le plan est ajouté avec succès !!");
            refreshTable();

        }
    }

    @FXML
    private void onTableItemSelect(MouseEvent event) {

        this.setUpdate(true);
        Plan plans = TablePlan.getSelectionModel().getSelectedItem();
        this.setTextField(plans.getNumeroDePlan(), plans.getDate(), plans.getCodeArticle(), plans.getDesignationPdff(), plans.getAtelier(), plans.getBT());
        Plan tablePlan = TablePlan.getSelectionModel().getSelectedItem();

    }

    void setTextField(String numero, Date dateDepo, String article, String pdf, String atelier, String bt) {

        txtNumeroPlan.setText(numero);
        txtCodeArticle.setText(article);
        pdfPath.setText(pdf);
        txtAtelier.setText(atelier);
        txtBT.setText(bt);
//this.java.sql.Date.valueOf(txtDate.getValue());     

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    @FXML
    private void ModiferPlan(ActionEvent event) {
        Plan plans = TablePlan.getSelectionModel().getSelectedItem();
        String numeroPlan = txtNumeroPlan.getText();
        String atelier = txtCodeArticle.getText();
        String pdf = pdfPath.getText();
        String bt = txtBT.getText();
        Date datee = java.sql.Date.valueOf(txtDate.getValue());
        String code = txtCodeArticle.getText();

        Plan p = new Plan();
        p.setDate(java.sql.Date.valueOf(txtDate.getValue()));
        p.setDesignationPdff(this.pdfPath.getText());
        p.setCodeArticle(txtCodeArticle.getText());
        p.setNumeroDePlan(txtNumeroPlan.getText());
        p.setBT(txtBT.getText());
        p.setAtelier(txtAtelier.getText());
        String numberOfPlan = plans.getNumeroDePlan();
        planService ps = new planService();
        ps.modifierPlan(p, numberOfPlan);
        JOptionPane.showMessageDialog(null, "Le plan est modifié  avec succès !!");

        refreshTable();
    }

    @FXML
    private void SupprimerPlan(ActionEvent event) {

        Connection cnx = MyConnexion.getInstance().getCnx();
        Plan tablePlan = TablePlan.getSelectionModel().getSelectedItem();
        planService ps = new planService();
        ps.SupprimerPlan(tablePlan);
        JOptionPane.showMessageDialog(null, "Le plan est supprimer avec succès !!");
        refreshTable();

    }

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

    //observalble list to store data
    private final ObservableList<Plan> dataList = FXCollections.observableArrayList();

    private void recherche() {

        List<Plan> results = new ArrayList<>();
        planService ps = new planService();
        results = ps.afficherPlan();
        dataList.addAll(results);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Plan> filteredData = new FilteredList<>(dataList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        txtChercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(plan -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (plan.getDesignationPdff().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (plan.getAtelier().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (plan.getBT().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (plan.getCodeArticle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Plan> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(TablePlan.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TablePlan.setItems(sortedData);

    }

}
