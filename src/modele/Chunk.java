package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 04/03/2015.
 */
public class Chunk {

    public List<String> getListeMots() {
        return listeMots;
    }

    private List<String> listeMots;


    public Chunk(){
        listeMots = new ArrayList<String>();
    }

    public void addMot(String mot)
    {
        listeMots.add(mot);
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
