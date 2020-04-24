
package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.Activity;
import fr.iutbourgogne.projetmsp.packModele.ActivityDAO;
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
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Classe représentant la fenêtre contenant les détails d'un projet
 * (liste des activités du projet)
 * @author Jorick
 */
public class VueDetailProjet extends JPanel {
    
    private User personne;
    
    public VueDetailProjet(User p) {
        this.personne = p;
        
        this.setPreferredSize(new Dimension(500, 470));
        initComponents();

        // on ajoute les listener
        addListener();
        
        // on affiche le nom du projet
        labelNom.setText(personne.getProjetEnCours().getNom());
        
        // on remplit les deux tableaux
        remplirTableau1();
        remplirTableau2();
    }
    
    /**
     * Méthode permettant de remplir le tableau du haut avec les activités du technicien
     */
    private void remplirTableau1() {
        
        // variable représentant la ligne du tableau dans laquelle
        // vont être inscrites les informations de l'activité
        int j = 0;
        
        tableauActivites.getTableHeader().setReorderingAllowed(false);
        tableauActivites.getTableHeader().setResizingAllowed(false);

        // récupère les activités du projet via la requête SQL
        ActivityDAO.findActivities(personne.getProjetEnCours());

        // remplissage des colonnes
        for (Activity i : personne.getProjetEnCours().getActivities()) {
            if(i.getIdTechnicien() == personne.getId()) {
                personne.addActivite(i);
                tableauActivites.setValueAt(i.getResume(), j, 0);
                tableauActivites.setValueAt(i.getStatut(), j, 1);
                j += 1;
            }
        }
    }
    
    /**
     * Méthode permettant de remplir le tableau du bas avec les autres activités
     * du projet qui ne sont pas réalisées par le technicien
     */
    private void remplirTableau2() {
        
        // variable représentant la ligne du tableau dans laquelle
        // vont être inscrites les informations de l'activité
        int j = 0;
        
        ArrayList<Activity> listeAutresActivites = new ArrayList();
        
        tableauAutresActivites.getTableHeader().setReorderingAllowed(false);
        tableauAutresActivites.getTableHeader().setResizingAllowed(false);

        // récupère les autres activités du projet via la requête SQL
        ActivityDAO.findActivities(personne.getProjetEnCours());

        // remplissage des colonnes
        for (Activity i : personne.getProjetEnCours().getActivities()) {
            boolean estDedans = false;
            boolean dejaPresent = false;

            for (Activity a : personne.getListeActivites()) {
                if (a.getId() == i.getId()) {
                    estDedans = true;
                }
            }
            
            for (Activity b : listeAutresActivites) {
                if (b.getId() == i.getId()) {
                    dejaPresent = true;
                }
            }
            
            if (!estDedans && !dejaPresent) {
                listeAutresActivites.add(i);
                tableauAutresActivites.setValueAt(i.getResume(), j, 0);
                tableauAutresActivites.setValueAt(i.getStatut(), j, 1);
                j += 1;
            }
        }
    }

    /**
     * Méthode permettant d'ajouter les Listener dans le constructeur
     */
    private void addListener() {
        labelRetour.addMouseListener(new Ecouteur_AccesReponse());
        tableauActivites.addMouseListener(new Ecouteur_AccesReponse());
        tableauAutresActivites.addMouseListener(new Ecouteur_AccesReponse());
        tableauActivites.addMouseMotionListener(new Ecouteur_AccesReponse());
        tableauAutresActivites.addMouseMotionListener(new Ecouteur_AccesReponse());
    }

    public class Ecouteur_AccesReponse implements ActionListener, MouseListener, MouseMotionListener {
        
        @Override
        public void mouseClicked(MouseEvent e) {
            
            // si l'utilisateur clique sur "retour"
            if (e.getSource() == labelRetour) {
                personne.notifyObservateurs("retour");  
            } 
            else if ((e.getSource() == tableauActivites) && (e.getClickCount() == 2)) {
                int ligne = tableauActivites.rowAtPoint(e.getPoint());
                int colonne = tableauActivites.columnAtPoint(e.getPoint());
                // s'il a cliqué sur la première colonne (nom du projet) et que la cellule n'est pas vide
                if ((colonne == 0) &&(tableauActivites.getValueAt(ligne, 0) != null)) {
                    
                    for (Activity i : personne.getProjetEnCours().getActivities()) { 
                        if (i.getResume().equals((String)(tableauActivites.getValueAt(ligne, 0)))) {
                            personne.getProjetEnCours().setActiviteEnCours(i);
                        }
                    }
                    
                    personne.notifyObservateurs("ouvreActivite");
                }
            }
            else if ((e.getSource() == tableauAutresActivites) && (e.getClickCount() == 2)) {
                int ligne = tableauAutresActivites.rowAtPoint(e.getPoint());
                int colonne = tableauAutresActivites.columnAtPoint(e.getPoint());
                // s'il a cliqué sur la première colonne (nom du projet) et que la cellule n'est pas vide
                if ((colonne == 0) &&(tableauAutresActivites.getValueAt(ligne, 0) != null)) {
                    
                    for (Activity i : personne.getProjetEnCours().getActivities()) { 
                        if (i.getResume().equals((String)(tableauAutresActivites.getValueAt(ligne, 0)))) {
                            personne.getProjetEnCours().setActiviteEnCours(i);
                        }
                    }
                    
                    personne.notifyObservateurs("ouvreActiviteLecture");
                }
            }
        }
    
