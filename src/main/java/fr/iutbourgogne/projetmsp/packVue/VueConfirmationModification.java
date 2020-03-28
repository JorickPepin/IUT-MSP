
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class VueConfirmationModification extends JFrame {
    
    public VueConfirmationModification() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Les modifications ont été enregistrées.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }
}
