package fr.iutbourgogne.projetmsp.packModele;

import fr.iutbourgogne.projetmsp.packConnexion.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Classe contenant les requêtes concernant les activités d'un projet
 * 
 * @author Jorick
 */
public class ActivityDAO {

    /**
     * Métode permettant de récupérer toutes les activités d'un projet depuis la bdd
     * @param projet = le projet dont on veut les activités
     * @return = une liste d'activités
     */
    public static ArrayList<Activity> findActivities(Projet projet) {

        // liste contenant les activités du projet
        ArrayList<Activity> activities = new ArrayList();
        
        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            // on prépare la requête
            PreparedStatement st = conn.prepareStatement("SELECT * FROM activité_type INNER JOIN activité ON activité_type.ID = activité.IDType INNER JOIN affecté ON\n"
                    + "activité.ID = affecté.activitéID NATURAL JOIN compose INNER JOIN projet ON compose.projetID = projet.ID \n"
                    + "WHERE projetID = ? ORDER BY case WHEN activité.statut = 'en cours' then 1 when activité.statut = 'prévue' then 2 when activité.statut = 'terminée' then 3 when activité.statut = 'annulée' then 4 else 5 end asc;");
            
            // on remplace le point d'intérogation par l'id du projet
            st.setInt(1, projet.getId());
            
            // on exécute la requête
            ResultSet rs = st.executeQuery();

            // on teste d'abord si on a un résultat
            if (rs.first()) {

                // on ajoute l'activité
                activities.add(new Activity(rs.getInt("activitéID"), rs.getString("activité_type.nom"), rs.getString("résumé"), rs.getString("activité.statut"), rs.getString("détail"), rs.getDate("dateDébut"), rs.getDate("dateFin"), rs.getInt("technicienID")));

                // tant qu'il y a des activités, on les ajoute
                while (rs.next()) {
                    activities.add(new Activity(rs.getInt("activitéID"), rs.getString("activité_type.nom"), rs.getString("résumé"), rs.getString("activité.statut"), rs.getString("détail"), rs.getDate("dateDébut"), rs.getDate("dateFin"), rs.getInt("technicienID")));
                }

                // on remplit l'attribut du projet contenant les activités
                projet.setActivities(activities);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les activités.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
        
        return activities;
    }

    /**
     * Méthode permettant de mettre à jour le détail d'une activité dans la bdd
     * @param id = l'id de l'activité
     * @param detail = le nouveau détail
     */
    public static void updateActivityDetail(int id, String detail) {

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            // on prépare la requête
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET détail=? WHERE ID=?");
            
            // on remplace le premier ? par le détail
            st.setString(1, detail);
            
            // on remplace le deuxième ? par l'id
            st.setInt(2, id);
            
            // on exécute la requête
            st.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de mettre à jour le détail de l'activité.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Méthode permettant de mettre à jour le statut de l'activité dans la bdd
     * @param id = l'id de l'activité
     * @param statut = le nouveau statut de l'activité
     */
    public static void updateActivityStatut(int id, String statut) {

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            // on prépare la requête
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET statut=? WHERE ID=?");
            
            // on remplace le premier ? par le statut
            st.setString(1, statut);
            
            // on remplace le deuxième ? par l'id de l'activité
            st.setInt(2, id);
            
            // on exécute la requête
            st.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de mettre à jour le statut de l'activité.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Méthode permettant de mettre à jour la date de commencement 
     * de l'activité dans la bdd
     * @param id = l'id de l'activité
     * @param dateDebut = la date de commencement de l'activité 
     */
    public static void updateActivityDateDebut(int id, Date dateDebut) {

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            // on prépare la requête
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET dateDébut=? WHERE ID=?");
            
            // on remplace le premier ? par la date de commencement
            st.setDate(1, dateDebut);
            
            // on remplace le deuxième ? par l'id de l'activité
            st.setInt(2, id);
            
            // on exécute la requête
            st.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de mettre à jour la date.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Méthode permettant de mettre à jour la date de fin 
     * de l'activité dans la bdd
     * @param id = l'id de l'activité
     * @param dateFin = la date de fin de l'activité
     */
    public static void updateActivityDateFin(int id, Date dateFin) {

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            // on prépare la requête
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET dateFin=? WHERE ID=?");
            
            // on remplace le premier ? par la date de fin
            st.setDate(1, dateFin);
            
            // on remplace le deuxième ? par l'id
            st.setInt(2, id);
            
            // on exécute la requête
            st.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de mettre à jour la date.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
    }
}
