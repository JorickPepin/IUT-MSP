package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModels.User;
import fr.iutbourgogne.projetmsp.packModels.UserDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;


public class VueAuth extends JPanel {
    
    private User personne;
    
    public VueAuth(User p) {
        this.personne = p;
        
        initComponents();
        
        boutonConnexion.addActionListener(new Ecouteur_Connexion());
        boutonConnexion.addMouseListener(new Ecouteur_Connexion());
        boutonConnexion.addMouseMotionListener(new Ecouteur_Connexion());
        champPseudo.addKeyListener(new Ecouteur_Connexion());
        champPassword.addKeyListener(new Ecouteur_Connexion());
    }
    
    // Classe interne, interception de la connexion
    public class Ecouteur_Connexion implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (e.getSource() == boutonConnexion){
                connexion();
            }
        }

        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
        @Override public void mouseClicked(MouseEvent e) {}

        @Override 
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
        
        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getComponent() == boutonConnexion) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ENTER){
                connexion();
            }
        }

        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {}
    }
    
    public void connexion() {
        VueAuth.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        ArrayList<String> identifiants = new ArrayList();

        identifiants.add(champPseudo.getText());
        identifiants.add(String.valueOf(champPassword.getPassword()));

        User user1 = UserDAO.findWithLogin(identifiants.get(0));

        user1.addObservateur((Observateur) SwingUtilities.getWindowAncestor(VueAuth.this));

        Border borderRed = BorderFactory.createLineBorder(Color.RED, 1);

        // échec
        if (user1.getLogin().equals("error")) {
            personne = user1;
            personne.notifyObservateurs("connexionEchec");

            champPseudo.setBorder(borderRed);
            champPassword.setBorder(borderRed);
        } // succes
        else if (identifiants.get(0).equals(user1.getLogin())
                && identifiants.get(1).equals(user1.getPassword())) {
            personne = user1;
            personne.notifyObservateurs("connexionSuccess");
        } // échec
        else if (identifiants.get(0).equals(user1.getLogin())
                && !identifiants.get(1).equals(user1.getPassword())) {
            personne = user1;
            personne.notifyObservateurs("connexionEchec");

            champPseudo.setBorder(borderRed);
            champPassword.setBorder(borderRed);
        }

        VueAuth.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
          
    public User getPersonne() {
        return personne;
    }
   
    @SuppressWarnings("unchecked")
    private void initComponents() {

        labelImageBas = new JLabel();
        champPseudo = new JTextField();
        labelUtilisateur = new JLabel();
        labelPassword = new JLabel();
        labelTitre = new JLabel();
        labelSousTitre = new JLabel();
        boutonConnexion = new JButton();
        champPassword = new JPasswordField();
        jSeparator1 = new JSeparator();
        labelAvertissement = new JLabel();


        setBackground(new Color(255, 255, 255));
        labelImageBas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bandeau_bas.jpg"))); // NOI18N*/

        champPseudo.setToolTipText("");
        champPseudo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        labelUtilisateur.setText("Utilisateur :");

        labelPassword.setText("Mot de passe :");

        labelTitre.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setText("Progiciel MSP");

        labelSousTitre.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        labelSousTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelSousTitre.setText("Meilleur Suivi de Projet");

        boutonConnexion.setBackground(new java.awt.Color(255, 204, 0));
        boutonConnexion.setText("Se connecter");

        champPseudo.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        champPassword.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(labelImageBas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(labelTitre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(labelSousTitre, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator1)
                                .addComponent(boutonConnexion, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(labelPassword, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelUtilisateur, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(champPseudo)
                                                .addComponent(champPassword, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))))
                        .addGap(90, 90, 90))
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelAvertissement, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(labelTitre, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSousTitre)
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(champPseudo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelUtilisateur, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(champPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(boutonConnexion)
                        .addGap(63, 63, 63)
                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAvertissement)
                        .addGap(46, 46, 46)
                        .addComponent(labelImageBas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
        
    }
    private javax.swing.JButton boutonConnexion;
    private javax.swing.JLabel labelUtilisateur;
    private javax.swing.JLabel labelImageBas;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelTitre;
    private javax.swing.JLabel labelSousTitre;
    private javax.swing.JLabel labelAvertissement;
    private javax.swing.JPasswordField champPassword;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField champPseudo;
}
