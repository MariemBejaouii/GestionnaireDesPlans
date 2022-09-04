
package entities;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Utilisateur {

    String nom, prenom, adressse, email, password, role, cin, telephone;
    //   Date date; 
    int idUser;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String adressse, String email, String password, String role, String cin, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.adressse = adressse;
        this.email = email;
        this.password = password;
        this.role = role;
        this.cin = cin;

        this.telephone = telephone;
    }

    public Utilisateur(int idUser , String nom, String prenom, String adressse, String email, String password, String role, String cin, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.adressse = adressse;
        this.email = email;
        this.password = password;
        this.role = role;
        this.cin = cin;
        this.telephone = telephone;
        this.idUser = idUser;
    }
    
    

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdressse() {
        return adressse;
    }

    public void setAdressse(String adressse) {
        this.adressse = adressse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "nom=" + nom + ", prenom=" + prenom + ", adressse=" + adressse + ", email=" + email + ", password=" + password + ", role=" + role + ", cin=" + cin + ", telephone=" + telephone + '}';
    }

}
