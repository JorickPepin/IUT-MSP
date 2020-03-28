
package fr.iutbourgogne.projetmsp.packModels;

import fr.iutbourgogne.projetmsp.packVue.Observateur;
import java.util.ArrayList;


/**
 *
 * @author Jorick
 */
public class User implements Observable {
    
    protected int id;
    protected String login;
    protected String password;
    protected String prenom;
    protected String nom;
    protected String role;
    protected ArrayList<Observateur> observateurs = new ArrayList<>();
    protected ArrayList<Projet> projet = new ArrayList<>();
    protected Projet projetEnCours;

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
    
    public void setProjets(ArrayList<Projet> projets) {
        this.projet = projets;
    }
    
    public ArrayList<Projet> getProjets() {
        return projet;
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
     
}
