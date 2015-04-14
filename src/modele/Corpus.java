package modele;

import java.util.List;

/**
 * Created by Maxime on 02/03/2015.
 */
public class Corpus {

    /*Attributs*/

    private String xmlFile;

    public String getXmlFile() {
        return xmlFile;
    }

    private void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    /*_____________*/

    private List<Chunk> listeChunks;

    public List<Chunk> getListeChunks() {
        return listeChunks;
    }

    public void setListeChunks(List<Chunk> listeChunks) {
        this.listeChunks = listeChunks;
    }

    /*_________________________________________*/


    public Corpus(String xml){
        this.xmlFile = xml;
    }

    private String parser(String xmlFile) {

        return "lolol";
    }



}
