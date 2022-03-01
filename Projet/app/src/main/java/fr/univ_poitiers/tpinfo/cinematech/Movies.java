package fr.univ_poitiers.tpinfo.cinematech;

public class Movies {
    private String id;
    private String title;
    private String realisateur;
    private String date;
    private String[] acteurs;
    private String urlImage;
    private int duree; //en minutes

    Movies(String id, String title, String real){
        this.id = id;
        this.title = title;
        this.realisateur = real;
    }
    public String getId(){return id;}
    public String getTitle(){return title;}
    public void setTitle(String tit){this.title = tit;}
    public String getRealisateur(){return realisateur;}
    public void setRealisateur(String real){this.realisateur = real;}
    public void setUrl(String url) { this.urlImage = url; }

    @Override
    public String toString() {
        return this.title + " de " + getRealisateur();
    }

}
