/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Moi
 */
public class Connexion {
    private static Connexion instance = null;
    private Connection cn = null;
    
    private final String url = "gestion_supports:mysql://localhost:3306/gestion_supports";
    private final String login = "root";
    private final String password = "";
    
    private Connexion(){
        try{
            Class.forName("com.mysql.gestion_supports.Driver");
            cn = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException ex){
            System.out.println("Driver introuvable");
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    private static synchronized Connexion getInstance(){
        if (instance == null){
            instance = new Connexion();
        }
        return instance;
    }

    public Connection getCn() {
        return cn;
    }
    
}
