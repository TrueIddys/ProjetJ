package controleur;

import modele.Chunk;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Maxime on 03/03/2015.
 */

public class Parser {

    static org.jdom2.Document document;
    static Element racine;

    public Parser(String fichierXml){

        String débutChemin = "src/xml/";
        String cheminComplet = débutChemin.concat(fichierXml);

        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File(cheminComplet));
        } catch (Exception e)
        {
            System.out.println("erreur :");
            System.out.println(e.getMessage());
        }

        racine = document.getRootElement();

    }

    public List<Chunk> afficheAll() {

        List<Chunk> listeChunks = new ArrayList<Chunk>();
        List listP = racine.getChildren("p"); //on récupère toutes les phrases du corpus

        Iterator i = listP.iterator();
        while (i.hasNext()){ //on parcours tout les éléments
            Element courantPhrase = (Element)i.next();

            List listChunk = courantPhrase.getChildren("c"); //on récupère tout les chunks dans une liste

            Iterator iChunk = listChunk.iterator();

            while (iChunk.hasNext()) //on parcours tout les chunks
            {
                Element courantChunk = (Element)iChunk.next();
                Chunk chunkLu = new Chunk(); //on récupère le chunk lu
                List listMot = courantChunk.getChildren();
                Iterator iMot = listMot.iterator();
                chunkLu.setchunk(courantChunk.getAttributeValue("id"));
                chunkLu.settype(courantChunk.getAttributeValue("c"));

                while(iMot.hasNext()){ //on parcours les mots dans le chunk courant
                    Element courantMot = (Element)iMot.next();
                    String mot = courantMot.getText();
                    if (courantMot.getName() == "pd") //si le mot est un symbole de ponctuation
                    {
                        chunkLu.concatenerDernierMot(courantMot.getText()); //on le concatene au mot précédent
                    }
                    else {
                        chunkLu.addMot(courantMot.getText()); //sinon on rajoute le mot au chunk
                    }
                }
                listeChunks.add(chunkLu); //on ajoute le chunk à la liste de chunk
            }
        }
        return listeChunks; //on retourne la liste de chunk
    }

}
