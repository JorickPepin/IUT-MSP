
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
import java.util.Properties;
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
import fr.iutbourgogne.projetmsp.packModele.ActivityDAO;
import java.util.Calendar;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Classe représentant la vue de modification d'une activité
 * 
 * @author Jorick
 */
public class VueModificationActivite extends JPanel {
    
    /**
     * Attribut représentant l'utilisateur
     */
    private User personne;
    
    /**
     * Attribut contenant la date de début de l'activité
     */
    private JDatePickerImpl dateDebut;
    
    /**
     * Attribut contenant la date de fin de l'activité
     */
    private JDatePickerImpl dateFin;
    
    /**
     * Constructeur de la vue
     * @param p = l'utilisateur
     */
    public VueModificationActivite(User p) {
        this.personne = p;
        
        // dimensions de la vue
        this.setPreferredSize(new Dimension(500, 400));
        
        // ajout des composants
        initComponents();
        
        // ajout des listener
        addListener();
        
        // on ajoute les informations de l'activité
        labelNom.setText(personne.getProjetEnCours().getActiviteEnCours().getResume());
        details.setText(personne.getProjetEnCours().getActiviteEnCours().getDetail());
        type.setText(personne.getProjetEnCours().getActiviteEnCours().getType()); 
    }
    
    private class Ecouteur implements ActionListener, MouseListener, MouseMotionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            // si l'utilisateur clique sur "enregistrer"
            if (e.getSource() == boutonValidation){
                VueModificationActivite.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                
                // on met à jour le détail de l'activité
                personne.getProjetEnCours().getActiviteEnCours().setDetail(details.getText());
                ActivityDAO.updateActivityDetail(personne.getProjetEnCours().getActiviteEnCours().getId(), details.getText());
                
                // on met à jour le statut de l'activité suivant le bouton selectionné
                switch (buttonSelected()) {
                    case "en cours":
                        personne.getProjetEnCours().getActiviteEnCours().setStatut("en cours");
                        ActivityDAO.updateActivityStatut(personne.getProjetEnCours().getActiviteEnCours().getId(), "en cours");
                        break;
                    case "annulée":
                        personne.getProjetEnCours().getActiviteEnCours().setStatut("annulée");
                        ActivityDAO.updateActivityStatut(personne.getProjetEnCours().getActiviteEnCours().getId(), "annulée");
                        break;
                    case "terminée":
                        personne.getProjetEnCours().getActiviteEnCours().setStatut("terminée");
                        ActivityDAO.updateActivityStatut(personne.getProjetEnCours().getActiviteEnCours().getId(), "terminée");
                        break; 
                    case "prévue":
                        personne.getProjetEnCours().getActiviteEnCours().setStatut("prévue");
                        ActivityDAO.updateActivityStatut(personne.getProjetEnCours().getActiviteEnCours().getId(), "prévue");
                        break;
                }
                
                // on met à jour la date de début de l'activité si elle est renseignée
                if (dateDebut.getModel().getValue() != null) {
                    int monthD = dateDebut.getModel().getMonth();
                    int dayD = dateDebut.getModel().getDay();
                    int yearD = dateDebut.getModel().getYear();

                    Calendar calDebut = Calendar.getInstance();

                    calDebut.set(Calendar.YEAR, yearD);
                    calDebut.set(Calendar.MONTH, monthD);
                    calDebut.set(Calendar.DATE, dayD);

                    Date jsqlD = new Date(calDebut.getTime().getTime());
                    personne.getProjetEnCours().getActiviteEnCours().setDateDebut(jsqlD);
                    ActivityDAO.updateActivityDateDebut(personne.getProjetEnCours().getActiviteEnCours().getId(), jsqlD);
                }
                
                // on met à jour la date de fin de l'activité si elle est renseignée
                if (dateFin.getModel().getValue() != null) {
                    int monthF = dateFin.getModel().getMonth();
                    int dayF = dateFin.getModel().getDay();
                    int yearF = dateFin.getModel().getYear();

                    Calendar calFin = Calendar.getInstance();

                    calFin.set(Calendar.YEAR, yearF);
                    calFin.set(Calendar.MONTH, monthF);
                    calFin.set(Calendar.DATE, dayF);

                    Date jsqlF = new Date(calFin.getTime().getTime());
                    personne.getProjetEnCours().getActiviteEnCours().setDateFin(jsqlF);
                    ActivityDAO.updateActivityDateFin(personne.getProjetEnCours().getActiviteEnCours().getId(), jsqlF);
                }
                
                // fenêtre de confirmation
                JOptionPane.showMessageDialog(null, "Les modifications ont été enregistrées.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                
                // on retourne à la vue d'affiche du détail de l'activité
                personne.notifyObservateurs("ouvreActivite");
            }
        } 

        @Override
        public void mouseClicked(MouseEvent e) {
            
            // si l'utilisateur clique sur "annuler"
            if (e.getSource() == labelAnnule) {
                creationFenetreAnnulation();
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
        
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
    }
    
    /**
     * Méthode permettant d'ajouter les listener dans le constructeur
     */
    private void addListener() {
        labelAnnule.addMouseListener(new Ecouteur());
        boutonValidation.addActionListener(new Ecouteur());     
        boutonValidation.addMouseListener(new Ecouteur());
        boutonValidation.addMouseMotionListener(new Ecouteur());
    }
    
    /**
     * Méthode permettant de savoir quel boutton est selectionné
     * @return le statut de l'activité qui est selectionné
     */
    private String buttonSelected() {
        String result = ""; 
        
        if (jRadioButtonEnCours.isSelected()) {
            result = "en cours";
        }
        if (jRadioButtonPrevue.isSelected()) {
            result = "prévue";
        }
        if (jRadioButtonTerminee.isSelected()) {
            result = "terminée";
        }
        if (jRadioButtonAnnulee.isSelected()) {
            result = "annulée";
        }
        
        return result;
    }
    
    /**
     * Méthode permettant de créer la fenêtre lorsque l'utilisateur clique sur "annuler"
     */
    private void creationFenetreAnnulation() {
        UIManager.put("OptionPane.yesButtonText", "Oui");
        UIManager.put("OptionPane.noButtonText", "Non");

        int reply = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir annuler ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            personne.notifyObservateurs("retourModification");
        }
    }
    
