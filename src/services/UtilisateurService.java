package services;

import entities.Utilisateur;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import tools.MyConnexion;
import tools.StorageLocale;
import tools.notification;

public class UtilisateurService {

    Connection cnx;
    notification nf = new notification();

    public UtilisateurService() {
        cnx = MyConnexion.getInstance().getCnx();
    }
    public static Utilisateur currentUser = new Utilisateur();

    
    
      public String Seconnecter(String email, String password	, boolean rememberMe) throws IOException {
        String sql = "Select * from utilisateur where email =?";
        String result = "";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);

            ste.setString(1, email);
            ResultSet rs = ste.executeQuery();
            if (rs.next()) {
                String pwd = rs.getString("password");
               // int isValidate = rs.getInt("isverified");
                String pwd2 = mdpconvert(password);
                
                    if (pwd2.equals(pwd)) {
                Utilisateur user = new Utilisateur();
               user.setIdUser(rs.getInt("idUser"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setAdressse(rs.getString("adresse"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCin(rs.getString("cin"));
                user.setTelephone(rs.getString("telephone"));
                Session(user);
                result = rs.getString("cin");
                        if (rememberMe) {
                            System.out.println("clicked remember me ");
                            try {
                                StorageLocale Storage = new StorageLocale();
                                Storage.WriteIntoLocalStorage(currentUser);
                            } catch (IOException ex) {
                                System.out.println("erreur de storé :" + ex.getMessage());
                            }
                        }
                    } else {
                        nf.addNotifications("Erreur", " Mot de passe incorrecte");

                    }
               

            } else {

                nf.addNotifications("Erreur", "Email introuvable");

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return result;

    }

    public void Session(Utilisateur user) {
        currentUser.setIdUser(user.getIdUser());
        currentUser.setNom(user.getNom());
        currentUser.setPrenom(user.getPrenom());
        currentUser.setAdressse(user.getAdressse());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setRole(user.getRole());
        currentUser.setCin(user.getCin());
        currentUser.setTelephone(user.getTelephone());

    }

    //Fonction de hachage mot de passe md5 
    public String mdpconvert(String p) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(p.getBytes());
            BigInteger pwd = new BigInteger(1, messageDigest);
            String hashpwd = pwd.toString(16);
            return hashpwd;

        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean getUtilisateurByEmail(String email) {
        boolean exist = false;

        try {
            String sql = "SELECT * FROM utilisateur where email=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, email);

            ResultSet rs = ste.executeQuery();//resultat requete sql
            if (rs.first()) {
                exist = true;
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return exist;

    }

    public String getRoleByCin(String cin) throws SQLException {

        String role = null;
        try {
            String sql = "SELECT `role FROM `utilisateur` WHERE `cin` ='" + cin + "'";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();

            while (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return role;

    }

    public boolean getRoleUtilisateur(String cin) {
        boolean exist = false;

        try {
            String sql = "SELECT * FROM utilisateurs where cinUser=?";
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, cin);

            ResultSet rs = ste.executeQuery();//resultat requete sql
            if (rs.first()) {
                exist = true;
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return exist;
    }

    public int ajouterUtilisateurs(Utilisateur U) {
        int resultat = 0;
        String sql = "insert into `utilisateur` (nom, prenom, adresse, email, password, role, cin, telephone) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, U.getNom());
            ste.setString(2, U.getPrenom());
            ste.setString(3, U.getAdressse());
            ste.setString(4, U.getEmail());
            //   ste.setString(7, mdpconvert(U.getPassword()));
            ste.setString(5, mdpconvert(U.getPassword()));
            ste.setString(6, U.getRole());
            ste.setString(7, U.getCin());
            ste.setString(8, U.getTelephone());
            //ste.setInt(9, U.getIsverified());

            int value = ste.executeUpdate();
            if (value > 0) {
                System.out.println("utilisateur ajouté avec succées");
                resultat = 1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return resultat;
    }

    public int modifierUtilisateur(Utilisateur U , String cin) {
        int resultat = 0;
        String sql = "update  utilisateur set nom=?, prenom=?, adresse=?, email=?, password=?, role=?, cin=?, telephone=? where cin =?" ;
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, U.getNom());
            ste.setString(2, U.getPrenom());
            ste.setString(3, U.getAdressse());
            ste.setString(4, U.getEmail());
            ste.setString(5, mdpconvert(U.getPassword()));
            ste.setString(6, U.getRole());
            ste.setString(7, U.getCin());
            ste.setString(8, U.getTelephone());
           ste.setString(9, cin);

            int value = ste.executeUpdate();
            if (value > 0) {
                System.out.println("utilisateur modifié avec succées");
                resultat = 1;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return resultat;
    }

    public void SupprimerUtilisateur(Utilisateur u) {
        String sql = "Delete From utilisateur where cin=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, u.getCin());
            int value = ste.executeUpdate();
            if (value > 0) {
                System.out.println("Utilisateur supprimé avec succées");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Utilisateur> afficherUtilisateur() {
        List<Utilisateur> users = new ArrayList<>();
        String query = "Select idUser,nom,prenom,adresse,email,password,role , cin , telephone from utilisateur";
        try {
            PreparedStatement ste = cnx.prepareStatement(query);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setIdUser(rs.getInt("idUser"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setAdressse(rs.getString("adresse"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setCin(rs.getString("cin"));
                u.setTelephone(rs.getString("telephone"));
                users.add(u);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;

    }

    public void CurrentuserLogOut() {
        currentUser.setNom("");
        currentUser.setEmail("");
        currentUser.setCin("");

    }

}
