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

    public Chunk(){
        listeMots = new ArrayList<String>();
        type = "";/*type ou liaison du chunk*/
        id = "c00";

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

    public List<String> getListeMots() {
        return listeMots;
    }

    public String gettype() {
        return type;
    }

    public String getid() {
        return id;
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
