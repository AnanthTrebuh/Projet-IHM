package fr.univ_poitiers.tpinfo.cinematech;

//ResearMovie used in RsearchMovieAdapter to fill the listView
public class ResearchMovie {
    private String name;
    private String id;
    private String urlImage;

    ResearchMovie(String name, String id){
        this.urlImage = null;
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public void setUrl(String url){
        this.urlImage = url;
    }

    public String getUrl(){ return this.urlImage; }

    public String getName(){ return this.name; }
}
