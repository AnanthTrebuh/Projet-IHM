package fr.univ_poitiers.tpinfo.cinematech;

public class Movies {
    private String id;
    private String title;
    private String realisateur;
    private String date;
    private String[] acteurs;
    private int duree; //en minutes

    Movies(String id, String title, String real, String date, String[] acteurs, int duree){
        this.id = id;
        this.title = title;
        this.realisateur = real;
        this.date = date;
        this.acteurs = acteurs;
        this.duree = duree;
    }
    public String getId(){return id;}
    public String getTitle(){return title;}
    public String getRealisateur(){return realisateur;}
    public String getDate(){ return date; }
    public String[] getActeurs(){ return acteurs;}
    public int getDuree(){return duree;}

    @Override
    public String toString() {
        return this.title + " de " + getRealisateur();
    }

}
