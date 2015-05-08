package controleur;

import modele.Corpus;


import java.io.*;
import org.jdom2.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.stream.StreamResult;

/**
 * Created by SIN on 06/05/2015.
 */
public class ParserInverse {
    private Corpus corpus ;
    private String nomFichier;

    /*
    *
    * parser inverse , qui va recréé un fichier xml lisible en mode de jeux
    * à partir d'une liste de chunck ou corpus
    *
    *
    */

    /*Dans ce constructeur on créé un nouveau nom pour le fichier , on enleve _pour_edition*/
    public ParserInverse(Corpus corp,String nom){

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();
            final Element racine = document.createElement("dr");
            document.appendChild(racine);
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }


        String temp = nom.replaceAll("_pour_edition","");
        corpus = corp;
        nomFichier = "Edit"+temp;
        System.out.println(nomFichier);









    }

    /*static void enregistre(String fichier)
    {
        try
        {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document, new FileOutputStream(fichier));
        }
        catch (java.io.IOException e){}
    }



    public void creerfichier() {

        enregistre(nomFichier);


    }*/

}
