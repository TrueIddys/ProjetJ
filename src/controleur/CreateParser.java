package controleur;

import modele.Chunk;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SIN on 27/04/2015.
 *
 * Ce parser � pour fonction de r�cup�rer un fichier xml utilis� sp�cialement pour le mode d'�dition
 * pour r�cup�rer tout les mots et ponctuation dans une liste.
 *
 */
public class CreateParser {
    static org.jdom2.Document document;
    static Element racine;

    public CreateParser(String fichierXml){

        /*Le contructeur permet de d�finir le chemin du fichier*/

        String debutChemin = "src/xml/";
        String cheminComplet = debutChemin.concat(fichierXml);
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            document = sxb.build(new File(cheminComplet));
        }
        catch (Exception e)
        {
            System.out.println("erreur :");
            System.out.println(e.getMessage());
        }
        racine = document.getRootElement();

    }

    public List<String> afficheAll() {

        List<String> listeMots = new ArrayList<String>();
        List listP = racine.getChildren("p"); //on r�cup�re toutes les phrases du corpus
        Iterator i = listP.iterator();

        while (i.hasNext()) //on parcours tout les paragraphe
        {
            Element courantPhrase = (Element) i.next();
            List listeMot = courantPhrase.getChildren(); //on r�cup�re tout les mots et ponctuation dans une liste
            Iterator iMot = listeMot.iterator();
            while (iMot.hasNext())
            {
                Element courantMot = (Element) iMot.next();
                listeMots.add(courantMot.getText());
            }
        }
        return listeMots; //on retourne la liste de mots.
    }
}
