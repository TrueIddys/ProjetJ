package vue;

import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Maxime on 02/03/2015.
 */
public class Menu extends JFrame implements ActionListener{
    private JPanel Menu;
    private JButton jouerButton;
    private JButton editerButton;
    private JButton optionButton;
    private JLabel label;


    public Menu() {

//        this.setContentPane(Menu);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setPreferredSize(new DimensionUIResource(600, 400));
//        this.pack();
//        this.setLocationRelativeTo(null);
//        this.setVisible(true);
        controleur.Utilities.initFenetre(this, Menu);
        jouerButton.addActionListener(this);
        editerButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Jouer")){
            Selection selection = new Selection();
            this.dispose();
        }
        else if (e.getActionCommand().equals("Editer")) {
            Edit edit = new Edit();
            this.dispose();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
