package vue;

import controleur.CreateParser;
import controleur.Utilities;
import modele.Chunk;
import modele.Corpus;
import sun.font.TrueTypeFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.DimensionUIResource;
import java.util.List;
/**
 * Created by SIN on 27/04/2015.
 *
 * travail � faire : initialiser les liste , cr�er une liste de mots ,
 * creer un parseur invers� (class createparser)
 *
 */

public class InterfaceEdit extends JFrame implements ActionListener, MouseListener{
    private JPanel interfaceEdit;
    private JPanel panelChunks;
    private JPanel panelLiaison;
    private JPanel panelMot;
    private JButton boutonFinir;
    private JButton boutonRetour;
    private JList liste;
    private JList listeLiaison;
    private JDialog fenetreDeFin;
    private JLabel mot;
    private List < String > listeMot;
    private Chunk chunkCourant;
    private String motCourant;
    private JPopupMenu jpop;
    private JPopupMenu jpopfounction;
    private int compteurChunk;
    private int compteurMot;
    private Point locate;

    private List<Chunk> listeChunks;
    private DefaultListModel modeleDeListe;
    private DefaultListModel modeleDeListeLiaison;

    private int compteurLiaison = 1;

    /*Initialisation de la fen�tre */
    public InterfaceEdit(String fichierXML){
        super();
        Utilities.initFenetre(this, interfaceEdit);
        boutonFinir.setVisible(false);
        initListe();
        initPanelMot(fichierXML);
        ajouterListener();

    }

    private void initListe()
    {
        modeleDeListe = new DefaultListModel();
        modeleDeListe.ensureCapacity(100);
        modeleDeListe.addElement("");
        modeleDeListeLiaison = new DefaultListModel();
        modeleDeListeLiaison.ensureCapacity(100);
        for (int i = 0; i < 1000; i++)
            modeleDeListeLiaison.addElement(" ");

        liste = new JList();
        liste.setModel(modeleDeListe);
        listeLiaison = new JList();
        listeLiaison.setModel(modeleDeListeLiaison);

        JScrollPane scrollPane = new JScrollPane(liste);
        JScrollPane scrollPaneLiaison = new JScrollPane(listeLiaison);
        scrollPane.setBackground(new Color(73, 200, 232));
        scrollPane.getVerticalScrollBar().setModel(scrollPaneLiaison.getVerticalScrollBar().getModel());

        listeLiaison.addMouseListener(this);
        panelChunks.add(scrollPane);
        panelLiaison.add(scrollPaneLiaison);

    }

