package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.User;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;

public class Fenetre extends JFrame implements Observateur {

    private User personne;

    private VueAuth vueAuth;

    public Fenetre(User p) {
        this.setResizable(false);

        vueAuth = new VueAuth(p);
        this.add(vueAuth);
        this.setTitle("MSP — Connexion");

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ToolTipManager.sharedInstance().setEnabled(false);
    }

    @Override
    public void update(String code) {
        switch (code) {
            case "connexionEchec":
                VueError vueE = new VueError();
                break;
            case "connexionSuccess":
                personne = vueAuth.getPersonne();
                this.afficherAccueil(personne);
                break;
            case "deconnexion":
                this.afficherAuth();
                break;
            case "changeMdp":
                this.afficherChangeMdp();
                break;
            case "retour":
                this.afficherAccueil(personne);
                break;
            case "changeMdpEchec":
                VueErrorChangeMdp vueErrorChangeMdp = new VueErrorChangeMdp();
                break;
            case "changeMdpEchec2":
                VueErrorChangeMdp2 vueErrorChangeMdp2 = new VueErrorChangeMdp2();
                break;
            case "changeMdpEchec3":
                VueErrorChangeMdp3 vueErrorChangeMdp3 = new VueErrorChangeMdp3();
                break;
            case "changeMdpEchec4":
                VueErrorChangeMdp4 vueErrorChangeMdp4 = new VueErrorChangeMdp4();
                break;
            case "changeMdpSuccess":
                VueSuccessChangeMdp vueSuccessChangeMdp = new VueSuccessChangeMdp();
                break;
            case "ouvreProjet":
                this.afficherDetailProjet();
                break;
            case "ouvreActivite":
                this.afficherDetailActivite();
                break;
            case "ouvreActiviteLecture":
                this.afficherDetailActiviteLecture();
                break;
            case "retourProjet":
                this.afficherDetailProjet();
                break;
            case "ouvreModificationActivite":
                this.afficherModificationActivite();
                break;
            case "validation":
                VueConfirmationAnnulation vueConfirmationAnnulation = new VueConfirmationAnnulation(personne);
                break;
            case "confirmation":
                VueConfirmationModification vueConfirmationModification = new VueConfirmationModification();
                break;
            case "retourModification":
                this.afficherDetailActivite();
                break;
        }
    }

    public void afficherAccueil(User personne) {
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

    public void afficherAuth() {
        vueAuth = new VueAuth(personne);
        this.setContentPane(vueAuth);
        this.setTitle("MSP — Connexion");
        this.pack();
    }

    public void afficherChangeMdp() {
        VueChangeMdp vueChangeMdp = new VueChangeMdp(personne);
        this.setContentPane(vueChangeMdp);
        this.setTitle("MSP — Changement du mot de passe");
        this.pack();
    }

    public void afficherDetailProjet() {
        VueDetailProjet vueDetailProjet = new VueDetailProjet(personne);
        this.setContentPane(vueDetailProjet);
        this.setTitle("MSP — Détail du projet");
        this.pack();
    }

    public void afficherDetailActivite() {
        VueDetailActivite vueDetailActivite = new VueDetailActivite(personne);
        this.setContentPane(vueDetailActivite);
        this.setTitle("MSP — Détail de l'activité");
        this.pack();
    }

    public void afficherDetailActiviteLecture() {
        VueDetailActiviteLecture vueDetailActiviteLecture = new VueDetailActiviteLecture(personne);
        this.setContentPane(vueDetailActiviteLecture);
        this.setTitle("MSP — Détail de l'activité");
        this.pack();
    }
    
    public void afficherModificationActivite() {
        VueModificationActivite vueModificationActivite = new VueModificationActivite(personne);
        this.setContentPane(vueModificationActivite);
        this.setTitle("MSP — Modifier l'activité");
        this.pack();
    }
}
