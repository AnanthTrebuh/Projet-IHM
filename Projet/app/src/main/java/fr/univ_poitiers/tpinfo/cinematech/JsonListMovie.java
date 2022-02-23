package fr.univ_poitiers.tpinfo.cinematech;

public class JsonListMovie {
    private String urlImage, name;
    private String id;
    
    JsonListMovie(String name, String id){
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

    public String getUrl(){
        return this.urlImage;
    }
}
