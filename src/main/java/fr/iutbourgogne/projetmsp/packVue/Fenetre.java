package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.User;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;

/**
 * Classe permettant de gérer la fenêtre et le lancement des vues
 * 
 * @author Jorick
 */
public class Fenetre extends JFrame implements Observateur {

    /**
     * Attribut contenant l'utilisateur
     */
    private User personne;

    /**
     * Attribut représentant la première vue = l'authentification
     */
    private VueAuth vueAuth;

    /**
     * Constructeur de la fenêtre
     * @param p = un utilisateur (valant null au départ)
     */
    public Fenetre(User p) {
        this.setResizable(false);

        // on lance la vue d'authentification
        vueAuth = new VueAuth(p);
        this.add(vueAuth);
        this.setTitle("MSP — Connexion");

        this.pack();
        this.setLocationRelativeTo(null); // permet de centrer la vue
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // permet d'enlever les tool tips (= des instructions dans des rectangles lorsque le curseur 
        // passe sur certains éléments et qui sont affichées même si vides)
        ToolTipManager.sharedInstance().setEnabled(false);
    }

    @Override
    /**
     * Cette méthode récupère les alertes données par l'observateur afin
     * de lancer les vues correspondantes
     */
    public void update(String code) {
        switch (code) {
            case "connexionSuccess": // la connexion est validée
                personne = vueAuth.getPersonne(); // on récupère la personne qui a été créée avec le login entré
                afficherAccueil(personne);  // on affiche la vue d'accueil
                break;
            case "deconnexion": // l'utilisateur a cliqué sur "déconnexion"
                afficherAuth(); // on affiche la vue d'authentification
                break;
            case "changeMdp":   // l'utilisateur a cliqué sur "changer le mot de passe"
                afficherChangeMdp(); 
                break;
            case "retour":
                afficherAccueil(personne);
                break;
            case "ouvreProjet": // l'utilisateur souhaite voir le détail d'un projet (liste des activités)
                this.afficherDetailProjet();
                break;
            case "ouvreActivite": // l'utilisateur souhaite voir le détail d'une activité dont il est en charge (modification possible)
                this.afficherDetailActivite();
                break;
            case "ouvreActiviteLecture": // l'utilisateur souhaite voir le détail d'une activité dont il n'est pas en charge (lecture seulement)
                this.afficherDetailActiviteLecture();
                break;
            case "retourProjet":
                this.afficherDetailProjet();
                break;
            case "ouvreModificationActivite": // l'utilisateur souhaite modifier une activité
                this.afficherModificationActivite();
                break;
            case "retourModification":
                this.afficherDetailActivite();
                break;
        }
    }

    /**
     * Méthode permettant d'afficher la vue principale (après l'authentification)
     * On teste le type de l'utilisateur pour ne pas afficher les fonctionnalités
     * des techniciens aux autres utilisateurs
     * @param personne 
     */
    private void afficherAccueil(User personne) {
        switch (personne.getRole()) {
            case "technicien":
                VueAccueilTechnicien vueAccueilTech = new VueAccueilTechnicien(personne);
                this.setContentPane(vueAccueilTech);
                this.setTitle("MSP — Accueil");
                this.pack();
                break;
            case "commercial":
                VueAccueilCommercial vueAccueilCom = new VueAccueilCommercial(personne);
                this.setContentPane(vueAccueilCom);
                this.setTitle("MSP — Accueil");
                this.pack();
                break;
            case "client":
                VueAccueilClient vueAccueilCli = new VueAccueilClient(personne);
                this.setContentPane(vueAccueilCli);
                this.setTitle("MSP — Accueil");
                this.pack();
                break;
        }
    }

    /**
     * Méthode permettant d'afficher la vue d'authentification
     */
    private void afficherAuth() {
        vueAuth = new VueAuth(personne);
        this.setContentPane(vueAuth);
        this.setTitle("MSP — Connexion");
        this.pack();
    }

    /**
     * Méthode permettant d'afficher la vue de changement de mot de passe
     */
    private void afficherChangeMdp() {
        VueChangeMdp vueChangeMdp = new VueChangeMdp(personne);
        this.setContentPane(vueChangeMdp);
        this.setTitle("MSP — Changement du mot de passe");
        this.pack();
    }

    /**
     * Méthode permettant d'afficher la vue de détail d'un projet (la liste des
     * activités qu'il contient)
     */
    private void afficherDetailProjet() {
        VueDetailProjet vueDetailProjet = new VueDetailProjet(personne);
        this.setContentPane(vueDetailProjet);
        this.setTitle("MSP — Détail du projet");
        this.pack();
    }

    /**
     * Méthode permettant d'afficher la vue du détail d'une activité lorsque
     * le technicien a le droit de la modifier (il est en charge de cette activité)
     */
    private void afficherDetailActivite() {
        VueDetailActivite vueDetailActivite = new VueDetailActivite(personne);
        this.setContentPane(vueDetailActivite);
        this.setTitle("MSP — Détail de l'activité");
        this.pack();
    }

    /**
     * Méthode permettant d'afficher la vue du détail d'une activité lorsque
     * le technicien a seulement un droit de lecture dessus (il travaille sur le projet mais
     * l'activité est réalisée par un autre technicien)
     */
    private void afficherDetailActiviteLecture() {
        VueDetailActiviteLecture vueDetailActiviteLecture = new VueDetailActiviteLecture(personne);
        this.setContentPane(vueDetailActiviteLecture);
        this.setTitle("MSP — Détail de l'activité");
        this.pack();
    }
    
    /**
     * Méthode permettant d'afficher la vue de modification d'une activité
     */
    private void afficherModificationActivite() {
        VueModificationActivite vueModificationActivite = new VueModificationActivite(personne);
        this.setContentPane(vueModificationActivite);
        this.setTitle("MSP — Modifier l'activité");
        this.pack();
    }
}
