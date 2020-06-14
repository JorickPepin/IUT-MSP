
package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.User;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DateFormat;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Classe représentant la vue détaillant une activité lorsque
 * le technicien est en charge de celle-ci (il peut la modifier)
 * 
 * @author Jorick
 */
public class VueDetailActivite extends JPanel {
    
    /**
     * Attribut représentant l'utilisateur
     */
    private User personne;
    
    /**
     * Constructeur de la vue
     * @param p = l'utilisateur
     */
    public VueDetailActivite(User p) {
        this.personne = p;
        
        // dimensions de la vue
        this.setPreferredSize(new Dimension(500, 400));
        
        // ajout des composants
        initComponents();
        
        // ajout des listener
        addListener();
        
        // on affiche le nom de l'activité
        labelNom.setText(personne.getProjetEnCours().getActiviteEnCours().getResume());
        
        // on affiche le détail de l'activité
        details.setText(personne.getProjetEnCours().getActiviteEnCours().getDetail());
        
        // permet de laisser la fenêtre contenant le détail en haut même si le 
        // texte dépasse (scroll en haut)
        details.setCaretPosition(0);
        
        // on affiche le type de l'activité
        type.setText(personne.getProjetEnCours().getActiviteEnCours().getType());
        
        // on remplit les cadres contenant les dates
        remplirDates();
    }
    
    /**
     * Méthode permettant de remplir les cadres contenant les dates
     */
    private void remplirDates() {
        DateFormat mediumDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        // si la date n'est pas renseignée dans la bdd
        if (personne.getProjetEnCours().getActiviteEnCours().getDateDebut() == null) {
            
            // on affiche "non renseignée"
            dateDebut.setText("Non renseignée");
            
        // sinon on affiche la date
        } else {
            dateDebut.setText(mediumDateFormat.format(personne.getProjetEnCours().getActiviteEnCours().getDateDebut()));
        }
        
        // si la date n'est pas renseignée dans la bdd
        if (personne.getProjetEnCours().getActiviteEnCours().getDateFin() == null) {
            
            // on affiche "non renseignée"
            dateFin.setText("Non renseignée");
            
        // sinon on affiche la date
        } else {
            dateFin.setText(mediumDateFormat.format(personne.getProjetEnCours().getActiviteEnCours().getDateFin()));
        }
    }
    
    private class Ecouteur implements ActionListener, MouseListener, MouseMotionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // si l'utilisateur clique sur "modifier"
            if (e.getSource() == boutonModification) {
                VueDetailActivite.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                personne.notifyObservateurs("ouvreModificationActivite");
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            
            // si l'utilisateur clique sur "retour"
            if (e.getSource() == labelRetour) {
                personne.notifyObservateurs("retourProjet");  
            } 
        }
    
