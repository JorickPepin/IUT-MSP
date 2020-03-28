
package fr.iutbourgogne.projetmsp.packModels;

import java.util.ArrayList;

/**
 *
 * @author Jorick
 */
public class Projet {
    
    protected int id;
    protected String nom;
    protected String statut;
    protected String dureeEstimee;
    protected String dureeFinale;
    protected ArrayList<Activity> activities = new ArrayList<>();
    protected Activity activiteEnCours;
    
    public Projet(int id, String nom, String statut, String dureeEstimee, String dureeFinale) {
        this.id = id;
        this.nom = nom;
        this.statut = statut;
        this.dureeEstimee = dureeEstimee;
        this.dureeFinale = dureeFinale;
    }
    
    public Projet(int id, String nom) {
        this.id = id;
        this.nom = nom; 
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getStatut() {
        return statut;
    }
        
    public String getDureeEstimee() {
        return dureeEstimee;
    }
            
    public String getDureeFinale() {
        return dureeFinale;
    }
    
    public int getId() {
        return id;
    }
    
    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
    
    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public Activity getActiviteEnCours() {
        return activiteEnCours;
    }

    public void setActiviteEnCours(Activity activiteEnCours) {
        this.activiteEnCours = activiteEnCours;
    }

}
