
package fr.iutbourgogne.projetmsp.packModele;

import java.util.ArrayList;

/**
 * Classe représentant un projet
 * 
 * @author Jorick
 */
public class Projet {
    
    /**
     * Attribut contenant l'id du projet dans la bdd
     */
    private int id;
    
    /**
     * Attribut contenant le nom du projet
     */
    private String nom;
    
    /**
     * Attribut contenant le statut du projet
     */
    private String statut;
    
    /**
     * Attribut contenant la durée estimée du projet
     */
    private String dureeEstimee;
    
    /**
     * Attribut contenant la durée finale du projet
     */
    private String dureeFinale;
    
    /**
     * Attribut contenant la liste des activités que contient le projet
     */
    private ArrayList<Activity> activities = new ArrayList<>();
    
    /**
     * Attribut représentant l'activité du projet que le technicien est en train
     * de consulter
     */
    private Activity activiteEnCours;
    
    /**
     * Constructeur du projet
     * @param id = l'id du projet
     * @param nom = le nom du projet
     * @param statut = le statut du projet
     * @param dureeEstimee = la durée estimée du projet
     * @param dureeFinale = la durée finale du projet
     */
    public Projet(int id, String nom, String statut, String dureeEstimee, String dureeFinale) {
        this.id = id;
        this.nom = nom;
        this.statut = statut;
        this.dureeEstimee = dureeEstimee;
        this.dureeFinale = dureeFinale;
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
