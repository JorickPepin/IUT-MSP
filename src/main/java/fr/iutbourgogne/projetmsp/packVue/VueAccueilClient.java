package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Jorick
 */
public class VueAccueilClient extends JPanel {

    private User personne;

    public VueAccueilClient(User p) {
        this.personne = p;
        
        this.setPreferredSize(new Dimension(500, 500));
        initComponents();
        
        labelBienvenue.setText("Bienvenue " + this.personne.getPrenom() + " " + this.personne.getNom());
        
        labelDeco.addMouseListener(new Ecouteur_AccesReponse());
        labelChangeMdp.addMouseListener(new Ecouteur_AccesReponse());
    }

    public class Ecouteur_AccesReponse implements ActionListener, MouseListener {
        
        @Override public void actionPerformed(ActionEvent e) {}
       
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == labelDeco) {
                personne.notifyObservateurs("deconnexion");  
            } else if (e.getSource() == labelChangeMdp) {
                personne.notifyObservateurs("changeMdp");  
            }
        }
    
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {} 
    }
    
    @SuppressWarnings("unchecked")                      
    private void initComponents() {
        
        labelImageBas = new JLabel();
        labelImageNoContent = new JLabel();
        labelNoContent = new JLabel();
        labelBienvenue = new JLabel();
        labelDeco = new JLabel();
        labelChangeMdp = new JLabel();
        labelRole = new JLabel();
        
        setBackground(new Color(255, 255, 255));
        
        labelImageBas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bandeau_bas.jpg")));
        
        labelBienvenue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelBienvenue.setHorizontalAlignment(SwingConstants.CENTER);
        labelBienvenue.setText("Bienvenue Nom de la personne");
        
        labelRole.setHorizontalAlignment(SwingConstants.CENTER);
        labelRole.setText("Vous êtes connecté en tant que Client");

        labelImageNoContent.setHorizontalAlignment(SwingConstants.CENTER);
        labelImageNoContent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nocontent.jpg")));
        
        labelNoContent.setHorizontalAlignment(SwingConstants.CENTER);
        labelNoContent.setText("Aucune action disponible pour le moment");
        
        labelDeco.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelDeco.setForeground(new java.awt.Color(204, 204, 204));
        labelDeco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelDeco.setText("Déconnexion ");
        labelDeco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelChangeMdp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelChangeMdp.setForeground(new java.awt.Color(204, 204, 204));
        labelChangeMdp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelChangeMdp.setText("Changer le mot de passe");
        labelChangeMdp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(labelImageBas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelRole, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelImageNoContent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelNoContent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelChangeMdp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelDeco)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDeco)
                    .addComponent(labelChangeMdp))
                .addGap(18, 18, 18)
                .addComponent(labelBienvenue)
                .addGap(5, 5, 5)
                .addComponent(labelRole)
                .addGap(50, 50, 50)
                .addComponent(labelImageNoContent)
                .addGap(5, 5, 5)
                .addComponent(labelNoContent)
                .addContainerGap(441, Short.MAX_VALUE)
                .addComponent(labelImageBas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }                    

    private JLabel labelImageBas;  
    private JLabel labelImageNoContent;
    private JLabel labelNoContent;
    private JLabel labelBienvenue;
    private JLabel labelChangeMdp;
    private JLabel labelDeco;
    private JLabel labelRole;    
}
