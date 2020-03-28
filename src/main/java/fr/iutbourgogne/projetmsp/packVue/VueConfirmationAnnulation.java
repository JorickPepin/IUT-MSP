
package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModels.User;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Jorick
 */
public class VueConfirmationAnnulation extends JFrame {
    
    public VueConfirmationAnnulation(User p) {

        
        UIManager.put("OptionPane.yesButtonText", "Oui");
            UIManager.put("OptionPane.noButtonText", "Non");
        
        int reply = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir annuler ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            p.notifyObservateurs("retourModification");
        }
        else {
            
        }
    }
}
