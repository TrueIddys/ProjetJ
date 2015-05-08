package vue;

import controleur.Utilities;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by SIN on 25/04/2015.
 */
public class Edit extends JFrame implements ActionListener  {
    private JPanel edit;
    private JButton retourMenu;
    private JPanel choixEdit;
    private List<File> listeFichier;
    private List<JButton> listeButtonFichier;

    /*constructeur de la vue*/
    public Edit(){
        super();
        Utilities.initFenetre(this, edit);
        listeFichier = rechercheFichier();
        listeButtonFichier = new ArrayList<JButton>();
        creerBoutonFichier();
        ajouterActionListener();
    }
    /*Cette methode permet d'activer le contenu interactif (ici le bouton de retour au menu)*/
    private void ajouterActionListener() {
        retourMenu.addActionListener(this);
    }

    /*Cette m�thode permet de creer des boutons int�ractif au nom des fihiers trait�*/
    private void creerBoutonFichier() {
        for (File file : listeFichier){
            JButton newButton = new JButton();
            newButton.setName(file.getName());
            newButton.setText(file.getName());
            newButton.addActionListener(this);
            listeButtonFichier.add(newButton);
            choixEdit.add(newButton);
        }

    }

    /*Cette methode creer une liste des fichiers � traiter afin de creer un menu int�ractif,
    * puisqu'il s'agit de la partie d'�dition , tout les fichier trait� et affich� finirons par "_pour_edition.xml"
    */

    private List<File> rechercheFichier() {
        List<File> listeFichiers = new ArrayList<File>();
        File repertoire = new File("src\\xml");
        File[] listeFiles = repertoire.listFiles();

        for (File file : listeFiles){
            if (file.getName().contains("_pour_edition.xml")){
                listeFichiers.add(file);
            }
        }
        return listeFichiers;
    }

    /*r�cup�ration des signaux envoy� par les boutons*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Menu")) {
            this.dispose();
            Menu menu = new Menu();
        }
        else {

            this.dispose();
            InterfaceEdit interfaceEdit  = new InterfaceEdit(e.getActionCommand());

        }
    }
    /* D�finition des panels (puisqu'il sont creer par nous m�me): voir sur "Edi.form" l'option "custom create"*/

    private void createUIComponents() {
        choixEdit = new JPanel();
        choixEdit.setLayout(new GridLayout(10,2));
        choixEdit.setBackground(new Color(60, 63, 65));
        edit = new JPanel();
        edit.setLayout(new GridLayout(10,2));
        edit.setBackground(new Color(62, 65, 97));
    }
}
