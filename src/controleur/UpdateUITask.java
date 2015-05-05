package controleur;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

/**
 * Created by Maxime on 05/05/2015.
 */
public class UpdateUITask extends TimerTask {

    private JLabel timeLabel;

    int nbSecondes = 0;

    public UpdateUITask(JLabel labelChrono){
        timeLabel = labelChrono;
    }

    @Override
    public void run() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                timeLabel.setText(String.valueOf(nbSecondes++));
            }
        });
    }
}