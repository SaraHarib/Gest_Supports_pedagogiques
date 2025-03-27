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
import beans.User;
import connexion.Connexion;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import services.AjoutSupportService;
import services.ProfesseurService;
import services.SupportService;
import services.UserService;

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

        UserService userService = new UserService();

        User user1 = new User("s.harib", "1234", "Quel est votre animal préféré ?", "chat", "saraharib12@gmail.com");
        User user2 = new User("garid", "3456", "Quel est votre film préféré ?", "rahma", "soukaina@gmail.com");

        if (userService.addUser(user1)) {
            System.out.println("Utilisateur 's.harib' ajouté avec succès.");
        } else {
            System.out.println("Échec de l'ajout de 's.harib'.");
        }

        if (userService.addUser(user2)) {
            System.out.println("Utilisateur 'garid' ajouté avec succès.");
        } else {
            System.out.println("Utilisateur déjà existant : " + user2.getLogin());
        }

        User foundUser = userService.findUserByLogin("s.harib");
        if (foundUser != null) {
            System.out.println("Utilisateur trouvé : " + foundUser.getLogin());
        } else {
            System.out.println("Utilisateur non trouvé.");
        }

        System.out.println("\nTentative d'authentification avant mise à jour du mot de passe");
        if (userService.authenticate("s.harib", "1234")) {
            System.out.println("Authentification réussie pour 's.harib'.");
        } else {
            System.out.println("Échec de l'authentification pour 's.harib'. Le mot de passe est peut-être incorrect.");
        }

        System.out.println("\nQuestion secrète de 's.harib' : " + userService.getSecurityQuestion("s.harib"));

        if (userService.verifySecurityAnswer("s.harib", "chat")) {
            System.out.println("Réponse correcte à la question secrète pour 's.harib'.");
        } else {
            System.out.println("Réponse incorrecte.");
        }

        System.out.println("\nMise à jour du mot de passe pour 's.harib'...");
        if (userService.updatePassword("s.harib", "newpass123")) {
            System.out.println("Mot de passe mis à jour avec succès.");
        } else {
            System.out.println("Échec de la mise à jour du mot de passe.");
        }

        System.out.println("\nTentative d'authentification avec le nouveau mot de passe...");
        if (userService.authenticate("s.harib", "newpass123")) {
            System.out.println("Authentification réussie avec le nouveau mot de passe.");
        } else {
            System.out.println("Échec de l'authentification avec le nouveau mot de passe.");
        }

        System.out.println("\nListe des supports ajoutés par le professeur ID = 2 :");
        for (Support s : ss.findByProfesseur(2)) {
            System.out.println(s.getTitre() + " - " + s.getType());
        }

        boolean result = userService.sendPasswordResetEmail("garid");

        if (result) {
            System.out.println("Email de réinitialisation envoyé avec succès !");
        } else {
            System.out.println("Échec de l'envoi de l'email.");
        }
    }
}
