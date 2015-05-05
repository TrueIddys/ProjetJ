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
 */
public class CreateParser {
    static org.jdom2.Document document;
    static Element racine;

    public CreateParser(String fichierXml){

        String debutChemin = "";
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
        List listP = racine.getChildren("p"); //on récupère toutes les phrases du corpus
        Iterator i = listP.iterator();

        while (i.hasNext()) //on parcours tout les paragraphe
        {
            Element courantPhrase = (Element) i.next();
            List listeMot = courantPhrase.getChildren(); //on récupère tout les mots et ponctuation dans une liste
            Iterator iMot = listeMot.iterator();
            while (iMot.hasNext())
            {
                String courantMot = (String) iMot.next();
                listeMots.add(courantMot);
            }
        }

        return listeMots; //on retourne la liste de mots.
    }
}
