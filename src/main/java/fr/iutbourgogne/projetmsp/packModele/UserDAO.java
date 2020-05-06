package fr.iutbourgogne.projetmsp.packModele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import fr.iutbourgogne.projetmsp.packConnexion.ConnectionProvider;
import javax.swing.JOptionPane;

/**
 * Classe contenant les requêtes relatives à l'utilisateur
 *
 * @author Jorick
 */
public class UserDAO {

    /**
     * Méthode permettant de créer l'utilisateur avec les informations de la
     * base de données
     * @param login = le login de l'utilisateur
     * @return l'utilisateur
     */
    public static User findWithLogin(String login) {
        User user = null;
        String typeUtilisateur = null;
        String password = null;

        // on initialise l'id à -1 car on est sûr que personne n'a cet id dans la bdd
        int id = -1;

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd

            // on prépare la requête
            PreparedStatement st = conn.prepareStatement("SELECT * FROM Utilisateur WHERE login=?");

            // on remplace le ? par le login
            st.setString(1, login);

            // on exécute la requête
            ResultSet rs = st.executeQuery();

            if (rs.first()) {   // s'il y a un résultat (l'utilisateur existe)  
                id = rs.getInt("id");
                password = rs.getString("password");
                typeUtilisateur = rs.getString("type_utilisateur");

            } else {            // l'utilisateur n'existe pas
                user = new User(); // on crée un utilisateur avec le constructeur vide pour avertir qu'il y a une erreur
            }

        } catch (SQLException e) { // la connexion n'a pas pu se faire
            JOptionPane.showMessageDialog(null, "Le logiciel n'a pas pu se connecter à la base de données.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }

        if (id != -1) { // si l'utilisateur existe (l'id a été mis à jour)

            // selon le type de l'utilisateur, on récupère pas les informations dans les mêmes tables
            // on teste donc le type pour appeler la bonne requête
            // dans la bdd fournie, il y a les 3 types suivants
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

    /**
     * Méthode permettant de récupérer les informations d'un technicien
     * @param id = l'id de l'utilisateur
     * @return une liste contenant le prénom et le nom
     */
    public static ArrayList<String> findTechnicien(int id) {

        // liste contenant le prénom et le nom
        ArrayList<String> infos = new ArrayList();

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            PreparedStatement st = conn.prepareStatement("SELECT nom, prenom FROM Technicien WHERE utilisateur_id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            // on ajoute le prénom et le nom à la liste
            if (rs.first()) {
                infos.add(rs.getString("prenom"));
                infos.add(rs.getString("nom"));
            }
            
        } catch (SQLException e) { // la connexion n'a pas pu se faire
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du technicien.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
        
        return infos;
    }

    /**
     * Méthode permettant de récupérer les informations d'un commercial
     * @param id = l'id de l'utilisateur
     * @return = le nom et le prénom du commercial
     */
    public static ArrayList<String> findCommercial(int id) {

        // liste contenant le prénom et le nom
        ArrayList<String> infos = new ArrayList();

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            PreparedStatement st = conn.prepareStatement("SELECT nom, prenom FROM Commercial WHERE utilisateur_id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            // on ajoute le prénom et le nom à la liste
            if (rs.first()) {
                infos.add(rs.getString("prenom"));
                infos.add(rs.getString("nom"));
            }
            
        } catch (SQLException e) { // la connexion n'a pas pu se faire
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du commercial.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
        
        return infos;
    }

    /**
     * Méthode permettant de récupérer les informations d'un client
     * @param id = l'id de l'utilisateur
     * @return = le nom et le prénom
     */
    public static ArrayList<String> findClient(int id) {

        // liste contenant le prénom et le nom
        ArrayList<String> infos = new ArrayList();

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            PreparedStatement st = conn.prepareStatement("SELECT nom, prenom FROM Client WHERE utilisateur_id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            // on ajoute le prénom et le nom à la liste
            if (rs.first()) {
                infos.add(rs.getString("prenom"));
                infos.add(rs.getString("nom"));
            }
            
        } catch (SQLException e) { // la connexion n'a pas pu se faire
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du client.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
        
        return infos;
    }

    /**
     * Méthode permettant de mettre à jour le mot de passe de l'utilisateur dans la base de données
     * @param mdp = le nouveau mot de passe
     * @param p = l'utilisateur
     * @return = la personne avec le mot de passe mis à jour
     */
    public static User updateMdp(String mdp, User p) {

        p.setPassword(mdp);

        try ( Connection conn = ConnectionProvider.openConnection()) { // connexion à la bdd
            
            PreparedStatement st = conn.prepareStatement("UPDATE Utilisateur SET password=? WHERE login=?");
            
            // on remplace le premier ? de la requête par le nouveau mot de passe
            st.setString(1, mdp);
            
            // on remplace le deuxième ? par le login de l'utilisateur 
            st.setString(2, p.getLogin());
            
            st.executeUpdate();

        } catch (SQLException e) { // la connexion n'a pas pu se faire
            JOptionPane.showMessageDialog(null, "Impossible de mettre à jour le mot de passe.", "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
        
        return p;
    }
}
