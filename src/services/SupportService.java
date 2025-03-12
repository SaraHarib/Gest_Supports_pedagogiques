/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.Etype;
import beans.Support;
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
public class SupportService implements IDao<Support> {
    private Connexion connexion ;

    public SupportService() {
        connexion = Connexion.getInstance();
}

    @Override
    public boolean create(Support o) {
        String req = "INSERT INTO Support (id, titre, type, fichier) VALUES (null, ?, ?, ?)"; 
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setString(1, o.getTitre());
            ps.setString(2, o.getType().toString());
            ps.setString(3, o.getFichier());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    

    @Override
    public boolean delete(Support o) {
        String req = "DELETE FROM Support WHERE id = ?"; 
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
    public boolean update(Support o) {
        String req = "UPDATE Support set titre = ?, type = ?, fichier = ? WHERE id  = ?"; 
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setString(1, o.getTitre());
            ps.setString(2, o.getType().toString());
            ps.setString(3, o.getFichier());
            ps.setInt(4, o.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public Support findById(int id) {
        String req = "SELECT * FROM Support WHERE id  = ?"; 
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Support(rs.getInt("id"), rs.getString("titre"), Etype.valueOf(rs.getString("type")), rs.getString("fichier") );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Support> findAll() {
         List<Support>  supports = new ArrayList<>();
        String req = "SELECT DISTINCT * from Support"; 
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                supports.add(new Support(rs.getInt("id"), rs.getString("titre"),Etype.valueOf(rs.getString("type")), rs.getString("fichier")));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return supports;
    }
    public List<Support> findByType(Etype type) {
    List<Support> supports = new ArrayList<>();
    String req = "SELECT * FROM support WHERE type = ?";
    try (PreparedStatement ps = connexion.getCn().prepareStatement(req)) {
        ps.setString(1, type.name());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            supports.add(new Support(rs.getInt("id"), rs.getString("titre"), Etype.valueOf(rs.getString("type")), rs.getString("fichier")));
        }
    } catch (SQLException ex) {
        System.out.println("Erreur SQL : " + ex.getMessage());
    }
    return supports;
}
    
    public Support findByTitre(String titre) {
    String req = "SELECT * FROM support WHERE titre = ?";
    try (PreparedStatement ps = connexion.getCn().prepareStatement(req)) {
        ps.setString(1, titre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Support(rs.getInt("id"), rs.getString("titre"), Etype.valueOf(rs.getString("type")), rs.getString("fichier"));
        }
    } catch (SQLException ex) {
        System.out.println("Erreur SQL : " + ex.getMessage());
    }
    return null;
}


}