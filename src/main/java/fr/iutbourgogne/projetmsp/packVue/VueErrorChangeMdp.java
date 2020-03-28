
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class VueErrorChangeMdp extends JFrame {
    
    public VueErrorChangeMdp() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Le mot de passe entré ne correspond pas au mot de passe actuel.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
    }
}