    private void initComponents() {

        boutonValidation = new JButton();
        labelImageBas = new JLabel();
        labelTitre = new JLabel();
        labelAnnule = new JLabel();
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
        
        jRadioButtonEnCours = new JRadioButton();
        jRadioButtonPrevue = new JRadioButton();
        jRadioButtonTerminee = new JRadioButton();
        jRadioButtonAnnulee = new JRadioButton();
        buttonGroup1 = new ButtonGroup();
   
        setBackground(new Color(255, 255, 255));
        
        Border border = BorderFactory.createLineBorder(new Color(160,160,160), 1);
        Border borderModif = BorderFactory.createLineBorder(new Color(255, 204, 0), 1);
 
        labelImageBas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bandeau_bas.jpg")));
        
        boutonValidation.setBackground(new java.awt.Color(255, 204, 0));
        boutonValidation.setText("Enregistrer");
        
        labelTitre.setFont(new Font("Tahoma", 0, 14));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setText("Modification de l'activité");

        labelNom.setFont(new Font("Tahoma", 1, 14));
        labelNom.setHorizontalAlignment(SwingConstants.CENTER);
        labelNom.setText("[nom_activité]");

        labelAnnule.setFont(new Font("Tahoma", 1, 11));
        labelAnnule.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelAnnule.setText("Annuler");
        labelAnnule.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
         
        labelDetail.setFont(new Font("Tahoma", 0, 14));
        labelDetail.setText("Détail");
        
        scrollPaneDetails.setPreferredSize(new Dimension(150, 100));
        scrollPaneDetails.setBorder(border);
        
        details.setFont(new Font("Tahoma", 1, 12));
        details.setText("[détail_activité]");
        
        details.setWrapStyleWord(true);
        details.setLineWrap(true);
        details.setCaretPosition(0);
        details.setEditable(true);
        
        labelType.setFont(new Font("Tahoma", 0, 14)); 
        labelType.setText("Activité de type");
        
        type.setFont(new Font("Tahoma", 1, 12));
        type.setText("[type_activité]");
        type.setPreferredSize(new Dimension(150, 20)); 
        type.setEditable(false);
        type.setBorder(border);
        type.setForeground(Color.GRAY);
        
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
        
        jRadioButtonEnCours.setEnabled(true);
        jRadioButtonPrevue.setEnabled(true);
        jRadioButtonTerminee.setEnabled(true);
        jRadioButtonAnnulee.setEnabled(true);
        
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
        
        
        Properties p = new Properties();
	p.put("text.today", "Aujourd'hui");
	p.put("text.month", "mois");
	p.put("text.year", "année"); 
        

        UtilDateModel model1 = new UtilDateModel();
	JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
	JDatePickerImpl dateDebut = new JDatePickerImpl(datePanel1, new DateComponentFormatter());
        model1.setSelected(false);
        
        datePanel1.setBackground(Color.white);
        dateDebut.setBackground(Color.white);
        
        Calendar cal1 = Calendar.getInstance();
        
        if (personne.getProjetEnCours().getActiviteEnCours().getDateDebut() != null) {
            cal1.setTime(personne.getProjetEnCours().getActiviteEnCours().getDateDebut());
            int monthDebut = cal1.get(Calendar.MONTH);
            int dayDebut = cal1.get(Calendar.DAY_OF_MONTH);
            int yearDebut = cal1.get(Calendar.YEAR);
        
            model1.setDate(yearDebut, monthDebut, dayDebut);
            model1.setSelected(true);
        }
      
        UtilDateModel model2 = new UtilDateModel();
	JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
	JDatePickerImpl dateFin = new JDatePickerImpl(datePanel2, new DateComponentFormatter());
        model2.setSelected(false);
        
        datePanel2.setBackground(Color.white);
        dateFin.setBackground(Color.white);
        
        Calendar cal2 = Calendar.getInstance();
        
        if (personne.getProjetEnCours().getActiviteEnCours().getDateFin() != null) {
            cal2.setTime(personne.getProjetEnCours().getActiviteEnCours().getDateFin());
            int monthFin = cal2.get(Calendar.MONTH);
            int dayFin = cal2.get(Calendar.DAY_OF_MONTH);
            int yearFin = cal2.get(Calendar.YEAR);
        
            model2.setDate(yearFin, monthFin, dayFin);
            model2.setSelected(true);
        }
        
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelImageBas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelNom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelTitre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
               
                .addComponent(labelAnnule)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup())
                        .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(boutonValidation, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24)
                                .addComponent(labelDateDebut)
                            .addComponent(dateDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                           .addComponent(labelDateFin))))
                .addContainerGap(50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelAnnule)
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
                    .addComponent(boutonValidation)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(labelImageBas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }                    

            
    private JButton boutonValidation;
    private JLabel labelImageBas;     
    private JLabel labelTitre;
    private JLabel labelAnnule;
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
    private JRadioButton jRadioButtonEnCours;
    private JRadioButton jRadioButtonPrevue;
    private JRadioButton jRadioButtonTerminee;
    private JRadioButton jRadioButtonAnnulee;
    private ButtonGroup buttonGroup1;
}
