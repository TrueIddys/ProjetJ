package vue;

import controleur.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Maxime on 25/04/2015.
 */
public class Selection extends JFrame implements ActionListener {
    private JPanel panelPrincipal;
    private JButton boutonDecouper;
    private JButton boutonRelier;
    private JButton boutonD_R;


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBoutonDecouper() {
        return boutonDecouper;
    }

    public void setBoutonDecouper(JButton boutonDecouper) {
        this.boutonDecouper = boutonDecouper;
    }

    public JButton getBoutonRelier() {
        return boutonRelier;
    }

    public void setBoutonRelier(JButton boutonRelier) {
        this.boutonRelier = boutonRelier;
    }

    public JButton getBoutonD_R() {
        return boutonD_R;
    }

    public void setBoutonD_R(JButton boutonD_R) {
        this.boutonD_R = boutonD_R;
    }


    public Selection(){
        Utilities.initFenetre(this, panelPrincipal);
        boutonD_R.addActionListener(this);
        boutonRelier.addActionListener(this);
        boutonDecouper.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Découper") || e.getActionCommand().equals("Découper et relier") || e.getActionCommand().equals("Relier")){
            ChoixCorpus choixCorpus = new ChoixCorpus();
            this.dispose();
        }
    }
}
