
package fr.iutbourgogne.projetmsp.packModele;

import java.sql.Date;

/**
 * Classe représentant une activité d'un projet
 * 
 * @author Jorick
 */
public class Activity {
    
    /**
     * Attribut correspondant à l'id de l'activité dans la bdd
     */
    private int id;
    
    /**
     * Attribut représentant le type de l'activité dans la bdd
     */
    private String type;
    
    /**
     * Attribut représentant le résumé de l'activité
     */
    private String resume;
    
    /**
     * Attribut représentant le statut de l'activité
     */
    private String statut;
    
    /**
     * Attribut représentant le détail de l'activité
     */
    private String detail;
    
    /**
     * Attribut représentant la date de commencement de l'activité
     */
    private Date dateDebut;
    
    /**
     * Attribut représentant la date de fin de l'activité
     */
    private Date dateFin;
    
    /**
     * Attribut représentant l'id du technicien qui est chargé de l'activité
     */
    private int idTechnicien;
    
    /**
     * Constructeur de l'activité
     * @param id
     * @param type
     * @param resume
     * @param statut
     * @param detail
     * @param dateDebut
     * @param dateFin
     * @param idTechnicien 
     */
    public Activity(int id, String type, String resume, String statut, String detail, Date dateDebut, Date dateFin, int idTechnicien) {
        this.id = id;
        this.type = type;
        this.resume = resume;
        this.statut = statut;
        this.detail = detail;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idTechnicien = idTechnicien;
    }

    public String getType() {
        return type;
    }

    public String getResume() {
        return resume;
    }

    public String getStatut() {
        return statut;
    }

    public String getDetail() {
        return detail;
    }
    
    public int getId() {
        return id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getIdTechnicien() {
        return idTechnicien;
    }
}
