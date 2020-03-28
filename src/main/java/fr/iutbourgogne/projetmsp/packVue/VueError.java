
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VueError extends JFrame {

    public VueError() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Le nom d'utilisateur ou le mot de passe est incorrect.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
    }                  
}

