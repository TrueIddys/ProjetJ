package controleur;

import modele.Chunk;
import modele.Corpus;


import java.io.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;

import javax.xml.transform.stream.StreamResult;

/**
 * Created by SIN on 06/05/2015.
 */
public class ParserInverse
{
    private Corpus corpus;
    private String nomFichier;

    /*
    *
    * parser inverse , qui va recréé un fichier xml lisible en mode de jeux
    * à partir d'une liste de chunck ou corpus
    *
    *
    */


    public ParserInverse(Corpus corp, String nom)
    {

        /*Dans ce constructeur on créé un nouveau nom pour le fichier , on enleve _pour_edition*/
        String temp = nom.replaceAll("_pour_edition", "");
        corpus = corp;
        nomFichier = "Edit" + temp;
        System.out.println(nomFichier);

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try
        {
            /*Création du document*/
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();

            /*Création de la racine du document*/
            final Element racine = document.createElement("dr");
            document.appendChild(racine);

            /*Création de toute les balise et du contenu*/
            /******************************************************************************************/

            Element fonctions = document.createElement("fonctions");
            racine.appendChild(fonctions);

            Element s = document.createElement("f");
            fonctions.appendChild(s);
            s.setAttribute("id", "s");
            Element v = document.createElement("f");
            fonctions.appendChild(v);
            v.setAttribute("id", "v");
            Element i = document.createElement("f");
            fonctions.appendChild(i);
            i.setAttribute("id", "i");
            Element ss = document.createElement("f");
            fonctions.appendChild(ss);
            ss.setAttribute("id", "_s");
            Element sv = document.createElement("f");
            fonctions.appendChild(sv);
            sv.setAttribute("id", "_v");

            Element p = document.createElement("p");
            racine.appendChild(p);

            for (int x = 0; x < corpus.getsize(); x++) {
                Element c = document.createElement("c");
                p.appendChild(c);
                c.setAttribute("id", corpus.getChunk(x).getid());
                c.setAttribute("c", corpus.getChunk(x).gettype());
                Chunk chunk = new Chunk();
                chunk = corpus.getChunk(x);
                for (int z = 0; z < chunk.getListeMots().size(); z++) {

                    if (chunk.getListeMots().get(z) == "(") {
                        Element pg = document.createElement("pg");
                        c.appendChild(pg);
                        pg.appendChild(document.createTextNode(chunk.getListeMots().get(z)));
                    }
                    else if (chunk.getListeMots().get(z).matches(".*([a-zA-Z]).*")) {
                        Element m = document.createElement("m");
                        c.appendChild(m);
                        m.appendChild(document.createTextNode(chunk.getListeMots().get(z)));
                    }
                    else {
                        Element pd = document.createElement("pd");
                        c.appendChild(pd);
                        pd.appendChild(document.createTextNode(chunk.getListeMots().get(z)));
                    }
                }
            }


            /******************************************************************************************/

            /*Création du document .xml*/


            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dr.dtd");

            final DOMSource source = new DOMSource(document);
            final StreamResult sortie = new StreamResult(new File("src/xml/" + nomFichier));
            transformer.transform(source, sortie);

        }
        catch( final ParserConfigurationException e){
            e.printStackTrace();
        }
        catch(TransformerConfigurationException e){
            e.printStackTrace();
        }
        catch(TransformerException e){
            e.printStackTrace();

        }

    }

}
