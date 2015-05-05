package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 04/03/2015.
 */

/*
* Redefinition du chunk : ajout du type et génération de l'id
*/

public class Chunk {
    private List<String> listeMots;
    private String type;
    private String id;
    private String lien;

    public Chunk(){
        listeMots = new ArrayList<String>();
        type = "?";
        id = "c00";
        lien = ""; /*il s'agit de l'id du chunck à qui il se relie*/
    }

    public void addMot(String mot)
    {
        listeMots.add(mot);
    }

    public void settype(String typ){
        this.type = typ;
    }

    public void setchunk(String ident){
        this.id = ident;
    }

    public void setliaison(String id){
        this.lien = id;
    }

    public List<String> getListeMots() {
        return listeMots;
    }

    public String getDernierMot()
    {
        return listeMots.get(listeMots.size()-1);
    }

    public void concatenerDernierMot(String ponct)
    {
        String motFinal = getDernierMot().concat(ponct);
        listeMots.set(listeMots.size()-1, motFinal);
    }

}
