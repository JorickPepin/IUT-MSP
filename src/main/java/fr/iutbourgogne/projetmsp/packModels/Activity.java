
package fr.iutbourgogne.projetmsp.packModels;

import java.sql.Date;


/**
 *
 * @author Jorick
 */
public class Activity {
    
    private int id;
    private String type;
    private String resume;
    private String statut;
    private String detail;
    private Date dateDebut;
    private Date dateFin;
    
    public Activity(int id, String type, String resume, String statut, String detail, Date dateDebut, Date dateFin) {
        this.id = id;
        this.type = type;
        this.resume = resume;
        this.statut = statut;
        this.detail = detail;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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
    
}
