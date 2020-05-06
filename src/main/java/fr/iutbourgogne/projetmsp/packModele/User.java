package fr.iutbourgogne.projetmsp.packModele;

import fr.iutbourgogne.projetmsp.packVue.Observateur;
import java.util.ArrayList;

/**
 * Classe représentant un utilisateur (avec la bdd fournie, l'utilisateur n'est
 * pas forcément un technicien, on récupère donc le rôle pour donner l'accès aux
 * fonctionnalités qu'aux techniciens)
 *
 * @author Jorick
 */
public class User implements Observable {

    /**
     * Attribut contenant l'id de l'utilisateur dans la bdd
     */
    private int id;

    /**
     * Attribut contenant le login de l'utilisateur
     */
    private String login;

    /**
     * Attribut contenant le mot de passe de l'utilisateur
     */
    private String password;

    /**
     * Attribut contenant le prenom de l'utilisateur
     */
    private String prenom;

    /**
     * Attribut contenant le nom de l'utilisateur
     */
    private String nom;

    /**
     * Attribut contenant le rôle de l'utilisateur (technicien, commercial, ...)
     * On récupère le rôle pour effectuer des tests car dans la bdd fournie, il
     * n'y a pas que des techniciens
     */
    private String role;

    private ArrayList<Observateur> observateurs = new ArrayList<>();

    /**
     * Attribut contenant la liste des projets du technicien
     */
    private ArrayList<Projet> listeProjets = new ArrayList<>();

    /**
     * Attribut contenant la liste des actvités du technicien
     */
    private ArrayList<Activity> listeActivites = new ArrayList<>();

    /**
     * Attribut contenant le projet que le technicien est en train de consulter
     */
    private Projet projetEnCours;

    /**
     * Constructeur permettant d'avertir que l'utilisateur n'existe pas
     * lorsqu'on exécute la requête (voir UserDao)
     */
    public User() {
        this.login = "error";
    }

    /**
     * Constructeur de l'utilisateur
     * @param id
     * @param login
     * @param password
     * @param prenom
     * @param nom
     * @param role
     */
    public User(int id, String login, String password, String prenom, String nom, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String mdp) {
        password = mdp;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String _prenom) {
        prenom = _prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String _nom) {
        nom = _nom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setListeProjets(ArrayList<Projet> projets) {
        this.listeProjets = projets;
    }

    public ArrayList<Projet> getListeProjets() {
        return listeProjets;
    }

    @Override
    public void addObservateur(Observateur obs) {
        observateurs.add(obs);
    }

    @Override
    public void notifyObservateurs(String code) {
        for (Observateur obs : observateurs) {
            obs.update(code);
        }
    }

    public Projet getProjetEnCours() {
        return projetEnCours;
    }

    public void setProjetEnCours(Projet projetEnCours) {
        this.projetEnCours = projetEnCours;
    }

    public ArrayList<Activity> getListeActivites() {
        return listeActivites;
    }

    public void addActivite(Activity activite) {
        this.listeActivites.add(activite);
    }
}
