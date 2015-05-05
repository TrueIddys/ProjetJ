package controleur;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;


/**
 * Created by Maxime on 24/03/2015.
 */
public class Utilities {

    public static Color CouleurPanelInterface = new Color(73, 200, 232);


    /**
     * Méthode qui initialise la fenêtre.
     */
    public static void initFenetre(JFrame fenetre, JPanel contentPane){
        fenetre.setContentPane(contentPane);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setMinimumSize(new DimensionUIResource(800, 400));
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }

}