package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.User;
import fr.iutbourgogne.projetmsp.packModele.UserDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.border.Border;

/**
 * Classe représentant la vue de changement de passe
 * 
 * @author Jorick
 */
public class VueChangeMdp extends JPanel {

    /**
     * Attribut représentant l'utilisateur
     */
    private User personne;

    public VueChangeMdp(User p) {
        this.personne = p;
        
        // dimensions de la vue
        this.setPreferredSize(new Dimension(500, 300));
        
        // ajout des composants
        initComponents();
        
        // ajout des listener
        addListener();
    }

    // classe interne
    private class Ecouteur implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == boutonValidation) {
                changementMdp();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == labelRetour) {
                personne.notifyObservateurs("retour");
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getComponent() == boutonValidation) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ENTER){
                changementMdp();
            }
        }
        
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {}
    }

    /**
     * Méthode contenant toutes les actions effectuées lorsque l'utilisateur clique sur boutonValidation
     */
    private void changementMdp() {

        // création de la bordure rouge pour l'appliquer lorsque nécessaire
        Border borderRed = BorderFactory.createLineBorder(Color.RED, 1);
        
        VueChangeMdp.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        // si tous les champs sont remplis
        if (champAncienMdp.getPassword().length != 0 && champNouveauMdp.getPassword().length != 0 
                && champValidationNouveauMdp.getPassword().length != 0) { 
            
            // si le mot de passe rentré correspond au mot de passe actuel
            if (String.valueOf(champAncienMdp.getPassword()).equals(personne.getPassword())) { 
                
                // si le nouveau mot de passe et le mot de passe de validation sont identiques
                if (String.valueOf(champNouveauMdp.getPassword()).equals(String.valueOf(champValidationNouveauMdp.getPassword()))) { 
                    
                    // si le nouveau mot de passe fait au moins 4 caractères (valeur basse pour faciliter les tests mais modifiable)  
                    if (String.valueOf(champNouveauMdp.getPassword()).length() > 3) {   
                        
                        // on met à jour le mot de passe
                        personne = UserDAO.updateMdp(String.valueOf(champNouveauMdp.getPassword()), personne);
                        
                        // fenêtre pour avertir du changement
                        JOptionPane.showMessageDialog(null, "Le mot de passe a été modifié.", "Informations correctes", JOptionPane.INFORMATION_MESSAGE);
                        
                        // on remet les bordures comme elles sont d'origine (pour enlever les bordures rouges potentielles)
                        initChamps();
                        
                        // on retourne à l'accueil
                        personne.notifyObservateurs("retour");
                        
                    } else { // le mot de passe fait moins de 4 caractères
                        JOptionPane.showMessageDialog(null, "Le mot de passe doit être composé de 4 caractères au minimum.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
                        initChamps();
                        champNouveauMdp.setBorder(borderRed);
                        champValidationNouveauMdp.setBorder(borderRed);
                    }
                    
                } else { // les deux mots de passe à entrer pour valider ne sont pas identiques
                    JOptionPane.showMessageDialog(null, "Les mots de passe entrés sont différents.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
                    initChamps();
                    champNouveauMdp.setBorder(borderRed);
                    champValidationNouveauMdp.setBorder(borderRed);
                }
                
            } else { // le mot de passe entrer ne correspond pas au mot de passe actuel
                JOptionPane.showMessageDialog(null, "Le mot de passe entré ne correspond pas au mot de passe actuel.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
                initChamps();
                champAncienMdp.setBorder(borderRed);
            }
            
        } else { // tous les champs n'ont pas été remplis
            JOptionPane.showMessageDialog(null, "Tous les champs ne sont pas remplis.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
            initChamps();

            // on teste pour savoir quels sont les champs vides
            // pour mettre les bordures en rouge
            if (champAncienMdp.getPassword().length == 0) {
                champAncienMdp.setBorder(borderRed);
            }
            if (champNouveauMdp.getPassword().length == 0) {
                champNouveauMdp.setBorder(borderRed);
            }
            if (champValidationNouveauMdp.getPassword().length == 0) {
                champValidationNouveauMdp.setBorder(borderRed);
            }
        }
        
        VueChangeMdp.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    /**
     * Méthode permettant de réinitialiser les bordures des champs de mot de
     * passe
     */
    private void initChamps() {
        champAncienMdp.setBorder(new JPasswordField().getBorder());
        champNouveauMdp.setBorder(new JPasswordField().getBorder());
        champValidationNouveauMdp.setBorder(new JPasswordField().getBorder());
    }

    /**
     * Méthode permettant d'ajouter les Listener dans le constructeur
     */
    private void addListener() {
        labelRetour.addMouseListener(new Ecouteur());
        boutonValidation.addActionListener(new Ecouteur());
        boutonValidation.addMouseListener(new Ecouteur());
        boutonValidation.addMouseMotionListener(new Ecouteur());
        champAncienMdp.addKeyListener(new Ecouteur());
        champNouveauMdp.addKeyListener(new Ecouteur());
        champValidationNouveauMdp.addKeyListener(new Ecouteur());   
        boutonValidation.addKeyListener(new Ecouteur()); 
    }
    
    private void initComponents() {

        boutonValidation = new JButton();
        labelRetour = new JLabel();
        champAncienMdp = new JPasswordField();
        champNouveauMdp = new JPasswordField();
        champValidationNouveauMdp = new JPasswordField();
        jSeparator1 = new JSeparator();
        labelAncienMdp = new JLabel();
        labelNouveauMdp = new JLabel();
        labelValidationNouveauMdp = new JLabel();
        labelImage = new JLabel();

        setBackground(new Color(255, 255, 255));

        labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bandeau_bas.jpg")));

        boutonValidation.setBackground(new java.awt.Color(255, 204, 0));
        boutonValidation.setText("Changer le mot de passe");

        labelRetour.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelRetour.setForeground(new java.awt.Color(204, 204, 204));
        labelRetour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelRetour.setText("Retour");
        labelRetour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelAncienMdp.setText("Mot de passe actuel :");
        labelNouveauMdp.setText("Nouveau mot de passe :");
        labelValidationNouveauMdp.setText("Validation nouveau mot de passe :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelImage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelRetour)
                                .addGap(31, 31, 31))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(boutonValidation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(labelNouveauMdp)
                                                        .addComponent(labelAncienMdp)
                                                        .addComponent(labelValidationNouveauMdp))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(champValidationNouveauMdp, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                        .addComponent(champNouveauMdp)
                                                        .addComponent(champAncienMdp))))
                                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelRetour)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(champAncienMdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelAncienMdp))
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(champNouveauMdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelNouveauMdp))
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(champValidationNouveauMdp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelValidationNouveauMdp))
                                .addGap(18, 18, 18)
                                .addComponent(boutonValidation)
                                .addGap(37, 37, 37)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(labelImage, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }

    private JButton boutonValidation;
    private JPasswordField champAncienMdp;
    private JPasswordField champNouveauMdp;
    private JPasswordField champValidationNouveauMdp;
    private JSeparator jSeparator1;
    private JLabel labelAncienMdp;
    private JLabel labelNouveauMdp;
    private JLabel labelRetour;
    private JLabel labelValidationNouveauMdp;
    private JLabel labelImage;
}
