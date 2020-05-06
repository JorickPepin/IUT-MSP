
package fr.iutbourgogne.projetmsp.packConnexion;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe permettant de se connecter à la base de données
 * 
 * @author Jorick
 */
public class ConnectionProvider {
    
    /**
     * Attribut contenant le username de la bdd
     */
    private static final String CNX_USERNAME = "root";
    
    /**
     * Attribut contenant le mot de passe de la bdd (mettre "" si pas de mdp)
     */
    private static final String CNX_PASSWORD = "root";
    
    /**
     * Attribut contenant l'url de la bdd
     */
    private static final String CNX_URL      = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    /**
     * Méthode permettant de se connecter à la base de données
     * @return la connexion
     * @throws SQLException 
     */
    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(CNX_URL, CNX_USERNAME, CNX_PASSWORD);
    }
}