        @Override 
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
        
        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getComponent() == boutonModification) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
        
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
    }
    
    /**
     * Méthode permettant d'ajouter les listener dans le constructeur
     */
    private void addListener() {
        labelRetour.addMouseListener(new Ecouteur());
        boutonModification.addActionListener(new Ecouteur()); 
        boutonModification.addMouseListener(new Ecouteur());
        boutonModification.addMouseMotionListener(new Ecouteur());
    }
    
    private void initComponents() {
        
        boutonModification = new JButton();
        labelImageBas = new JLabel();
        labelTitre = new JLabel();
        labelRetour = new JLabel();
        labelNom = new JLabel();
        labelDetail = new JLabel();
        labelType = new JLabel();
        labelStatut = new JLabel();
        labelDateDebut = new JLabel();
        labelDateFin = new JLabel();
        jSeparator1 = new JSeparator();
        details = new JTextArea();
        scrollPaneDetails = new JScrollPane(details, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        type = new JTextArea();
        dateDebut = new JTextArea();
        dateFin = new JTextArea();
        
        jRadioButtonEnCours = new JRadioButton();
        jRadioButtonPrevue = new JRadioButton();
        jRadioButtonTerminee = new JRadioButton();
        jRadioButtonAnnulee = new JRadioButton();
        buttonGroup1 = new ButtonGroup();
   
        setBackground(new Color(255, 255, 255));
        Border border = BorderFactory.createLineBorder(new Color(160,160,160), 1);
 
        labelImageBas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bandeau_bas.jpg")));
        
        boutonModification.setBackground(new java.awt.Color(255, 204, 0));
        boutonModification.setText("Modifier");
        
        labelTitre.setFont(new Font("Tahoma", 0, 14));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setText("Détail de l'activité");

        labelNom.setFont(new Font("Tahoma", 1, 14));
        labelNom.setHorizontalAlignment(SwingConstants.CENTER);
        labelNom.setText("[nom_activité]");

        labelRetour.setFont(new Font("Tahoma", 1, 11));
        labelRetour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelRetour.setText("Retour");
        labelRetour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
         
        labelDetail.setFont(new Font("Tahoma", 0, 14));
        labelDetail.setText("Détail");
        
        scrollPaneDetails.setPreferredSize(new Dimension(150, 100));
        scrollPaneDetails.setBorder(border);
        
        details.setFont(new Font("Tahoma", 1, 12));
        details.setText("[détail_activité]");
        details.setForeground(Color.GRAY);
        details.setWrapStyleWord(true);
        details.setLineWrap(true);
        details.setEditable(false);
        
        labelType.setFont(new Font("Tahoma", 0, 14)); 
        labelType.setText("Activité de type");
        
        type.setFont(new Font("Tahoma", 1, 12));
        type.setText("[type_activité]");
        type.setPreferredSize(new Dimension(150, 20)); 
        type.setEditable(false);
        type.setBorder(border);
        type.setForeground(Color.GRAY);
        
        dateDebut.setFont(new Font("Tahoma", 1, 12));
        dateDebut.setText("[date_début]");
        dateDebut.setPreferredSize(new Dimension(100, 20)); 
        dateDebut.setEditable(false);
        dateDebut.setBorder(border);
        dateDebut.setForeground(Color.GRAY);

        dateFin.setFont(new Font("Tahoma", 1, 12));
        dateFin.setText("[date_fin]");
        dateFin.setPreferredSize(new Dimension(100, 20)); 
        dateFin.setEditable(false);
        dateFin.setBorder(border);
        dateFin.setForeground(Color.GRAY);
        
        labelStatut.setFont(new Font("Tahoma", 0, 14)); 
        labelStatut.setText("Statut");       
        
        buttonGroup1.add(jRadioButtonEnCours);
        jRadioButtonEnCours.setText("En cours");
        
        buttonGroup1.add(jRadioButtonPrevue);
        jRadioButtonPrevue.setText("Prévue");
        
        buttonGroup1.add(jRadioButtonTerminee);
        jRadioButtonTerminee.setText("Terminée");
        
        buttonGroup1.add(jRadioButtonAnnulee);
        jRadioButtonAnnulee.setText("Annulée");
        
        jRadioButtonEnCours.setBackground(new Color(255, 255, 255));
        jRadioButtonPrevue.setBackground(new Color(255, 255, 255));
        jRadioButtonTerminee.setBackground(new Color(255, 255, 255));
        jRadioButtonAnnulee.setBackground(new Color(255, 255, 255));
        
        jRadioButtonEnCours.setEnabled(false);
        jRadioButtonPrevue.setEnabled(false);
        jRadioButtonTerminee.setEnabled(false);
        jRadioButtonAnnulee.setEnabled(false);
        
        // on selectionne le bouton radio suivant le statut de l'activité
        switch (personne.getProjetEnCours().getActiviteEnCours().getStatut()) {
            case "prévue":
                jRadioButtonPrevue.setSelected(true);
                break;
            case "en cours":
                jRadioButtonEnCours.setSelected(true);
                break;
            case "terminée":
                jRadioButtonTerminee.setSelected(true);
                break;
            case "annulée":
                jRadioButtonAnnulee.setSelected(true);
                break;
        }
        
        labelDateDebut.setFont(new Font("Tahoma", 0, 14)); 
        labelDateDebut.setText("Date de début");
        
        labelDateFin.setFont(new Font("Tahoma", 0, 14)); 
        labelDateFin.setText("Date de fin");
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelImageBas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelNom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelTitre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
               
                .addComponent(labelRetour)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup())
                        .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(boutonModification, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                               
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(103, 103, 103)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelDetail)
                            .addComponent(labelType)
                            .addComponent(scrollPaneDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(type))
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonEnCours)
                            .addComponent(jRadioButtonPrevue)
                            .addComponent(jRadioButtonTerminee)
                            .addComponent(jRadioButtonAnnulee)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(labelStatut)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateFin)
                            .addGap(24, 24, 24)
                                .addComponent(labelDateDebut)
                            .addComponent(dateDebut)
                           .addComponent(labelDateFin))))
                .addContainerGap(50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelRetour)
                .addGap(23, 23, 23)
                .addComponent(labelTitre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelType)
                            .addComponent(labelDateDebut))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                            .addGap(4)
                                        .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(labelDetail)
                                            .addGap(4)
                                        .addComponent(scrollPaneDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelDateFin)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(labelStatut)
                                    .addGap(4)
                                .addComponent(jRadioButtonEnCours)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonPrevue)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonTerminee, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonAnnulee))))
                    .addGroup(layout.createSequentialGroup()
                            .addGap(4)
                        .addComponent(dateDebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGap(40, 40, 40)
                    .addComponent(boutonModification)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(labelImageBas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }                    

    private JButton boutonModification;
    private JLabel labelImageBas;     
    private JLabel labelTitre;
    private JLabel labelRetour;
    private JLabel labelNom;
    private JLabel labelDetail;
    private JLabel labelType;
    private JLabel labelStatut;
    private JLabel labelDateDebut;
    private JLabel labelDateFin;
    private JSeparator jSeparator1;
    private JTextArea details;
    private JScrollPane scrollPaneDetails;
    private JTextArea type;
    private JTextArea dateDebut;
    private JTextArea dateFin;
    private JRadioButton jRadioButtonEnCours;
    private JRadioButton jRadioButtonPrevue;
    private JRadioButton jRadioButtonTerminee;
    private JRadioButton jRadioButtonAnnulee;
    private ButtonGroup buttonGroup1;
}
