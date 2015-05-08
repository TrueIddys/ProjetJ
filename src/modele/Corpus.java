package modele;


import java.util.ArrayList;
import java.util.List;

public class Corpus {
    private List<Chunk> liste;


    public Corpus(){
        liste = new ArrayList();


    }

    public void addChunk(Chunk chunk)
    {
        liste.add(chunk);
    }

    public List<Chunk> getListe() {
        return liste;
    }

    public int getsize() {
        return liste.size();
    }



}
