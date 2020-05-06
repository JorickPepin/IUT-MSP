package fr.iutbourgogne.projetmsp.packModele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import fr.iutbourgogne.projetmsp.packConnexion.ConnectionProvider;
import javax.swing.JOptionPane;

/**
 * Classe permettant de récupérer les projets d'un technicien
 *
 * @author Jorick
 */
public class ProjetDAO {

    /**
     * Méthode permettant de récupérer la liste des projets associés au
     * technicien
     * @param p = le technicien
     * @return la liste des projets
     */
    public static ArrayList<Projet> findProjets(User p) {

        // liste contenant les projets
        ArrayList<Projet> projets = new ArrayList();

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd

            // on prépare la requête
            PreparedStatement st = conn.prepareStatement("SELECT DISTINCT projetID, nom, statut, duréeEstimée, duréeFinale FROM Projet INNER JOIN Compose ON Projet.id = Compose.projetId NATURAL JOIN Affecté "
                    + "WHERE Affecté.technicienID = ? ORDER BY case when statut = 'en cours' then 1 when statut = 'en attente' then 2 when statut = 'fini' then 3 else 4 end asc");

            // on remplace le premier ? par l'id du technicien
            st.setInt(1, p.getId());

            // on exécute la requête
            ResultSet rs = st.executeQuery();

            // on vérifie s'il y a au moins un résultat
            if (rs.first()) {

                // on ajoute le projet
                projets.add(new Projet(rs.getInt("projetID"), rs.getString("nom"), rs.getString("statut"), rs.getString("duréeEstimée"), rs.getString("duréeFinale")));

                // tant qu'il y a des résultats
                while (rs.next()) {

                    // on les ajoute
                    projets.add(new Projet(rs.getInt("projetID"), rs.getString("nom"), rs.getString("statut"), rs.getString("duréeEstimée"), rs.getString("duréeFinale")));
                }

                // on remplit la liste des projets de la personne
                p.setListeProjets(projets);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les projets.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
        return projets;
    }
}
