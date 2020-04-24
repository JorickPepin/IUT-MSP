package fr.iutbourgogne.projetmsp.packModele;

import fr.iutbourgogne.projetmsp.packConnexion.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe contenant les requêtes concernant les activités d'un projet
 * @author Jorick
 */
public class ActivityDAO {

    public static ArrayList<Activity> findActivities(Projet projet) {

        ArrayList<Activity> activities = new ArrayList();

        try ( Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM activité_type INNER JOIN activité ON activité_type.ID = activité.IDType INNER JOIN affecté ON\n"
                    + "activité.ID = affecté.activitéID NATURAL JOIN compose INNER JOIN projet ON compose.projetID = projet.ID \n"
                    + "WHERE projetID = ? ORDER BY case WHEN activité.statut = 'en cours' then 1 when activité.statut = 'prévue' then 2 when activité.statut = 'terminée' then 3 when activité.statut = 'annulée' then 4 else 5 end asc;");
            st.setInt(1, projet.getId());
            ResultSet rs = st.executeQuery();

            if (rs.first()) {

                activities.add(new Activity(rs.getInt("activitéID"), rs.getString("activité_type.nom"), rs.getString("résumé"), rs.getString("activité.statut"), rs.getString("détail"), rs.getDate("dateDébut"), rs.getDate("dateFin"), rs.getInt("technicienID")));

                while (rs.next()) {
                    activities.add(new Activity(rs.getInt("activitéID"), rs.getString("activité_type.nom"), rs.getString("résumé"), rs.getString("activité.statut"), rs.getString("détail"), rs.getDate("dateDébut"), rs.getDate("dateFin"), rs.getInt("technicienID")));
                }

                projet.setActivities(activities);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return activities;
    }

    public static void updateActivityDetail(int id, String detail) {

        try ( Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET détail=? WHERE ID=?");
            st.setString(1, detail);
            st.setInt(2, id);
            int rs = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public static void updateActivityStatut(int id, String statut) {

        try ( Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET statut=? WHERE ID=?");
            st.setString(1, statut);
            st.setInt(2, id);
            int rs = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public static void updateActivityDateDebut(int id, Date dateDebut) {

        try ( Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET dateDébut=? WHERE ID=?");
            st.setDate(1, dateDebut);
            st.setInt(2, id);
            int rs = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }

    public static void updateActivityDateFin(int id, Date dateFin) {

        try ( Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("UPDATE Activité SET dateFin=? WHERE ID=?");
            st.setDate(1, dateFin);
            st.setInt(2, id);
            int rs = st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
    }
}
