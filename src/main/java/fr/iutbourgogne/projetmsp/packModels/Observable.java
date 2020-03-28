
package fr.iutbourgogne.projetmsp.packModels;

import fr.iutbourgogne.projetmsp.packVue.Observateur;


/**
 *
 * @author Jorick
 */
public interface Observable {
    public void addObservateur(Observateur obs);
    public void notifyObservateurs(String code);
}
