/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import beans.AjoutSupport;
import beans.Etype;
import beans.Professeur;
import beans.Support;
import connexion.Connexion;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import services.AjoutSupportService;
import services.ProfesseurService;
import services.SupportService;

/**
 *
 * @author Moi
 */
public class Test {

    public static void main(String[] args) {
        Connection testConn = Connexion.getInstance().getCn();
        if (testConn != null) {
            System.out.println("Connexion à la base de données réussie !");
        } else {
            System.out.println("Échec de la connexion.");
        }

        SupportService ss = new SupportService();
        ProfesseurService ts = new ProfesseurService();
        AjoutSupportService ass = new AjoutSupportService();

        ss.create(new Support("Programmation Java", Etype.PRESENTATION, "java.ppt"));
        ss.create(new Support("Tp C++", Etype.PDF, "Tp.pdf"));

        Support support = ss.findById(1);
        if (support != null) {
            System.out.println("Support trouvé : " + support.getTitre());
        }

        support.setFichier("java2.pdf");
        ss.update(support);
        System.out.println("Le fichier du support mis à jour : " + ss.findById(1).getFichier());

        List<Support> supports = ss.findAll();
        System.out.println("\nLa liste des supports : ");
        for (Support s : supports) {
            System.out.println(s.getTitre() + " / " + s.getType() + " / " + s.getFichier());
        }

        ts.create(new Professeur("Harib", "Sara", "sara@gmail.com"));
        ts.create(new Professeur("Garid", "Soukaina", "soukaina@gmail.com"));

        Professeur professeur = ts.findById(1);
        if (professeur != null) {
            System.out.println("Professeur trouvé : " + professeur.getNom() + " " + professeur.getPrenom());
        }

        professeur.setEmail("Harib@gmail.com");
        ts.update(professeur);
        System.out.println("Email du professeur mis à jour : " + ts.findById(1).getEmail());

        List<Professeur> professeurs = ts.findAll();
        System.out.println("\nLa liste des professeurs : ");
        for (Professeur e : professeurs) {
            System.out.println(e.getNom() + " " + e.getPrenom() + " - " + e.getEmail());
        }

        ass.create(new AjoutSupport(ss.findById(1), ts.findById(1), new Date()));
        ass.create(new AjoutSupport(ss.findById(2), ts.findById(2), new Date()));

        List<AjoutSupport> ajouts = ass.findAll();
        System.out.println("\nLa liste des supports ajoutés : ");
        for (AjoutSupport as : ajouts) {
            System.out.println("Ajout : " + as.getProfesseur().getNom() + " à ajouté le support  " + as.getSupport().getTitre());
        }

        AjoutSupport supprimerAjout = ass.findAll().get(0);
        ass.delete(supprimerAjout);
        System.out.println("Ajout supprimé : " + supprimerAjout.getProfesseur().getNom() + " / " + supprimerAjout.getSupport().getTitre());

        System.out.println("\nAjouts existants :");
        for (AjoutSupport as : ass.findAll()) {
            System.out.println("Ajout : " + as.getProfesseur().getNom() + " a ajouté le support " + as.getSupport().getTitre());
        }

        System.out.println("\nListe des supports de type PDF :");
        List<Support> supportsPDF = ss.findByType(Etype.PDF);
        for (Support s : supportsPDF) {
            System.out.println(s.getTitre() + " - " + s.getFichier());
        }

        System.out.println("\nRecherche d'un support par titre : 'Programmation Java'");
        Support supportRecherche = ss.findByTitre("Programmation Java");
        if (supportRecherche != null) {
            System.out.println("Support trouvé : " + supportRecherche.getTitre() + " - " + supportRecherche.getType());
        } else {
            System.out.println("Aucun support trouvé avec ce titre.");
        }

    }
}
