package vue;

import controleur.Parser;
import controleur.Utilities;
import modele.Chunk;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by Maxime on 03/03/2015.
 */
public class InterfaceJeu extends JFrame implements ActionListener {
    private JPanel InterfaceJeu;
    private JPanel panelMots;
    private JPanel panelChunks;
    private JPanel panelMenu;

    private JList liste;
    private JLabel mot;
    private JDialog fenetreDeFin;
    private JButton boutonRetour;

    private Chunk chunkCourant;
    private String motCourant;
    private int compteurChunk;
    private int compteurMot;
    private List<Chunk> listeChunks;
    private DefaultListModel modeleDeListe;


    /**
     * Constructeur de la fenêtre
     * @param fichierXML Fichier XML qui servira de support au niveau
     */
    public InterfaceJeu(String fichierXML){
        Utilities.initFenetre(this, InterfaceJeu);
        initListe();
        initPanelMot(fichierXML);
        initPanelMenu();

        ajouterListener();
    }

    /**
     * Méthode qui initialise la liste
     */
    private void initListe()
    {
        modeleDeListe = new DefaultListModel();
        modeleDeListe.ensureCapacity(100);
        modeleDeListe.addElement("");
        liste = new JList();
        liste.setModel(modeleDeListe);
        JScrollPane scrollPane = new JScrollPane(liste);
        scrollPane.setBackground(new Color(73, 200, 232));


        panelChunks.add(scrollPane);
    }

    /**
     * Méthode qui initialise le panel de gauches
     */
    private void initPanelMenu()
    {
        boutonRetour = new JButton("Retour");
        boutonRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMenu.add(boutonRetour, BorderLayout.CENTER);
    }

    /**
     * Méthode qui ajoute les Listeners aux différents panels,
     * boutons etc ...
     */
    private void ajouterListener()
    {
        Toolkit.getDefaultToolkit().addAWTEventListener
                (
                        new AWTEventListener() {
                            public void eventDispatched(AWTEvent event) {
                                KeyEvent ke = (KeyEvent) event;

                                if (ke.getID() == KeyEvent.KEY_RELEASED) {
                                } else if (ke.getID() == KeyEvent.KEY_PRESSED) {
                                    if (ke.getKeyCode() == KeyEvent.VK_LEFT)
                                    {
                                        deplacerMot();
                                    }
                                    else if (ke.getKeyCode() == KeyEvent.VK_DOWN)
                                    {
                                        deplacerChunk();
                                    }
                                } else if (ke.getID() == KeyEvent.KEY_TYPED) {
                                    System.out.println("typed");
                                }
                            }


                        }, AWTEvent.KEY_EVENT_MASK);

        boutonRetour.addActionListener(this);
    }

    /**
     * Fonction qui initialise le panel de mot
     * @param fichierXML
     */
    private void initPanelMot(String fichierXML){
        parser(fichierXML);
        JTextArea texteExp = new JTextArea("Fleche gauche : rajouter au chunk \n Fleche du bas : ajouter à un nouveau chunk");
        texteExp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        texteExp.setBackground(new Color(73, 200, 232));
        texteExp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMots.add(texteExp, BorderLayout.CENTER);

    }


    /**
     * Fonction qui permet de rajouter un nouveau chunk avec le mot courant
     */
    private void deplacerChunk() {
        String motCourant = panelMots.getComponent(0).getName();
        modeleDeListe.addElement(motCourant);
        changerMot();
    }


    /**
     * Fonction qui permet de faire rentrer un mot dans le chunk courant
     */
    private void deplacerMot() {
        String motCourant = panelMots.getComponent(0).getName();
        modeleDeListe.setElementAt(((String) modeleDeListe.lastElement()).concat(" " + motCourant), modeleDeListe.getSize()-1);
        changerMot();

    }

    /**
     * Fonction qui change de mot après avoir appuyé sur une des fleches
     */
    private void changerMot() {
        compteurMot++;

        if (chunkCourant.getListeMots().size() <= compteurMot)
        {
            compteurChunk++;

            if (listeChunks.size() <= compteurChunk)
            {
                compteurChunk = 0;
                compteurMot = 0;
                ecranDeFin();

                return;

            }
            
            chunkCourant = listeChunks.get(compteurChunk);
            compteurMot = 0;
        }
        
        motCourant = chunkCourant.getListeMots().get(compteurMot);
        definirLabelMot(motCourant);
    }

    /**
     * Méthode qui initialise la fenêtre de fin
     */
    private void ecranDeFin() {
        fenetreDeFin = new JDialog(this);
        JPanel panelDeFin = new JPanel(new BorderLayout());
        JTextArea texteDeFin = new JTextArea();
        texteDeFin.setText("Bravo ! Vous avez fini ce niveau. Cliquez sur OK pour retournez à l'écran de choix de niveau.");
        JButton boutonOk = new JButton("OK");
        boutonOk.setVerticalAlignment(SwingConstants.CENTER);
        boutonOk.setHorizontalAlignment(SwingConstants.CENTER);
        boutonOk.addActionListener(this);
        panelDeFin.add(texteDeFin, BorderLayout.NORTH);
        panelDeFin.add(boutonOk, BorderLayout.SOUTH);
        fenetreDeFin.add(panelDeFin);
        fenetreDeFin.pack();
        fenetreDeFin.setLocationRelativeTo(null);
        fenetreDeFin.setVisible(true);

    }


    /**
     * Méthode qui permet d'appeler la fonction de parsage et de récupérer le corpus
     * @param fichierXML Fichier XML parsé
     */
    public void parser(String fichierXML){
        Parser parseur = new Parser(fichierXML);

        listeChunks = parseur.afficheAll();

        chunkCourant = listeChunks.get(compteurChunk);
        motCourant = chunkCourant.getListeMots().get(compteurMot);
        ajouterPremierMot(motCourant);
    }


    /**
     * Fonction qui ajoute le premier mot à placer
     * @param texte Premier mot parsé
     */
    public void ajouterPremierMot(String texte){
        mot = new JLabel();
        definirLabelMot(texte);
        mot.setHorizontalAlignment(JLabel.CENTER);
        mot.setVerticalAlignment(JLabel.CENTER);
        mot.setAlignmentX(Component.CENTER_ALIGNMENT);
        mot.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        panelMots.add(mot, BorderLayout.SOUTH);
    }

    /**
     * Méthode qui définit les informations du mot à rajouter
     * @param nom Le mot à rajouter
     */
    public void definirLabelMot(String nom){
        mot.setName(nom);
        mot.setText(nom);

    }


    /**
     * Méthode qui permet de mettre en place les panels de façon dynamique de
     * manière à pouvoir les remplir
     */
    private void createUIComponents() {
        panelMots = new JPanel();
        panelMots.setLayout(new BorderLayout());
        panelMots.setBackground(new Color(73,200,232));


        panelChunks = new JPanel();
        panelChunks.setLayout(new BorderLayout());
        panelChunks.setBackground(new Color(73, 200, 232));

        panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(73, 200, 232));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "OK")
        {
            fenetreDeFin.dispose();
            this.dispose();

            ChoixCorpus choixCorpus = new ChoixCorpus();
        }
        else if (e.getActionCommand() == "Retour")
        {
            this.dispose();
            ChoixCorpus choixCorpus = new ChoixCorpus();
        }
    }
}
