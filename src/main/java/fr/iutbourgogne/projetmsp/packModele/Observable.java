
package fr.iutbourgogne.projetmsp.packModele;

import fr.iutbourgogne.projetmsp.packVue.Observateur;


/**
 *
 * @author Jorick
 */
public interface Observable {
    public void addObservateur(Observateur obs);
    public void notifyObservateurs(String code);
}
