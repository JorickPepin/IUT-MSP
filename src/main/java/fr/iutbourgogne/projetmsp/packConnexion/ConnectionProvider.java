
package fr.iutbourgogne.projetmsp.packConnexion;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Jorick
 */
public class ConnectionProvider {
    private static final String CNX_USERNAME = "root";
    private static final String CNX_PASSWORD = "root";
    private static final String CNX_URL      = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(CNX_URL, CNX_USERNAME, CNX_PASSWORD);
    }
}
