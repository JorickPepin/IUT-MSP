
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class VueSuccessChangeMdp extends JFrame {
    
    public VueSuccessChangeMdp() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Le mot de passe a été modifié.", "Informations correctes", JOptionPane.INFORMATION_MESSAGE);
    }
}
