package vue;

import controleur.Parser;
import controleur.Utilities;
import modele.Chunk;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by Maxime on 03/03/2015.
 */
public class InterfaceJeu extends JFrame implements ActionListener, MouseListener {
    private JPanel InterfaceJeu;
    private JPanel panelMots;
    private JPanel panelChunks;
    private JPanel panelMenu;
    private JPanel panelLiaison;

    private JList liste;
    private JList listeLiaison;
    private JLabel mot;
    private JDialog fenetreDeFin;
    private JButton boutonRetour;

    private Chunk chunkCourant;
    private String motCourant;
    private int compteurChunk;
    private int compteurMot;
    private List<Chunk> listeChunks;
    private DefaultListModel modeleDeListe;
    private DefaultListModel modeleDeListeLiaison;

    private int compteurLiaison = 1;
    private int compteurUtilisationLiaison = 0;
    private String mode;


    /**
     * Constructeur de la fenêtre
     * @param fichierXML Fichier XML qui servira de support au niveau
     */
    public InterfaceJeu(String fichierXML, String modeDeJeu){
        Utilities.initFenetre(this, InterfaceJeu);
        mode = modeDeJeu;
        initListe();
        initPanelMot(fichierXML);
        initPanelMenu();
        ajouterListener();
        if (mode == "relier"){
            remplirListe();
        }


    }

    /**
     * Méthode qui remplit la liste avec les chunks dans le cas où le joueur joue au mode Relier
     */
    private void remplirListe(){
        String chunkComplet;
        chunkComplet = "";
        for (Chunk chunkActuel : listeChunks){
            List<String> listeMots = chunkActuel.getListeMots();
            for (String mot : listeMots){
                chunkComplet = chunkComplet + mot + " ";
                System.out.println(chunkComplet);

            }
            modeleDeListe.insertElementAt(chunkComplet, listeChunks.indexOf(chunkActuel));
            chunkComplet = "";
        }
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

        if (mode == "relier"){
            modeleDeListeLiaison = new DefaultListModel();
            modeleDeListeLiaison.ensureCapacity(100);
            for (int i = 0; i < 1000; i++)
                modeleDeListeLiaison.addElement(" ");
            listeLiaison = new JList();
            listeLiaison.setModel(modeleDeListeLiaison);
            JScrollPane scrollPaneLiaison = new JScrollPane(listeLiaison);
            scrollPane.getVerticalScrollBar().setModel(scrollPaneLiaison.getVerticalScrollBar().getModel());
            scrollPaneLiaison.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            panelLiaison.add(scrollPaneLiaison);


        }
        liste.addMouseListener(this);
        panelChunks.add(scrollPane);
    }

    /**
     * Méthode qui initialise le panel de gauches
     */
    private void initPanelMenu()
    {
        boutonRetour = new JButton("Retour");
        boutonRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panelRetour = new JPanel();
        panelRetour.add(boutonRetour, BorderLayout.CENTER);
        panelRetour.setBackground(Utilities.CouleurPanelInterface);
        panelMenu.add(panelRetour, BorderLayout.SOUTH);
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
                                }
                                else if (ke.getID() == KeyEvent.KEY_PRESSED) {
                                    if (mode == "decouper" || mode == "dr") {
                                        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                                            deplacerMot();
                                        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                            deplacerChunk();
                                        }
                                    }
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
        String instruction = "";
        if (mode == "relier"){
            instruction = "Relier les chunks en cliquant \n sur 2 chunks differents.\nLes liaisons s'affichent à\n droite sous forme de chiffres";
        }
        else if (mode == "decouper") {
            instruction = "Fleche gauche : rajouter au chunk \n" +
                    " Fleche du bas : ajouter à un nouveau chunk";

        }
        else {
            instruction = "Fleche gauche : rajouter au chunk" +
                    "\n" +
                    "Fleche du bas : ajouter à un nouveau chunk \n" +
                    "Relier les chunks en cliquant \n" +
                    " sur 2 chunks differents.\n" +
                    "Les liaisons s'affichent à\n" +
                    " droite sous forme de chiffres";
        }
        JTextArea texteExp = new JTextArea(instruction);
        texteExp.setEditable(false);
        texteExp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        texteExp.setBackground(Utilities.CouleurPanelInterface);
        JPanel panelTextExp = new JPanel();
        panelTextExp.add(texteExp, BorderLayout.CENTER);
        panelTextExp.setBackground(Utilities.CouleurPanelInterface);
        panelMots.add(panelTextExp, BorderLayout.CENTER);



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

        if (mode == "decouper" || mode == "dr") {
            chunkCourant = listeChunks.get(compteurChunk);
            motCourant = chunkCourant.getListeMots().get(compteurMot);
            ajouterPremierMot(motCourant);
        }
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
        panelMots.setBackground(Utilities.CouleurPanelInterface);


        panelChunks = new JPanel();
        panelChunks.setLayout(new BorderLayout());
        panelChunks.setBackground(Utilities.CouleurPanelInterface);

        panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        panelMenu.setBackground(Utilities.CouleurPanelInterface);

        panelLiaison = new JPanel();
        panelLiaison.setLayout(new BorderLayout());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "OK")
        {
            fenetreDeFin.dispose();
            this.dispose();

            ChoixCorpus choixCorpus = new ChoixCorpus(mode);
        }
        else if (e.getActionCommand() == "Retour")
        {
            this.dispose();
            ChoixCorpus choixCorpus = new ChoixCorpus(mode);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mode == "relier" || mode == "dr") {
            if (e.getClickCount() == 1) {
                int index = liste.locationToIndex(e.getPoint());
                modeleDeListeLiaison.setElementAt(compteurLiaison, index);
                compteurUtilisationLiaison++;
                if (compteurUtilisationLiaison == 2) {
                    compteurUtilisationLiaison = 0;
                    compteurLiaison++;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
