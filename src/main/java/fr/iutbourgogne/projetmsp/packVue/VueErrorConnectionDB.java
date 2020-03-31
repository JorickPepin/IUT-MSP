/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.iutbourgogne.projetmsp.packVue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorick
 */
public class VueErrorConnectionDB extends JFrame {
    
    public VueErrorConnectionDB() {
        JOptionPane jop1;
        jop1 = new JOptionPane();
        JOptionPane.showMessageDialog(null, "Le logiciel n'a pas pu se connecter à la base de données.", "Échec connexion", JOptionPane.ERROR_MESSAGE);
    }
}
