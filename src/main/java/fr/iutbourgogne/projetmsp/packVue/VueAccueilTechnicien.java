
package fr.iutbourgogne.projetmsp.packVue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import fr.iutbourgogne.projetmsp.packModele.User;
import fr.iutbourgogne.projetmsp.packModele.Projet;
import fr.iutbourgogne.projetmsp.packModele.ProjetDAO;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * Classe représentant la vue d'accueil (post authentification) pour un technicien
 * 
 * @author Jorick
 */
public class VueAccueilTechnicien extends JPanel {

    /**
     * Attribut représentant la personne qui s'est connectée
     */
    private User personne;

    /**
     * Constructeur de la vue
     * @param p = l'utilisateur
     */
    public VueAccueilTechnicien(User p) {
        this.personne = p;
        
        // dimensions de la vue
        this.setPreferredSize(new Dimension(500, 450));
        
        // ajout des composants
        initComponents();
        
        // on remplace le texte sur label en ajoutant le nom et prénom du technicien
        labelBienvenue.setText("Bienvenue " + personne.getPrenom() + " " + personne.getNom());
        
        // ajout des listener
        labelDeco.addMouseListener(new Ecouteur());
        labelChangeMdp.addMouseListener(new Ecouteur());
        tableauProjets.addMouseListener(new Ecouteur());
        tableauProjets.addMouseMotionListener(new Ecouteur());
        
        // remplissage du tableau avec la liste des projets
        Remplir_tableau();
        
        
    }

    /**
     * Méthode permettant de remplir le tableau contenant la liste des projets
     */
    private void Remplir_tableau() {
        
        // index de la ligne du tableau
        int j = 0;
        
        tableauProjets.getTableHeader().setReorderingAllowed(false);
        tableauProjets.getTableHeader().setResizingAllowed(true);
        
        // récupère les projets du technicien via la requête SQL
        ProjetDAO.findProjets(personne);
      
        // récupération du modèle du tableau
        DefaultTableModel model = (DefaultTableModel) tableauProjets.getModel();
 
        // remplissage des colonnes
        for (Projet i : personne.getListeProjets()) {
            model.addRow(new Object[]{i.getNom(), i.getDureeEstimee(), i.getDureeFinale(), i.getStatut()});
            
            // on colorie la case du statut
            tableauProjets.setDefaultRenderer(Object.class, new CustomRenderer());
        } 
        
    }

    // classe interne
    private class Ecouteur implements ActionListener, MouseListener, MouseMotionListener {
       
        @Override
        public void mouseClicked(MouseEvent e) {
            
            // si utilisateur clique sur déconnexion
            if (e.getSource() == labelDeco) {
                personne.notifyObservateurs("deconnexion");  
                
            // si utilisateur clique sur changement de mdp
            } else if (e.getSource() == labelChangeMdp) {
                personne.notifyObservateurs("changeMdp");  
                
            // si utilisateur double-clique sur le tableau
            } else if ((e.getSource() == tableauProjets) && (e.getClickCount() == 2)) {
                
                // on récupère la ligne et la colonne sur lesquelles il a cliqué pour avoir la case
                int ligne = tableauProjets.rowAtPoint(e.getPoint());
                int colonne = tableauProjets.columnAtPoint(e.getPoint());
                
                // s'il a cliqué sur la première colonne (nom du projet) et que la cellule n'est pas vide
                if ((colonne == 0) && (tableauProjets.getValueAt(ligne, 0) != null)) {
                    
                    // on parcourt la liste des projets pour trouver celui qui correspond à celui sur lequel l'utilisateur a cliqué
                    for (Projet i : personne.getListeProjets()) { 
                        if (i.getNom().equals((String)(tableauProjets.getValueAt(ligne, 0)))) {
                           personne.setProjetEnCours(i);
                        }
                    }
                    personne.notifyObservateurs("ouvreProjet");
                }
            }
        }
    
        @Override 
        public void mouseExited(MouseEvent e) {
            tableauProjets.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
        
        @Override
        public void mouseMoved(MouseEvent e) {
            if ((tableauProjets.columnAtPoint(e.getPoint())) == 0 
                    && (tableauProjets.getValueAt(tableauProjets.rowAtPoint(e.getPoint()), 0) != null)) {
                tableauProjets.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else {
                tableauProjets.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
                    case "fini":
                        c.setBackground(new Color(0, 255, 0, 30));
                        break;
                    case "en cours":
                        c.setBackground(new Color(0, 0, 255, 30));
                        break;
                    case "annulé":
                        c.setBackground(new Color(255, 0, 0, 30));
                        break;
                    case "en attente":
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
        labelBienvenue = new JLabel();
        labelDeco = new JLabel();
        labelChangeMdp = new JLabel();
        labelRole = new JLabel();
        labelListe = new JLabel();
        labelPrecision = new JLabel();
        jSeparator1 = new JSeparator();
        tableauProjets = new JTable();
        jScrollPane1 = new JScrollPane();
 
        setBackground(new Color(255, 255, 255));

        labelImageBas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bandeau_bas.jpg")));

        labelBienvenue.setFont(new java.awt.Font("Tahoma", 0, 18));
        labelBienvenue.setHorizontalAlignment(SwingConstants.CENTER);
        labelBienvenue.setText("Bienvenue Nom de la personne");

        labelRole.setHorizontalAlignment(SwingConstants.CENTER);
        labelRole.setText("Vous êtes connecté en tant que Technicien");

        labelDeco.setFont(new java.awt.Font("Tahoma", 1, 11));
        labelDeco.setForeground(new java.awt.Color(204, 204, 204));
        labelDeco.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelDeco.setText("Déconnexion ");
        labelDeco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelChangeMdp.setFont(new java.awt.Font("Tahoma", 1, 11)); 
        labelChangeMdp.setForeground(new java.awt.Color(204, 204, 204));
        labelChangeMdp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelChangeMdp.setText("Changer le mot de passe");
        labelChangeMdp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelListe.setFont(new Font("Tahoma", 0, 14));
        labelListe.setHorizontalAlignment(SwingConstants.LEFT);
        labelListe.setText("Liste des projets");

        labelPrecision.setHorizontalAlignment(SwingConstants.CENTER);
        labelPrecision.setText("Cliquez sur le nom du projet pour voir les activités qu'il contient");

        tableauProjets.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Nom du projet", "Durée estimée", "Durée finale", "Statut"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        tableauProjets.setSelectionBackground(Color.WHITE);
        tableauProjets.setSelectionForeground(new Color(0, 0, 0));
        tableauProjets.getColumnModel().getColumn(0).setPreferredWidth(140);
        tableauProjets.getColumnModel().getColumn(1).setPreferredWidth(80);
        tableauProjets.getColumnModel().getColumn(3).setPreferredWidth(50);
        tableauProjets.setAutoCreateRowSorter(true);
        tableauProjets.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(tableauProjets);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(labelImageBas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelBienvenue, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelRole, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelChangeMdp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelDeco)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) 
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelListe))
                .addGap(23, 23, 23))
            .addComponent(labelPrecision, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(labelListe)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelPrecision)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(labelImageBas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
    }                    

    private JLabel labelImageBas;     
    private JLabel labelBienvenue;
    private JLabel labelChangeMdp;
    private JLabel labelDeco;
    private JLabel labelRole;     
    private JLabel labelListe;
    private JLabel labelPrecision;
    private JSeparator jSeparator1;
    private JTable tableauProjets;
    private JScrollPane jScrollPane1;
}
