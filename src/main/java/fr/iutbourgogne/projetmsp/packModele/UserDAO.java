
package fr.iutbourgogne.projetmsp.packModele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import fr.iutbourgogne.projetmsp.packConnexion.ConnectionProvider;
import fr.iutbourgogne.projetmsp.packVue.VueErrorConnectionDB;

/**
 *
 * @author Jorick
 */
public class UserDAO {
    
    public static User findWithLogin(String login) {
        User user = null;
        String typeUtilisateur = null;
        int id = -1;
        String password = null;
        
        try (Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Utilisateur WHERE login=?");
            st.setString(1, login);
            ResultSet rs = st.executeQuery();

            if (rs.first()) {   // QUELQU'UN
                id = rs.getInt("id");
                password = rs.getString("password");
                typeUtilisateur = rs.getString("type_utilisateur");
            } else {            // PERSONNE (resultset empty)
                user = new User();
            }
        } catch(SQLException e) {
            VueErrorConnectionDB error = new VueErrorConnectionDB();
        }
        
        if (id != -1) { // si l'utilisateur existe
            switch (typeUtilisateur) {
                case "technicien":
                    user = new User(id, login, password, findTechnicien(id).get(0), findTechnicien(id).get(1), typeUtilisateur);
                    break;
                case "commercial":
                    user = new User(id, login, password, findCommercial(id).get(0), findCommercial(id).get(1), typeUtilisateur);
                    break;
                case "client":
                    user = new User(id, login, password, findClient(id).get(0), findClient(id).get(1), typeUtilisateur);
                    break;
            }
        }
        return user;
    }
        
    public static User updateMdp(String mdp, User p) {
        
        p.setPassword(mdp);
        
        try (Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("UPDATE Utilisateur SET password=? WHERE login=?");
            st.setString(1, mdp);
            st.setString(2, p.getLogin());
            int rs = st.executeUpdate();

        }   catch(SQLException e) {
               System.out.println("SQL Exception: " + e);
            }
        return p;
    }
    
    public static ArrayList<String> findTechnicien(int id) {
        
        ArrayList<String> infos = new ArrayList();
        
        try (Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT nom, prenom FROM Technicien WHERE utilisateur_id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.first()) {
                infos.add(rs.getString("prenom"));
                infos.add(rs.getString("nom"));
            }
        }   catch(SQLException e) {
               System.out.println("SQL Exception: " + e);
            }
        return infos;
    }      
    
    public static ArrayList<String> findCommercial(int id) {
        
        ArrayList<String> infos = new ArrayList();
        
        try (Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT nom, prenom FROM Commercial WHERE utilisateur_id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.first()) {
                infos.add(rs.getString("prenom"));
                infos.add(rs.getString("nom"));
            }
        } catch(SQLException e) {
               System.out.println("SQL Exception: " + e);
            }
        return infos;
    }     
    
    public static ArrayList<String> findClient(int id) {
        
        ArrayList<String> infos = new ArrayList();
        
        try (Connection conn = ConnectionProvider.openConnection()) {
            PreparedStatement st = conn.prepareStatement("SELECT nom, prenom FROM Client WHERE utilisateur_id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.first()) {
                infos.add(rs.getString("prenom"));
                infos.add(rs.getString("nom"));
            }
        }   catch(SQLException e) {
               System.out.println("SQL Exception: " + e);
            }
        return infos;
    } 
}
