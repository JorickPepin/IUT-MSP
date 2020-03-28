
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class VueErrorChangeMdp4 extends JFrame {
    
    public VueErrorChangeMdp4() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Le mot de passe doit être composé de 4 caractères au minimum.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
    }
}
