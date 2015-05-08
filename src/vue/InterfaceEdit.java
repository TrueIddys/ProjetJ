package vue;

import controleur.CreateParser;
import controleur.ParserInverse;
import controleur.Utilities;
import modele.Chunk;
import modele.Corpus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;
import java.util.List;



public class InterfaceEdit extends JFrame implements ActionListener, MouseListener{
    private JPanel interfaceEdit;
    private JPanel panelChunks;
    private JPanel panelLiaison;
    private JPanel panelMot;
    private JButton boutonFinir;
    private JButton boutonRetour;
    private JList liste;
    private JList listeLiaison;

    private JLabel mot;
    private List < String > listeMot;

    private String motCourant;
    private JPopupMenu jpop;
    private JPopupMenu jpopfounction;
    private int compteurChunk;
    private int compteurMot;
    private Point locate;
    private Component composant;
    private int x;
    private int y;
    private String nomfichier;

    private DefaultListModel modeleDeListe;
    private DefaultListModel modeleDeListeLiaison;

    private int compteurLiaison = 1;

    /*Initialisation de la fenêtre */
    public InterfaceEdit(String fichierXML){
        super();
        Utilities.initFenetre(this, interfaceEdit);
        boutonFinir.setVisible(false);
        initListe();
        initPanelMot(fichierXML);
        ajouterListener();
        nomfichier = fichierXML;
    }

    /*
    * Initialisation des listes pour les chunk et les liaison ,
    * cette methode nous permet de définir la zone possible pour l'utilisation de la souris
    * mais également des mettre des scrollsbar à nos 2 listes , celles si seront liées afin de pouvoir défiler ensemble
    */

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

