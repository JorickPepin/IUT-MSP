
package fr.iutbourgogne.projetmsp.packModele;

import fr.iutbourgogne.projetmsp.packVue.Observateur;
import java.util.ArrayList;


/**
 *
 * @author Jorick
 */
public class User implements Observable {
    
    private int id;
    private String login;
    private String password;
    private String prenom;
    private String nom;
    private String role;
    private ArrayList<Observateur> observateurs = new ArrayList<>();
    private ArrayList<Projet> listeProjets = new ArrayList<>();
    private ArrayList<Activity> listeActivites = new ArrayList<>();
    private Projet projetEnCours;

    public User() {
        this.login = "error";
    }
   
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
