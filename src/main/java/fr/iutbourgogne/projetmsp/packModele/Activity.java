
package fr.iutbourgogne.projetmsp.packModele;

import java.sql.Date;
import java.util.ArrayList;


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
    private int idTechnicien;
    
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