        liste.addMouseListener(this);
        panelChunks.add(scrollPane);
        panelLiaison.add(scrollPaneLiaison);

    }

    /*
    * Cette methode permet d'activer le contenu interactif ,
    * on y retrouve les intéractions de boutons ainsi que les intéractions avec
    * les flêches qui activent différentes méthodes
    */

    private void ajouterListener() {
        Toolkit.getDefaultToolkit().addAWTEventListener
                (
                        new AWTEventListener() {
                            public void eventDispatched(AWTEvent event) {
                                KeyEvent ke = (KeyEvent) event;
                                if (ke.getID() == KeyEvent.KEY_RELEASED) {
                                } else if (ke.getID() == KeyEvent.KEY_PRESSED) {

                                    if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                                        deplacerMot();

                                    } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                        if (compteurMot == 0) {
                                            deplacerMot();
                                        } else {
                                            deplacerChunk();
                                        }


                                    }

                                }
                            }
                        }, AWTEvent.KEY_EVENT_MASK
                );

        boutonRetour.addActionListener(this);
        boutonFinir.addActionListener(this);
    }


    /**
     * Fonction qui change de mot après avoir appuyé sur une des fleches après avoir ajouté un mots ou un chunk
     */
    private void changerMot() {

        if (listeMot.size()-1 > compteurMot)
        {
            compteurMot++;
            motCourant = listeMot.get(compteurMot);
            definirLabelMot(motCourant);
        }
        else{
            compteurMot++;


            definirLabelMot("");
            return;

        }



    }

    /*
     * Cette fonction permet de placer un mot sur le chunk actuel (la dernière ligne de la liste des chunks)
     */
    private void deplacerMot() {
        if(compteurMot<listeMot.size()) {
            String motCourant = panelMot.getComponent(0).getName();
            modeleDeListe.setElementAt(((String) modeleDeListe.lastElement()).concat(" " + motCourant), modeleDeListe.getSize() - 1);
            changerMot();
        }

    }

    /*
    * Cette fonction permet de placer un mot sur un chunk supplémentaire
    */
    private void deplacerChunk() {


        if(compteurMot<listeMot.size()){
            compteurChunk++;
            String motCourant = panelMot.getComponent(0).getName();
            modeleDeListe.addElement(motCourant);
            changerMot();
        }
    }

    /*
    * Initialisation du panel de mot , on y retrouve la methode appelant le parseur qui retourne une liste de mot complête
    * du fichier Xml choisit à la vue précédente
    *
    * voir dans Edit "InterfaceEdit interfaceEdit  = new InterfaceEdit(e.getActionCommand());"
    *
    * e.getActionCommand() retourne le nom du bouton sur lequel on a cliqué
    */
    private void initPanelMot(String fichierXML){
        createparser(fichierXML);
        JTextArea texteExp = new JTextArea("Fleche gauche : rajouter au chunk \n Fleche du bas : ajouter a un nouveau chunk \n" +
                "Une fois tout les mots en place , faites un clic droit sur un chunk pour ses liaisons et fonctions  \n " +
                "Une fois touts les chunks parametres un bouton de fin va apparaitre");
        texteExp.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        texteExp.setBackground(new Color(73, 200, 232));
        texteExp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMot.add(texteExp, BorderLayout.CENTER);


    }

    /*
     * Methode qui appele le parseur
     */

    private void createparser(String fichierXML){
        CreateParser createparseur = new CreateParser(fichierXML);

        listeMot = createparseur.afficheAll();


            motCourant = listeMot.get(compteurChunk);
            ajouterPremierMot(motCourant);
    }

    /*
     * Methode qui ajoute le premier mot , cette methode est utilisé pour initialiser la vue
     */
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

    /*verification si le bouton fin peu apparaitre*/
    public void finVisible(){

        int fin = 0;
        for(int i=0;i<=compteurChunk;i++ ){
            if(modeleDeListeLiaison.getElementAt(i).equals("")||modeleDeListeLiaison.getElementAt(i).equals(" ")){
                fin = 1;
            }
        }
        if(fin==0)
            boutonFinir.setVisible(true);
    }


    /* Définition des panels (puisqu'il sont creer par nous même): voir sur "Edit.form" l'option "custom create"*/

    private void createUIComponents() {
        // TODO: place custom component creation code here
        /*mise en place de l'interface pour les remplirs de manière dynamique*/
        panelLiaison = new JPanel();
        panelLiaison.setLayout(new BorderLayout());
        panelLiaison.setBackground(new Color(93, 135, 133));

        panelChunks = new JPanel();
        panelChunks.setLayout(new BorderLayout());
        panelChunks.setBackground(new Color(60, 63, 65));

        panelMot = new JPanel();
        panelMot.setLayout(new BorderLayout());
        panelMot.setBackground(new Color(113, 125, 126));


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        /*
        * Le bouton fin doit creer une liste de chunk (corpus) afin de pouvoir utiliser un parseur inversé
        * pour creer un fichier Xml utilisable dans le mode de jeu
        */

        if (e.getActionCommand() == "Fin")
        {

            /*On modifie les liste suivants les liaisons afin d'obtenir la valeur attendue pour chunk.type*/

            for(int i=0;i<=compteurChunk;i++ ){
                String test = modeleDeListeLiaison.getElementAt(i).toString();

                if(modeleDeListeLiaison.getElementAt(i).toString().matches(".*(0|1|2|3|4|5|6|7|8|9)( s| i| v| _s| _v).*")){

                    String element1 = modeleDeListeLiaison.getElementAt(i).toString();
                    modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.getElementAt(i).toString().replaceAll("0|1|2|3|4|5|6|7|8|9| |", ""), i);
                    String liaison = element1.replaceAll("i|v|s|_| |", "");
                    for(int j=0;j<=compteurChunk;j++ ){
                        if(modeleDeListeLiaison.getElementAt(j).toString().equals(liaison)){
                            modeleDeListeLiaison.setElementAt("c"+i,j);
                        }
                    }
                }

                else if(test.equals("  _s")||test.equals("  _v")||test.equals("  s")||test.equals("  v")||test.equals("  i")){

                    modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.getElementAt(i).toString().replaceAll(" ", ""),i);
                }
            }





            Corpus corpus = new Corpus();

            for(int i=0;i<=compteurChunk;i++ ){
                /*Parcourt des chunk et on instancie la classe chunk à chaque parcourt*/
                Chunk chunck = new Chunk();
                chunck.setchunk("c" +i);


                String element = modeleDeListeLiaison.getElementAt(i).toString();
                chunck.settype(element);



                /*récupération de tout les mots de chaque chunk dans la liste de chunk
                afin de les ajouter au chunk sous forme de liste */

                String elementmot = modeleDeListe.getElementAt(i).toString();

                String mot = "" ;
                for(int j =0;j<elementmot.length();j++){

                    /*Si c'est un espace (fin d'un mot) on ajoute le mot à la liste*/

                    if (elementmot.charAt(j) == ' '){
                        if(mot!="") {
                            chunck.addMot(mot);
                            mot = "" ;
                        }
                    }

                    /*Si c'est une ponctuation on l'ajoute directement*/

                    else if (elementmot.substring(j,j+1).matches("^a-Z")) {

                        mot = elementmot.substring(j,j+1);
                        chunck.addMot(mot);
                        mot = "" ;

                    }

                    /*Si c'est le dernier charactère d'un chunk on ajoute le mot construit*/

                    else if (j==elementmot.length()-1){
                        mot = mot+elementmot.substring(j,j+1);
                        chunck.addMot(mot);
                        mot = "" ;
                    }

                    /*Sinon on continue à concaténé les charactères */

                    else{
                        mot = mot+elementmot.substring(j,j+1) ;
                    }

                }



                /*Maintenant on insère chaque chunk au corpus instancié au début de la méthode*/

                corpus.addChunk(chunck);
                }


            /*ici on a  le parser inverse  , on va utiliser ses methodes pour creer le fichier*/

            ParserInverse parserinv = new ParserInverse(corpus,nomfichier);




            this.dispose();
            Edit edit = new Edit();
        }
        /*
         * Bouton Retour
         */
        else if (e.getActionCommand() == "Retour")
        {
            this.dispose();

        }
        /*
        * definition des action sur le menu contextuel
        */
        else if (e.getActionCommand() == "Supprimer")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(" ", index);
            jpop.setVisible(false);
            boutonFinir.setVisible(false);

        }
        else if (e.getActionCommand() == "Nouvelle liaison")
        {
            int index = liste.locationToIndex(locate);
            compteurLiaison++;
            modeleDeListeLiaison.setElementAt(compteurLiaison, index);
            jpop.setVisible(false);
            finVisible();

        }
        /*
        * Menu dans le menu contextuel , c'est le menu d'édition de fonction du chunk,
        * Ce menu affecte la ligne oû l'utilisateur à fait un clic droit pour premier menu contextuel.
        *
        *
        */
        else if (e.getActionCommand() == "Editer la fonction")
        {
            /*On instancie le menu*/
            jpopfounction = new JPopupMenu();

            /*création des bouton du menu de founction*/
            JMenuItem menuVerbe = new JMenuItem( "Verbe" );
            JMenuItem menuSujet = new JMenuItem( "Sujet" );
            JMenuItem menuSuiviVerbe = new JMenuItem( "Element qui suit un verbe" );
            JMenuItem menuSuiviSujet = new JMenuItem( "Element qui suit un sujet" );
            JMenuItem menuIntruducteur = new JMenuItem( "Introducteur");
            JMenuItem annulPopupFunction = new JMenuItem( "Annuler l'edition" );


            jpopfounction.add(menuVerbe);
            jpopfounction.add(menuSujet);
            jpopfounction.add(menuSuiviVerbe);
            jpopfounction.add(menuSuiviSujet);
            jpopfounction.add(menuIntruducteur);
            jpopfounction.add(annulPopupFunction);

            /*affichage du menu*/
            jpopfounction.show(composant,x,y);


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
            finVisible();
        }
        else if (e.getActionCommand() == "Sujet")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" s", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
            finVisible();
        }
        else if (e.getActionCommand() == "Element qui suit un verbe")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" _v", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
            finVisible();
        }
        else if (e.getActionCommand() == "Element qui suit un sujet")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" _s", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
            finVisible();
        }
        else if (e.getActionCommand() == "Introducteur")
        {
            int index = liste.locationToIndex(locate);
            modeleDeListeLiaison.setElementAt(modeleDeListeLiaison.elementAt(index)+" i", index);
            jpop.setVisible(false);
            jpopfounction.setVisible(false);
            finVisible();
        }
        else if (e.getActionCommand() == "Annuler l'edition")
        {

            jpopfounction.setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*
        * Ajout de liaison sur le clic gauche
        */
        if (e.getButton() == MouseEvent.BUTTON1){
            if (e.getClickCount() == 1) {
                if(compteurMot >= listeMot.size()) {
                    int index = liste.locationToIndex(e.getPoint());
                    modeleDeListeLiaison.setElementAt(compteurLiaison, index);

                    finVisible();
                }
            }
        }


        /*
        * Création du menu contextuel
        * C'est le même sysême de menu que pour editer les fonctions
        *
        */
        else if (e.getButton() == MouseEvent.BUTTON3){
            if(compteurMot >= listeMot.size()) {

                jpop = new JPopupMenu();
                locate = e.getPoint();
                composant = e.getComponent();
                x = e.getX();
                y = e.getY();

            /*création des bouton du menu*/
                JMenuItem menuSupprimLiaison = new JMenuItem("Supprimer");
                JMenuItem menuNouvelleLiaison = new JMenuItem("Nouvelle liaison");
                JMenuItem menuEditerFonction = new JMenuItem("Editer la fonction");
                JMenuItem annulPopup = new JMenuItem("Annuler");


                jpop.add(menuSupprimLiaison);
                jpop.add(menuNouvelleLiaison);
                jpop.add(menuEditerFonction);
                jpop.add(annulPopup);


            /*affichage du menu*/
                jpop.show(composant, x, y);


                enableEvents(AWTEvent.MOUSE_EVENT_MASK);

                menuSupprimLiaison.addActionListener(this);
                menuNouvelleLiaison.addActionListener(this);
                menuEditerFonction.addActionListener(this);
                annulPopup.addActionListener(this);
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

