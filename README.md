# Gest_Supports_pedagogiques
Ce projet est une application de gestion des supports pédagogiques. Il permet d'organiser et de gérer les supports pédagogiques mis en ligne par les professeurs. L'application est développée en Java avec une base de données MySQL.

# Fonctionnalités

Ajouter un support : Permet d'ajouter un nouveau support avec son titre, son type et son fichier associé.
Filtrer les supports par professeur : Afficher les supports ajoutés par un professeur spécifique.
Rechercher un support par titre : Permet de rechercher un support en fonction de son titre.
Lister les supports par type : Affiche les supports selon leur type (PDF, Vidéo, etc.).
Visualisation des statistiques : Générer un graphe en barres montrant le nombre de supports par professeur.

#DEMO
https://drive.google.com/drive/u/0/folders/18S-CPCHHvHKJ3Hg3HyhTufHf0slMlYNn
# Structure de la Base de Données

CREATE TABLE Professeur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE Support (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    type ENUM('PDF','PRESENTATION','LIEN_WEB') NOT NULL,
    fichier VARCHAR(255) NOT NULL
);


CREATE TABLE AjoutSupport (
    support_id INT NOT NULL,
    professeur_id INT NOT NULL,
    date_ajout DATE NOT NULL,
    PRIMARY KEY (support_id, professeur_id, date_ajout),
    FOREIGN KEY (support_id) REFERENCES Support(id) ON DELETE CASCADE,
    FOREIGN KEY (professeur_id) REFERENCES Professeur(id) ON DELETE CASCADE
);



CREATE TABLE User(
    login VARCHAR(50) PRIMARY KEY,
    password VARCHAR(256) NOT NULL
);
