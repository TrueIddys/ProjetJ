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
import java.util.Random;

/**
 * Created by Maxime on 02/03/2015.
 */
public class ChoixCorpus extends JFrame implements ActionListener{
    private JPanel choixCorpus;
    private JButton aleatoireButton;
    private JButton retourMenu;
    private JPanel panelNiveau;
    private List<File> listeFichier;

    private List<JButton> listeButtonFichier;

    private String modeDeJeu;

    public ChoixCorpus(String mode) {
        super();
        modeDeJeu = mode;
        listeFichier = rechercheFichier();
        listeButtonFichier = new ArrayList<JButton>();
        creerBoutonFichier();
        Utilities.initFenetre(this, choixCorpus);

        ajouterActionListener();
    }

    private void ajouterActionListener() {
        retourMenu.addActionListener(this);
        aleatoireButton.addActionListener(this);
    }

    private void creerBoutonFichier() {
        for (File file : listeFichier){
            JButton newButton = new JButton();
            newButton.setName(file.getName());
            newButton.setText(file.getName());
            newButton.addActionListener(this);
            listeButtonFichier.add(newButton);
            panelNiveau.add(newButton);
        }

    }

    private List<File> rechercheFichier() {
        List<File> listeFichiers = new ArrayList<File>();
        File repertoire = new File("src\\xml");
        File[] listeFiles = repertoire.listFiles();

        for (File file : listeFiles){
            if (file.isFile()){
                listeFichiers.add(file);
            }
        }
        return listeFichiers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Menu")){
            this.dispose();
            Menu menu = new Menu();
        }
        else if (e.getActionCommand().equals("Aleatoire")){
            this.dispose();
            InterfaceJeu ij = new InterfaceJeu(listeButtonFichier.get(choisirFichierAleatoirement()).getName(), modeDeJeu);
        }
        else {
            InterfaceJeu ij = new InterfaceJeu(e.getActionCommand(), modeDeJeu);
            this.dispose();
        }
    }

    private int choisirFichierAleatoirement() {
        Random rand = new Random();

        int nbAleatoire = rand.nextInt(listeButtonFichier.size()-1 - 0 +1) + 0;
        return nbAleatoire;
    }

    private void createUIComponents() {
        panelNiveau = new JPanel();
        panelNiveau.setSize(new Dimension(400, 200));
        panelNiveau.setLayout(new GridLayout(10, 1));
        panelNiveau.setBackground(new Color(73, 200, 232));

   }
}
