
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

/**
 *
 * @author Jorick
 */
public class VueAccueilTechnicien extends JPanel {

    private User personne;

    public VueAccueilTechnicien(User p) {
        this.personne = p;
        
        this.setPreferredSize(new Dimension(500, 450));
        initComponents();
        
        labelBienvenue.setText("Bienvenue " + personne.getPrenom() + " " + personne.getNom());
        
        labelDeco.addMouseListener(new Ecouteur_AccesReponse());
        labelChangeMdp.addMouseListener(new Ecouteur_AccesReponse());
        tableauProjets.addMouseListener(new Ecouteur_AccesReponse());
        tableauProjets.addMouseMotionListener(new Ecouteur_AccesReponse());
        
        Remplir_tableau();
    }

    private void Remplir_tableau() {
        
        int j = 0, k = 0, l = 0, m = 0;
        
        tableauProjets.getTableHeader().setReorderingAllowed(false);
        tableauProjets.getTableHeader().setResizingAllowed(false);
        
        // récupère les projets du technicien via la requête SQL
        ProjetDAO.findProjets(personne);
      
        // remplissage des colonnes
        for (Projet i : personne.getListeProjets()) {
            tableauProjets.setValueAt(i.getNom(), j, 0);
            tableauProjets.setValueAt(i.getDureeEstimee(), j, 1);
            tableauProjets.setValueAt(i.getDureeFinale(), j, 2);
            tableauProjets.setValueAt(i.getStatut(), j, 3);
            j += 1;
        } 
    }
    
    public class Ecouteur_AccesReponse implements ActionListener, MouseListener, MouseMotionListener {
        
        @Override public void actionPerformed(ActionEvent e) {}
       
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
    
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseDragged(MouseEvent e) {}
        
        @Override 
        public void mouseExited(MouseEvent e) {
            tableauProjets.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
        
        @Override
        public void mouseMoved(MouseEvent e) {
            if ((tableauProjets.columnAtPoint(e.getPoint())) == 0 && (tableauProjets.getValueAt(tableauProjets.rowAtPoint(e.getPoint()), 0) != null)) {
                tableauProjets.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else {
                tableauProjets.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    @SuppressWarnings("unchecked")                      
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
        
        labelBienvenue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelBienvenue.setHorizontalAlignment(SwingConstants.CENTER);
        labelBienvenue.setText("Bienvenue Nom de la personne");
        
        labelRole.setHorizontalAlignment(SwingConstants.CENTER);
        labelRole.setText("Vous êtes connecté en tant que Technicien");

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
        
        labelListe.setFont(new Font("Tahoma", 0, 14)); 
        labelListe.setHorizontalAlignment(SwingConstants.LEFT);
        labelListe.setText("Liste des projets");
        
        labelPrecision.setHorizontalAlignment(SwingConstants.CENTER);
        labelPrecision.setText("Cliquez sur le nom du projet pour voir les activités qu'il contient");

                tableauProjets.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nom du projet", "Durée estimée", "Durée finale", "Statut"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        tableauProjets.setSelectionBackground(new Color(255, 204, 0));
        tableauProjets.setSelectionForeground(new Color(0, 0, 0));
        tableauProjets.getColumnModel().getColumn(0).setPreferredWidth(151);
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
