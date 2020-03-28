
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class VueErrorChangeMdp3  extends JFrame {
    
    public VueErrorChangeMdp3() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Tous les champs ne sont pas remplis.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
    }
}