    private void ajouterListener()
    {
        Toolkit.getDefaultToolkit().addAWTEventListener
                (
                        new AWTEventListener() {
                            public void eventDispatched(AWTEvent event) {
                                KeyEvent ke = (KeyEvent) event;
                                if (ke.getID() == KeyEvent.KEY_RELEASED) {
                                } else if (ke.getID() == KeyEvent.KEY_PRESSED) {

                                    if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                                        if (compteurMot < listeMot.size()) {
                                            deplacerMot();
                                        }
                                    } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                        if (compteurMot < listeMot.size()) {
                                            deplacerChunk();
                                        }

                                    } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                        if (compteurMot > 0) {
                                            annuler();
                                        }

                                    }

                                }
                            }
                        }, AWTEvent.KEY_EVENT_MASK
                );

        boutonRetour.addActionListener(this);
    }

    private void annuler() {

        /*compteurMot--;
        motCourant = listeMot.get(compteurMot);
        definirLabelMot(motCourant);
        modeleDeListe.setElementAt(((String) modeleDeListe.lastElement()).subSequence(0, (int) modeleDeListe.lastElement()).length()- motCourant.length()- 1, modeleDeListe.getSize() - 1);
        if(((String) modeleDeListe.lastElement()).isEmpty()){
            compteurChunk--;
        }*/
    }

    private void deplacerMot() {
        String motCourant = panelMot.getComponent(0).getName();
        modeleDeListe.setElementAt(((String) modeleDeListe.lastElement()).concat(" " + motCourant), modeleDeListe.getSize() - 1);
        changerMot();

    }

    /**
     * Fonction qui change de mot apr�s avoir appuy� sur une des fleches
     */
    private void changerMot() {

        if (listeMot.size()-1 > compteurMot)
        {
            compteurMot++;
            motCourant = listeMot.get(compteurMot);
            definirLabelMot(motCourant);
        }
        else{
            boutonFinir.setVisible(true);
            definirLabelMot("");
            return;

        }


    }

    private void deplacerChunk() {
        compteurChunk++;
        String motCourant = panelMot.getComponent(0).getName();
        modeleDeListe.addElement(motCourant);
        changerMot();
    }

    private void initPanelMot(String fichierXML){
        createparser(fichierXML);
        JTextArea texteExp = new JTextArea("Fleche gauche : rajouter au chunk \n Fleche du bas : ajouter � un nouveau chunk "); /* Fleche de haut : annuler la derni�re op�ration*/
        texteExp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        texteExp.setBackground(new Color(73, 200, 232));
        texteExp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMot.add(texteExp, BorderLayout.CENTER);


    }

    private void createparser(String fichierXML){
        CreateParser createparseur = new CreateParser(fichierXML);

        listeMot = createparseur.afficheAll();


            motCourant = listeMot.get(compteurChunk);
            ajouterPremierMot(motCourant);
    }

    public void ajouterPremierMot(String texte){
        mot = new JLabel();
        definirLabelMot(texte);
        mot.setHorizontalAlignment(JLabel.CENTER);
        mot.setVerticalAlignment(JLabel.CENTER);
        mot.setAlignmentX(Component.CENTER_ALIGNMENT);
        mot.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        panelMot.add(mot, BorderLayout.SOUTH);
    }

    public void definirLabelMot(String nom){
        mot.setName(nom);
        mot.setText(nom);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        /*mise en place de l'interface pour les remplirs de mani�re dynamique*/
        panelLiaison = new JPanel();
        panelLiaison.setLayout(new BorderLayout());
        panelLiaison.setBackground(new Color(93, 135, 133));

        panelChunks = new JPanel();
        panelChunks.setLayout(new BorderLayout());
        panelChunks.setBackground(new Color(60, 63, 65));

        panelMot = new JPanel();
        panelMot.setLayout(new BorderLayout());
        panelMot.setBackground(new Color(60, 63, 65));


    }


    /**
     * M�thode qui initialise la fen�tre de fin
     */
    private void ecranDeFin() {
        fenetreDeFin = new JDialog(this);
        JPanel panelDeFin = new JPanel(new BorderLayout());
        JTextArea texteDeFin = new JTextArea();
        texteDeFin.setText("Vous avez termin� l'edition du corpus , celui peut etre retrouver dans les fichiers du jeu.");
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
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Fin")
        {

            for(int i=0;i<compteurChunk;i++ ){
                Chunk chunck = new Chunk();
                chunck.settype("");
                chunck.setchunk("c"+ i);
                listeChunks.add(chunck);
            }
            /*ici on a le parser inverse : on va utiliser une methode qui creer le fichier*/

            this.dispose();
            Edit edit = new Edit();
        }
        else if (e.getActionCommand() == "Retour")
        {
            /*Edit edit = new Edit();*/
            this.dispose();

        }
        /*
        * definition des action sur le menu contextuel
        */
        else if (e.getActionCommand() == "Suprimmer la liaison")
        {
            int index = liste.locationToIndex(jpop.getLocationOnScreen());
            modeleDeListeLiaison.setElementAt("", index);
            jpop.setVisible(false);

        }
        else if (e.getActionCommand() == "Nouvelle liaison")
        {
            int index = liste.locationToIndex(jpop.getLocationOnScreen());
            compteurLiaison++;
            modeleDeListeLiaison.setElementAt(compteurLiaison, index);
            jpop.setVisible(false);

        }
        else if (e.getActionCommand() == "Editer la fonction")
        {
            jpopfounction = new JPopupMenu();
            /*cr�ation des bouton du menu de founction*/
            JMenuItem menuVerbe = new JMenuItem( "Verbe" );
            JMenuItem menuSujet = new JMenuItem( "Sujet" );
            JMenuItem menuSuiviVerbe = new JMenuItem( "Element qui suit un verbe" );
            JMenuItem menuSuiviSujet = new JMenuItem( "Element qui suit un sujet" );
            JMenuItem menuIntruducteur = new JMenuItem( "Intruducteur");
            JMenuItem annulPopupFunction = new JMenuItem( "Annuler l'edition" );


            jpopfounction.add(menuVerbe);
            jpopfounction.add(menuSujet);
            jpopfounction.add(menuSuiviVerbe);
            jpopfounction.add(menuSuiviSujet);
            jpopfounction.add(menuIntruducteur);
            jpopfounction.add(annulPopupFunction);


            jpopfounction.setLocation(jpop.getLocation());
            jpopfounction.setEnabled(true);
            jpopfounction.setVisible(true);

            enableEvents(AWTEvent.MOUSE_EVENT_MASK);

            menuVerbe.addActionListener(this);
            menuSujet.addActionListener(this);
            menuSuiviVerbe.addActionListener( this);
            menuSuiviSujet.addActionListener( this);
            menuIntruducteur.addActionListener( this);
            annulPopupFunction.addActionListener(this);


        }
        else if (e.getActionCommand() == "Annuler")
        {

            jpop.setVisible(false);
        }
        /*
        * definition des action sur le menu de fonction
        */
        else if (e.getActionCommand() == "Verbe")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" v", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
        }
        else if (e.getActionCommand() == "Sujet")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" s", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
        }
        else if (e.getActionCommand() == "Element qui suit un verbe")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" _v", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
        }
        else if (e.getActionCommand() == "Element qui suit un sujet")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" _s", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
        }
        else if (e.getActionCommand() == "Introducteur")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" i", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
        }
        else if (e.getActionCommand() == "Annuler l'edition")
        {

            jpopfounction.setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1){
            if (e.getClickCount() == 1) {
                int index = liste.locationToIndex(e.getPoint());
                modeleDeListeLiaison.setElementAt(compteurLiaison, index);
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3){
            jpop = new JPopupMenu();
            locate=e.getPoint();
            /*cr�ation des bouton du menu*/
            JMenuItem menuSupprimLiaison = new JMenuItem( "Suprimmer la liaison" );
            JMenuItem menuNouvelleLiaison = new JMenuItem( "Nouvelle liaison" );
            JMenuItem menuEditerFonction = new JMenuItem( "Editer la fonction" );
            JMenuItem annulPopup = new JMenuItem( "Annuler" );


            jpop.add(menuSupprimLiaison);
            jpop.add(menuNouvelleLiaison);
            jpop.add(menuEditerFonction);
            jpop.add(annulPopup);

            jpop.setLocation(e.getXOnScreen(),e.getYOnScreen());
            jpop.setEnabled(true);
            jpop.setVisible(true);

            enableEvents(AWTEvent.MOUSE_EVENT_MASK);

            menuSupprimLiaison.addActionListener( this);
            menuNouvelleLiaison.addActionListener( this);
            menuEditerFonction.addActionListener( this);
            annulPopup.addActionListener( this);

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

