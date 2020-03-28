
package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModels.User;
import fr.iutbourgogne.projetmsp.packModels.UserDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.border.Border;

/**
 *
 * @author Jorick
 */
public class VueChangeMdp extends JPanel {
    
    private User personne;
    
    public VueChangeMdp(User p) {
        this.personne = p;
        
        this.setPreferredSize(new Dimension(500, 300));
        initComponents();
        
        labelRetour.addMouseListener(new Ecouteur_AccesReponse());
        boutonValidation.addActionListener(new Ecouteur_AccesReponse());   
        boutonValidation.addMouseListener(new Ecouteur_AccesReponse());
        boutonValidation.addMouseMotionListener(new Ecouteur_AccesReponse());
    }
    
        public class Ecouteur_AccesReponse implements ActionListener, MouseListener, MouseMotionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            Border borderRed = BorderFactory.createLineBorder(Color.RED, 1);

            if (e.getSource() == boutonValidation){
                VueChangeMdp.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                if (champAncienMdp.getPassword().length != 0 && champNouveauMdp.getPassword().length != 0 && champValidationNouveauMdp.getPassword().length != 0) { // si aucun des champs ne sont vides
                    if (String.valueOf(champAncienMdp.getPassword()).equals(personne.getPassword())) { // si le mot de passe rentré correspond au mot de passe actuel
                        if (String.valueOf(champNouveauMdp.getPassword()).equals(String.valueOf(champValidationNouveauMdp.getPassword()))) { // si le nouveau mot de passe et le mot de passe de validation sont identiques
                            if (String.valueOf(champNouveauMdp.getPassword()).length() > 3) { // si le nouveau mot de passe fait au moins 4 caractères (valeur basse pour faciliter le test mais modifiable)    
                                personne = UserDAO.updateMdp(String.valueOf(champNouveauMdp.getPassword()), personne);
                                personne.notifyObservateurs("changeMdpSuccess");
                                initChamps();
                            }
                            else {
                                personne.notifyObservateurs("changeMdpEchec4");
                                initChamps();
                                champNouveauMdp.setBorder(borderRed);
                                champValidationNouveauMdp.setBorder(borderRed);
                            }
                        }
                        else {
                            personne.notifyObservateurs("changeMdpEchec2");
                            initChamps();
                            champNouveauMdp.setBorder(borderRed);
                            champValidationNouveauMdp.setBorder(borderRed);
                        }
                    }
                    else {
                        personne.notifyObservateurs("changeMdpEchec");
                        initChamps();
                        champAncienMdp.setBorder(borderRed);
                    }
                }
                else {
                    personne.notifyObservateurs("changeMdpEchec3");
                    initChamps();
                        
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
        }
       
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == labelRetour) {
                personne.notifyObservateurs("retour");  
            } 
        }
    
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}

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
    }
    
    /**
     * Méthode permettant de réinitialiser les bordures des champs de mot de passe
     */
    private void initChamps() {
        champAncienMdp.setBorder(new JPasswordField().getBorder());
        champNouveauMdp.setBorder(new JPasswordField().getBorder());
        champValidationNouveauMdp.setBorder(new JPasswordField().getBorder());        
    }
    
    @SuppressWarnings("unchecked")                      
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

        labelAncienMdp.setText("Ancien mot de passe :");
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