        @Override public void actionPerformed(ActionEvent e) {}
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
            if ((tableauAutresActivites.columnAtPoint(e.getPoint())) == 0 && (tableauAutresActivites.getValueAt(tableauAutresActivites.rowAtPoint(e.getPoint()), 0) != null)) {
                tableauAutresActivites.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else {
                tableauAutresActivites.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               
            }
            if ((tableauActivites.columnAtPoint(e.getPoint())) == 0 && (tableauActivites.getValueAt(tableauActivites.rowAtPoint(e.getPoint()), 0) != null)) {
                tableauActivites.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else {
                tableauActivites.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        labelImageBas = new JLabel();
        labelTitre = new JLabel();
        labelRetour = new JLabel();
        labelNom = new JLabel();
        labelListe1 = new JLabel();
        labelListe2 = new JLabel();
        labelPrecision1 = new JLabel();
        labelPrecision2 = new JLabel();
        jSeparator1 = new JSeparator();
        tableauActivites = new JTable();
        jScrollPane1 = new JScrollPane();
        tableauAutresActivites = new JTable();
        jScrollPane2 = new JScrollPane();
        
        setBackground(new Color(255, 255, 255));
        
        labelImageBas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bandeau_bas.jpg")));
        
        labelTitre.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setText("Projet");

        labelNom.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        labelNom.setHorizontalAlignment(SwingConstants.CENTER);
        labelNom.setText("[nom_projet]");

        labelRetour.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelRetour.setForeground(new java.awt.Color(204, 204, 204));
        labelRetour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelRetour.setText("Retour");
        labelRetour.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                
        labelListe1.setFont(new Font("Tahoma", 0, 14)); 
        labelListe1.setHorizontalAlignment(SwingConstants.LEFT);
        labelListe1.setText("Liste de vos activités");
        
        labelPrecision1.setHorizontalAlignment(SwingConstants.CENTER);
        labelPrecision1.setText("Cliquez sur le nom de l'activité pour en voir les détails et la modifier");
        
        labelPrecision2.setHorizontalAlignment(SwingConstants.CENTER);
        labelPrecision2.setText("Cliquez sur le nom de l'activité pour en voir les détails");

        tableauActivites.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Résumé de l'activité", "Statut"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        tableauActivites.setSelectionBackground(new Color(255, 204, 0));
        tableauActivites.setSelectionForeground(new Color(0, 0, 0));
        tableauActivites.getColumnModel().getColumn(0).setPreferredWidth(300);
        jScrollPane1.setViewportView(tableauActivites);
        
        labelListe2.setFont(new Font("Tahoma", 0, 14)); 
        labelListe2.setHorizontalAlignment(SwingConstants.LEFT);
        labelListe2.setText("Liste des autres activités");
        
        tableauAutresActivites.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Résumé de l'activité", "Statut"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        tableauAutresActivites.setSelectionBackground(new Color(255, 204, 0));
        tableauAutresActivites.setSelectionForeground(new Color(0, 0, 0));
        tableauAutresActivites.getColumnModel().getColumn(0).setPreferredWidth(300);
        jScrollPane2.setViewportView(tableauAutresActivites);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(labelImageBas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelTitre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelNom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelRetour)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) 
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelListe1))
                .addGap(23, 23, 23))
            .addComponent(labelPrecision1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelListe2))
                .addGap(23, 23, 23))
            .addComponent(labelPrecision2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRetour))
                .addGap(15, 15, 15)
                .addComponent(labelTitre)
                .addGap(5, 5, 5)
                .addComponent(labelNom)
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(labelListe1)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelPrecision1)
                .addGap(25, 25, 25)
                .addComponent(labelListe2)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelPrecision2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(labelImageBas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }                    

    private JLabel labelImageBas;     
    private JLabel labelTitre;
    private JLabel labelRetour;
    private JLabel labelNom;
    private JLabel labelListe1;
    private JLabel labelListe2;
    private JLabel labelPrecision1;
    private JLabel labelPrecision2;
    private JSeparator jSeparator1;
    private JTable tableauActivites;
    private JScrollPane jScrollPane1;
    private JTable tableauAutresActivites;
    private JScrollPane jScrollPane2;
}
