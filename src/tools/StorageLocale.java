package tools;

import entities.Utilisateur;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class StorageLocale {
    
     private String fileName = "LocaleStorage.txt";

    public StorageLocale() throws IOException {
        this.initStorageLocale();
    }

    public File initStorageLocale() throws IOException {

        File Object= new File(fileName);
        if (Object.createNewFile()) {
            System.out.println("Fichier crée: " + Object.getName());
        } else {
            System.out.println("storage init.");
        }
        return Object;
    }

    public void WriteIntoLocalStorage(Utilisateur user) throws IOException {

        FileWriter writer = new FileWriter(fileName);
        JSONObject objuser = new JSONObject();
        objuser.put("idUser", user.getIdUser());
        objuser.put("nom", user.getNom());
        objuser.put("prenom", user.getPrenom());
        objuser.put("adressse", user.getAdressse());
        objuser.put("email", user.getEmail());
        objuser.put("password", user.getPassword());
        objuser.put("role", user.getRole());
        objuser.put("cin", user.getCin());
        objuser.put("telephone", user.getTelephone());


        System.out.println( "hhhhhhhhh" +objuser.toJSONString());

        writer.write(objuser.toJSONString());
        writer.close();
        System.out.println("Ajoutée avec succées userConnecté !!");
    }

    public Utilisateur getUser() throws FileNotFoundException {
        Utilisateur user = new Utilisateur();

        File Object = new File(fileName);
        Scanner myReader = new Scanner(Object);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            Object obj = JSONValue.parse(data);
            JSONObject json = (JSONObject) obj;
            user = getUserFromJSON(json);
        }
        myReader.close();
        return user;
    }

    public Utilisateur getUserFromJSON(JSONObject json) {
        Utilisateur user = new Utilisateur();
        long idUser = (Long) json.get("idUser");
     
        String nom = (String) json.get("nom");
        String prenom = (String) json.get("prenom");
        String email = (String) json.get("email");
        String password = (String) json.get("password");
        String adresse= (String) json.get("adresse");
        String role = (String) json.get("role");
           String cin = (String) json.get("cin");
           String telephone = (String) json.get("telephone");
           


        user.setIdUser(Math.toIntExact(idUser));
    
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(password);
        user.setAdressse(adresse);
        user.setRole(role);
        user.setCin(cin);   
        user.setTelephone(telephone);
        return user;

    }

    public void deleteStorage() {
        File Object = new File(fileName);
        if (Object.delete()) {
            System.out.println("LocaleStorage supprimé: " + Object.getName());
        } else {
            System.out.println(" erreur");
        }
    }
    
} 



