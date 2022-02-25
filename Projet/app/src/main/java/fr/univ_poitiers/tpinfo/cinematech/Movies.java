package fr.univ_poitiers.tpinfo.cinematech;

public class Movies {
    private String id;
    private String title;
    private String realisateur;
    private String date;
    private String[] acteurs;
    private int duree; //en minutes

    Movies(String id, String title, String real){
        this.id = id;
        this.title = title;
        this.realisateur = real;
    }
    public String getId(){return id;}
    public String getTitle(){return title;}
    public String getRealisateur(){return realisateur;}

    @Override
    public String toString() {
        return this.title + " de " + getRealisateur();
    }

}
