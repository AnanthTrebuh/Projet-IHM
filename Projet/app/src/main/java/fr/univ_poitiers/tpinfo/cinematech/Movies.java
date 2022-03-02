package fr.univ_poitiers.tpinfo.cinematech;

//Simple class movie with, id, title, realisateur, and an url based on baseUrl + backdropSize + imageName.jpg
//With all the getters and setters necessary
public class Movies {
    private String id;
    private String title;
    private String realisateur;
    private String backdropSize, baseUrl, urlImage;

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
    public void setUrl(String url) { this.urlImage = this.baseUrl + this.backdropSize + url; }
    public String getUrl() { return this.urlImage; }
    public void setbackdropSize(String backdropSize) { this.backdropSize = backdropSize; }
    public String getbackdropSize() { return this.backdropSize; }
    public void setbaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    @Override
    public String toString() {
        return this.title + " de " + getRealisateur();
    }

}
