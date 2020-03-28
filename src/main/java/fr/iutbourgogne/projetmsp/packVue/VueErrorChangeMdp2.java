
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class VueErrorChangeMdp2 extends JFrame {
    
    public VueErrorChangeMdp2() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Les mots de passe entrés sont différents.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
    }
}
