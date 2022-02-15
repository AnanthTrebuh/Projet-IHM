package fr.univ_poitiers.tpinfo.cinematech;

public class Movies {
    private String title;
    private String realisateur;
    private String date;
    private String[] acteurs;
    private int duree; //en minutes

    Movies(String title, String real, String date, String[] acteurs, int duree){
        this.title = title;
        this.realisateur = real;
        this.date = date;
        this.acteurs = acteurs;
        this.duree = duree;
    }

    public String getTitle(){return title;}
    public String getRealisateur(){return realisateur;}
    public String getDate(){ return date; }
    public String[] getActeurs(){ return acteurs;}
    public int getDuree(){return duree;}

}
