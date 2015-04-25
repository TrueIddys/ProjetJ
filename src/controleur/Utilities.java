package controleur;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;


/**
 * Created by Maxime on 24/03/2015.
 */
public class Utilities {

    /**
     * Méthode qui initialise la fenêtre.
     */
    public static void initFenetre(JFrame fenetre, JPanel contentPane){
        fenetre.setContentPane(contentPane);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setPreferredSize(new DimensionUIResource(800, 600));
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }

}