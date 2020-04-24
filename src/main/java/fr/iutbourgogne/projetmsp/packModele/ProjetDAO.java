
package fr.iutbourgogne.projetmsp.packModele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import fr.iutbourgogne.projetmsp.packConnexion.ConnectionProvider;

/**
 *
 * @author Jorick
 */
public class ProjetDAO {
    
       public static ArrayList<Projet> findProjets(User p) {
       
       ArrayList<Projet> projets = new ArrayList();
       
        try (Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT DISTINCT projetID, nom, statut, duréeEstimée, duréeFinale FROM Projet INNER JOIN Compose ON Projet.id = Compose.projetId NATURAL JOIN Affecté "
                    + "WHERE Affecté.technicienID = ? ORDER BY case when statut = 'en cours' then 1 when statut = 'en attente' then 2 when statut = 'fini' then 3 else 4 end asc");
            st.setInt(1, p.getId());
            ResultSet rs = st.executeQuery();
            
            if (rs.first()) {
                
                projets.add(new Projet(rs.getInt("projetID"), rs.getString("nom"), rs.getString("statut"), rs.getString("duréeEstimée"), rs.getString("duréeFinale")));
  
                while(rs.next()){
                    projets.add(new Projet(rs.getInt("projetID"), rs.getString("nom"), rs.getString("statut"), rs.getString("duréeEstimée"), rs.getString("duréeFinale")));
                }
                
                p.setListeProjets(projets); 
            }
        } catch(SQLException e) {
               System.out.println("SQL Exception: " + e);
            }
       return projets;
   }
}
