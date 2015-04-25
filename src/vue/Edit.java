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

    public Edit(){
        super();
        Utilities.initFenetre(this, edit);

        listeFichier = rechercheFichier();
        listeButtonFichier = new ArrayList<JButton>();
        creerBoutonFichier();


        ajouterActionListener();
    }

    private void ajouterActionListener() {
        retourMenu.addActionListener(this);
    }

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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Menu")) {
            this.dispose();
            Menu menu = new Menu();
        }
        else {
            InterfaceJeu ij = new InterfaceJeu(e.getActionCommand());
            this.dispose();
        }
    }

    private void createUIComponents() {
        choixEdit = new JPanel();
        choixEdit.setLayout(new GridLayout(10,2));
        choixEdit.setBackground(new Color(60, 63, 65));
        edit = new JPanel();
        edit.setLayout(new GridLayout(10,2));
        edit.setBackground(new Color(62, 65, 97));
    }
}
