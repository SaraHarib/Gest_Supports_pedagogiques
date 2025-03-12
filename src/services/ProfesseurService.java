/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.Professeur;
import connexion.Connexion;
import dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Moi
 */
public class ProfesseurService implements IDao<Professeur> {
    private Connexion connexion;
    
    public ProfesseurService(){
        connexion = Connexion.getInstance();
    }
    
    public Professeur findByEmail(String email) {
    String req = "SELECT * FROM Professeur WHERE email = ?";
    try (PreparedStatement ps = connexion.getCn().prepareStatement(req)) {
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Professeur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"));
        }
    } catch (SQLException ex) {
        System.out.println("Erreur SQL : " + ex.getMessage());
    }
    return null;
}

    @Override
    public boolean create(Professeur o) {
        if (findByEmail(o.getEmail()) != null) {
        System.out.println("Erreur : Un professeur avec cet email existe déjà !");
        return false;
    }
        String req =  "INSERT INTO Professeur (nom, prenom, email) VALUES (?, ?, ?)";
        try{
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getEmail());
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Professeur o) {
        String req = "DELETE FROM Professeur WHERE id = ?";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setInt(1, o.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Professeur o) { 
        String req = "UPDATE Professeur SET nom = ?, prenom = ?, email = ? WHERE id = ?";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getEmail());
            ps.setInt(4, o.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public Professeur findById(int id) {
        String req = "SELECT * FROM Professeur WHERE id = ?";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Professeur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Professeur> findAll() {
        List<Professeur> professeurs = new ArrayList<>();
        String req = "SELECT * FROM Professeur";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                professeurs.add(new Professeur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return professeurs;
    }
    
}
