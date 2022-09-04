package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class MyConnexion {

    public String url = "jdbc:mysql://localhost:3306/cimenterieproject";
    public String user = "root";
    public String pwd = "";

    private Connection cnx;
    public static MyConnexion ct;

    private MyConnexion() {
        try {
            cnx = DriverManager.getConnection(url, user, pwd);
            System.out.print("Connection etablie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public static MyConnexion getInstance() {
        if (ct == null) {
            ct = new MyConnexion();
        }
        return ct;

    }

    public Connection getCnx() {
        return cnx;
    }

}