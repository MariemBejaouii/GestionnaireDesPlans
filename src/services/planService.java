package services;

import entities.Plan;
import entities.Utilisateur;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static services.UtilisateurService.currentUser;
import tools.MyConnexion;
import tools.notification;

public class planService {

    Connection cnx;
    notification nf = new notification();
    //  private EntityManager emf;
/////////::::teeessssssssst
    public planService() {
        cnx = MyConnexion.getInstance().getCnx();
    }
    // public static Utilisateur currentUser = new Utilisateur();

    public int ajouterPlan(Plan p) {
        int resultat = 0;
        String sql = "insert into `plans` (NumeroDePlan, codeArticle, designationPdff, Atelier,  BT, Date , idUser ) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, p.getNumeroDePlan());
            ste.setString(2, p.getCodeArticle());
            ste.setString(3, p.getDesignationPdff());
            ste.setString(4, p.getAtelier());
            ste.setString(5, p.getBT());
            ste.setDate(6, (Date) p.getDate());
            ste.setInt(7, currentUser.getIdUser());

            int value = ste.executeUpdate();
            if (value > 0) {
                System.out.println("plan ajouté avec succées from service");
                resultat = 1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return resultat;
    }

    public int modifierPlan(Plan p, String numero) {
        int resultat = 0;
        String sql = "update  plans set  NumeroDePlan=?, codeArticle=?, designationPdff=?, Atelier=?,  BT=?, Date=?  where NumeroDePlan =?    ";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, p.getNumeroDePlan());
            ste.setString(2, p.getCodeArticle());
            ste.setString(3, p.getDesignationPdff());
            ste.setString(4, p.getAtelier());
            ste.setString(5, p.getBT());
            ste.setDate(6, (Date) p.getDate());
            ste.setString(7, numero);

            int value = ste.executeUpdate();
            if (value > 0) {
                System.out.println("plan modifier avec succées from service");
                resultat = 1;
            } else {
                System.out.println("there is an error about update ");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return resultat;
    }

    public void SupprimerPlan(Plan p) {
        String sql = "Delete From plans where NumeroDePlan=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, p.getNumeroDePlan());
            int value = ste.executeUpdate();
            if (value > 0) {
                System.out.println("Plan supprimé avec succées");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Plan> afficherPlan() {
        List<Plan> planss = new ArrayList<>();
        String query = "Select idPlan,NumeroDePlan,codeArticle,designationPdff,Atelier,BT , Date , idUser from plans ";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Plan p = new Plan();
                p.setIdPlan(rs.getInt("idPlan"));
                p.setNumeroDePlan(rs.getString("NumeroDePlan"));
                p.setCodeArticle(rs.getString("codeArticle"));
                p.setDesignationPdff(rs.getString("designationPdff"));
                p.setAtelier(rs.getString("Atelier"));
                p.setBT(rs.getString("BT"));
                p.setDate(rs.getDate("Date"));
                p.setIdUser(rs.getInt("idUser"));

                planss.add(p);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return planss;

    }

    public List<Plan> afficherPlanParAdmin() {
        List<Plan> planss = new ArrayList<>();
        String query = "Select idPlan,NumeroDePlan,codeArticle,designationPdff,Atelier,BT , Date , idUser from plans where idUser =? ";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Plan p = new Plan();
                p.setIdPlan(rs.getInt("idPlan"));
                p.setNumeroDePlan(rs.getString("NumeroDePlan"));
                p.setCodeArticle(rs.getString("codeArticle"));
                p.setDesignationPdff(rs.getString("designationPdff"));
                p.setAtelier(rs.getString("Atelier"));
                p.setBT(rs.getString("BT"));
                p.setDate(rs.getDate("Date"));
                p.setIdUser(currentUser.getIdUser());

                planss.add(p);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return planss;

    }

}
