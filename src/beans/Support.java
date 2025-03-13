/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Moi
 */
public class Support {

    private int id;
    private String titre;
    private Etype type;
    private String fichier;

    public Support(int id, String titre, Etype type, String fichier) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.fichier = fichier;
    }

    public Support(String titre, Etype type, String fichier) {
        this.titre = titre;
        this.type = type;
        this.fichier = fichier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Etype getType() {
        return type;
    }

    public void setType(Etype type) {
        this.type = type;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

}
