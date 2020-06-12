
package fr.iutbourgogne.projetmsp.packVue;

import fr.iutbourgogne.projetmsp.packModele.Activity;
import fr.iutbourgogne.projetmsp.packModele.ActivityDAO;
import fr.iutbourgogne.projetmsp.packModele.User;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Classe représentant la fenêtre contenant les détails d'un projet
 * (liste des activités du projet)
 * 
 * @author Jorick
 */
public class VueDetailProjet extends JPanel {
    
    /**
     * Attribut représentant l'utilisateur
     */
    private User personne;
    
    /**
     * Constructeur de la vue
     * @param p = l'utilisateur 
     */
    public VueDetailProjet(User p) {
        this.personne = p;
        
        // dimensions de la vue
        this.setPreferredSize(new Dimension(500, 470));
        
        // ajout des composants
        initComponents();

        // on ajoute les listener
        addListener();
        
        // on affiche le nom du projet
        labelNom.setText(personne.getProjetEnCours().getNom());
        
        // on remplit le tableau du haut contenant les activités du technicien
        remplirTableau1();
        
        // on remplit le tableau du bas contenant les autres activités du projet
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

        // récupère les activités du projet via la requête SQL
        ActivityDAO.findActivities(personne.getProjetEnCours());

        // récupération du modèle du tableau
        DefaultTableModel model = (DefaultTableModel) tableauActivites.getModel();
        
        // remplissage des colonnes
        for (Activity i : personne.getProjetEnCours().getActivities()) {
            if(i.getIdTechnicien() == personne.getId()) {
                personne.addActivite(i);
                model.addRow(new Object[]{i.getResume(), i.getStatut()});
                
                // on colorie la case du statut
                tableauActivites.setDefaultRenderer(Object.class, new CustomRenderer());
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
        
        // liste contenant les autres activités du projet
        ArrayList<Activity> listeAutresActivites = new ArrayList();
        
        tableauAutresActivites.getTableHeader().setReorderingAllowed(false);

        // récupère les autres activités du projet via la requête SQL
        ActivityDAO.findActivities(personne.getProjetEnCours());

        // récupération du modèle du tableau
        DefaultTableModel model = (DefaultTableModel) tableauActivites.getModel();
        
        // on balaye les activités du projet
        for (Activity i : personne.getProjetEnCours().getActivities()) {
            
            // booleens permettant de savoir si une activité est déjà présente dans les tableaux
            boolean estDedans = false;
            boolean dejaPresent = false;

            // on balaye les activités du technicien
            for (Activity a : personne.getListeActivites()) {
                
                // si les deux activités sont les mêmes
                if (a.getId() == i.getId()) {
                    
                    // l'activité est déjà dans le tableau du haut
                    estDedans = true;
                }
            }
            
            // on balaye également le tableau du bas pour voir si l'activité n'a pas déjà été inscrite
            // car la même activité peut se retrouver écrite plusieurs si elle est affectée à plusieurs techniciens
            for (Activity b : listeAutresActivites) {
                
                // si l'activité est déjà présente
                if (b.getId() == i.getId()) {
                    dejaPresent = true;
                }
            }
            
            // si elle n'est pas dans le tableau du haut et déjà inscrite dans celui du bas
            if (!estDedans && !dejaPresent) {
                
                // on l'ajoute à la liste pour les tests
                listeAutresActivites.add(i);
                
                // on remplit les colonnes
                model.addRow(new Object[]{i.getResume(), i.getStatut()});
                
                // on colorie la case du statut
                tableauAutresActivites.setDefaultRenderer(Object.class, new CustomRenderer());
            }
        }
    }

    /**
     * Méthode permettant d'ajouter les Listener dans le constructeur
     */
    private void addListener() {
        labelRetour.addMouseListener(new Ecouteur());
        tableauActivites.addMouseListener(new Ecouteur());
        tableauAutresActivites.addMouseListener(new Ecouteur());
        tableauActivites.addMouseMotionListener(new Ecouteur());
        tableauAutresActivites.addMouseMotionListener(new Ecouteur());
    }

    // classe interne
    private class Ecouteur implements ActionListener, MouseListener, MouseMotionListener {
        
        @Override
        public void mouseClicked(MouseEvent e) {
            
            // si l'utilisateur clique sur "retour"
            if (e.getSource() == labelRetour) {
                personne.notifyObservateurs("retour");  
            } 
            
            // s'il double clique sur le tableau du haut
            else if ((e.getSource() == tableauActivites) && (e.getClickCount() == 2)) {
                
                // on récupère les coordonnées de quelle case il a cliqué
                int ligne = tableauActivites.rowAtPoint(e.getPoint());
                int colonne = tableauActivites.columnAtPoint(e.getPoint());
                
                // s'il a cliqué sur la première colonne (nom du projet) et que la cellule n'est pas vide
                if ((colonne == 0) &&(tableauActivites.getValueAt(ligne, 0) != null)) {
                    
                    // on balaye la liste des activités pour voir celle qui correspond à celle cliquée
                    for (Activity i : personne.getProjetEnCours().getActivities()) { 
                        if (i.getResume().equals((String)(tableauActivites.getValueAt(ligne, 0)))) {
                            personne.getProjetEnCours().setActiviteEnCours(i);
                        }
                    }
                    
                    // on ouvre le détail de l'activité (elle est modifiable)
                    personne.notifyObservateurs("ouvreActivite");
                }
            }
            
            // s'il double clique sur le tableau du bas
            else if ((e.getSource() == tableauAutresActivites) && (e.getClickCount() == 2)) {
                
                // on récupère les coordonnées de quelle case il a cliqué
                int ligne = tableauAutresActivites.rowAtPoint(e.getPoint());
                int colonne = tableauAutresActivites.columnAtPoint(e.getPoint());
                
                // s'il a cliqué sur la première colonne (nom du projet) et que la cellule n'est pas vide
                if ((colonne == 0) &&(tableauAutresActivites.getValueAt(ligne, 0) != null)) {
                    
                    // on balaye la liste des activités pour voir celle qui correspond à celle cliquée
                    for (Activity i : personne.getProjetEnCours().getActivities()) { 
                        if (i.getResume().equals((String)(tableauAutresActivites.getValueAt(ligne, 0)))) {
                            personne.getProjetEnCours().setActiviteEnCours(i);
                        }
                    }
                    
                    // on ouvre le détail de l'activité (en lecture seulement)
                    personne.notifyObservateurs("ouvreActiviteLecture");
                }
            }
        }
    
        @Override 
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
    
        @Override
        public void mouseMoved(MouseEvent e) {

            // on met un curseur en forme de main lorsqu'il survole une activité
            if (tableauAutresActivites.getModel().getRowCount() > 0) { // si le tableau est vide, on ne peut pas effectuer les tests suivants
                
                if ((tableauAutresActivites.columnAtPoint(e.getPoint())) == 0
                        && (tableauAutresActivites.getValueAt(tableauAutresActivites.rowAtPoint(e.getPoint()), 0) != null)) {
                    tableauAutresActivites.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    tableauAutresActivites.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                }
            }
            
            if (tableauActivites.getModel().getRowCount() > 0) { // si le tableau est vide, on ne peut pas effectuer les tests suivants
                
                if ((tableauActivites.columnAtPoint(e.getPoint())) == 0 && (tableauActivites.getValueAt(tableauActivites.rowAtPoint(e.getPoint()), 0) != null)) {
                    tableauActivites.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    tableauActivites.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }
        
        @Override public void actionPerformed(ActionEvent e) {}
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
    }

    /**
     * Classe interne permettant de colorier la cellule contenant le statut
     */
    class CustomRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          
            if (value != null) {
                switch (value.toString()) {
                    case "terminée":
                        c.setBackground(new Color(0, 255, 0, 30));
                        break;
                    case "en cours":
                        c.setBackground(new Color(0, 0, 255, 30));
                        break;
                    case "annulée":
                        c.setBackground(new Color(255, 0, 0, 30));
                        break;
                    case "prévue":
                        c.setBackground(new Color(255, 255, 0, 80));
                        break;
                    default:
                        c.setBackground(Color.WHITE);
                        break;
                }
            }
            
            return c;
        }
    }
    
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
            new Object [][] {},
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
        tableauActivites.setAutoCreateRowSorter(true);
        tableauActivites.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(tableauActivites);
        
        labelListe2.setFont(new Font("Tahoma", 0, 14)); 
        labelListe2.setHorizontalAlignment(SwingConstants.LEFT);
        labelListe2.setText("Liste des autres activités");
        
        tableauAutresActivites.setModel(new DefaultTableModel(
            new Object [][] {},
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
        tableauAutresActivites.setAutoCreateRowSorter(true);
        tableauAutresActivites.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
