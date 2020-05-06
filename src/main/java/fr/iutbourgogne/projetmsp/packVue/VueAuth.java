package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.User;
import fr.iutbourgogne.projetmsp.packModele.UserDAO;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * Classe représentant la vue d'identification
 * 
 * @author Jorick
 */
public class VueAuth extends JPanel {
    
    /**
     * Attribut représentant l'utilisateur
     */
    private User personne;
    
    public VueAuth(User p) {
        this.personne = p;
        
        // ajout des composants
        initComponents();
        
        // ajout des listener
        addListener();
    }
    
    // Classe interne, interception de la connexion
    private class Ecouteur_Connexion implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // si l'utilisateur clique sur le bouton "connexion"
            if (e.getSource() == boutonConnexion){
                connexion();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
            // si l'utilisateur appuie sur la touche "entrée"
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                connexion();
            }
        }
        
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
        
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyReleased(KeyEvent e) {}
    }
    
    
    /**
     * Méthode contenant toutes les actions effectuées lorsque l'utilisateur 
     * clique sur boutonConnexion ou sur la touche entrée
     */
    private void connexion() {
        VueAuth.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        // liste contenant le login et le mot de passe
        ArrayList<String> identifiants = new ArrayList();

        // on ajoute le login qu'il a rentré
        identifiants.add(champPseudo.getText());
        
        // on ajoute le mot de passe qu'il a rentré
        identifiants.add(String.valueOf(champPassword.getPassword()));

        // on lance une recherche dans la bdd pour trouver l'utilisateur 
        // correspondant au login entré
        User user = UserDAO.findWithLogin(identifiants.get(0));
        
        user.addObservateur((Observateur) SwingUtilities.getWindowAncestor(VueAuth.this));
        
        // création de la bordure rouge pour l'appliquer quand nécessaire
        Border borderRed = BorderFactory.createLineBorder(Color.RED, 1);

        // échec : le login de la personne créée vaut "error", c'est que le login n'a
        // pas été trouvé dans la base données
        if (user.getLogin().equals("error")) {
            personne = user;
            
            JOptionPane.showMessageDialog(null, "Le nom d'utilisateur ou le mot de passe est incorrect.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);

            // la bordure des champs devient rouge
            champPseudo.setBorder(borderRed);
            champPassword.setBorder(borderRed);
            
        } 

        // échec : l'identifiant est bon mais pas le mot de passe
        else if (identifiants.get(0).equals(user.getLogin())
                && !identifiants.get(1).equals(user.getPassword())) {
            personne = user;
            
            JOptionPane.showMessageDialog(null, "Le nom d'utilisateur ou le mot de passe est incorrect.", "Informations incorrectes", JOptionPane.ERROR_MESSAGE);
            
            // la bordure des champs devient rouge
            champPseudo.setBorder(borderRed);
            champPassword.setBorder(borderRed);
        }
        
        // succès : l'identifiant et le mot de passe sont corrects
        else if (identifiants.get(0).equals(user.getLogin())
                && identifiants.get(1).equals(user.getPassword())) {
            personne = user;
            
            // on avertit que la connexion a réussi
            personne.notifyObservateurs("connexionSuccess");
        }

        VueAuth.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Méthode permettant d'ajouter les Listener dans le constructeur
     */
    private void addListener() {
        boutonConnexion.addActionListener(new Ecouteur_Connexion());
        boutonConnexion.addMouseListener(new Ecouteur_Connexion());
        boutonConnexion.addMouseMotionListener(new Ecouteur_Connexion());
        champPseudo.addKeyListener(new Ecouteur_Connexion());
        champPassword.addKeyListener(new Ecouteur_Connexion());   
        boutonConnexion.addKeyListener(new Ecouteur_Connexion()); 
    }
    
    public User getPersonne() {
        return personne;
    }
   
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
