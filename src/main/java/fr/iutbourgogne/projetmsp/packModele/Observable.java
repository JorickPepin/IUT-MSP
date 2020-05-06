
package fr.iutbourgogne.projetmsp.packModele;

import fr.iutbourgogne.projetmsp.packVue.Observateur;


/**
 * Interface permettant de notifier la classe Fenêtre afin de lancer 
 * les vues demandées
 * @author Jorick
 */
public interface Observable {
    public void addObservateur(Observateur obs);
    public void notifyObservateurs(String code);
}
