
package fr.iutbourgogne.projetmsp.packModele;

/**
 * Contient divers utilitaires pour l'ensemble de la couche métier
 * @author Admin
 */
public class Utils {
    
    /**
     * Méthode permettant de "crypter" le mot de passe
     * @param mdp = le mot de passe 
     * @return le mot de passe "crypté"
     */
    public static String hashPassWord(String mdp) {
        StringBuilder mdpCrypte = new StringBuilder();
        
        for(char c : mdp.toCharArray()) {
            mdpCrypte.append(Integer.toHexString(c));
        }
        
        return mdpCrypte.toString().toUpperCase();
    }
    
    /**
     * Méthode permettant de vérifier si le mot de passe correspond aux critères
     * définis ici
     * Critère défini : le mot de passe doit faire au moins 4 caractères
     * 
     * @param mdp = le mot de passe entré
     * @return true si le mdp correspond aux critères, false sinon
     */
    public static boolean isPassWordSafe(String mdp) {
        return mdp.length() > 3;
    }
}
