/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.AjoutSupport;
import beans.Professeur;
import beans.Support;
import connexion.Connexion;
import dao.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Moi
 */
public class AjoutSupportService implements IDao<AjoutSupport> {
    private Connexion connexion;
    
    private SupportService ss;
    private ProfesseurService ts; // teacherservice car il y'avais une confusion avec ps dans preparedstatement
    
    public AjoutSupportService(){
        connexion = Connexion.getInstance();
        
        ss = new SupportService();
        ts = new ProfesseurService();
    }

    
    @Override
    public boolean create(AjoutSupport o) {
        String req = "INSERT INTO ajoutsupport (support_id, professeur_id, date_ajout) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setInt(1, o.getSupport().getId());
            ps.setInt(2, o.getProfesseur().getId());
            ps.setDate(3, new java.sql.Date(o.getDateAjout().getTime()));
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(AjoutSupport o) {
        String req = "DELETE FROM ajoutsupport WHERE support_id = ? AND professeur_id = ?";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setInt(1, o.getSupport().getId());
            ps.setInt(2, o.getProfesseur().getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(AjoutSupport o) {
        String req = "UPDATE ajoutsupport SET date_ajout = ? WHERE support_id = ? AND professeur_id = ?";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ps.setDate(1, new java.sql.Date(o.getDateAjout().getTime()));
            ps.setInt(2, o.getSupport().getId());
            ps.setInt(3, o.getProfesseur().getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public AjoutSupport findById(int id) {
        // Cette approche ne peut pas être appliquée, car ajoutsupport ne possède pas d'identifiant unique
        return null;
    }

    @Override
    public List<AjoutSupport> findAll() {
        List<AjoutSupport> ajouts = new ArrayList<>();
        String req = "SELECT * FROM ajoutsupport";
        try {
            PreparedStatement ps = connexion.getCn().prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Support support = ss.findById(rs.getInt("support_id"));
                Professeur professeur = ts.findById(rs.getInt("professeur_id"));
                Date date_ajout = rs.getDate("date_ajout");
                ajouts.add(new AjoutSupport(support, professeur, date_ajout));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ajouts;
    }
    
    
    
}
