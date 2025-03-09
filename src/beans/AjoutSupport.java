/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author Moi
 */
public class AjoutSupport {
    private int id;
    private Support support;
    private Professeur professeur;
    private Date dateAjout;

    public AjoutSupport(int id, Support support, Professeur professeur, Date dateAjout) {
        this.id = id;
        this.support = support;
        this.professeur = professeur;
        this.dateAjout = dateAjout;
    }

    public AjoutSupport(Support support, Professeur professeur, Date dateAjout) {
        this.support = support;
        this.professeur = professeur;
        this.dateAjout = dateAjout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }
    
    
    
    
    
}
