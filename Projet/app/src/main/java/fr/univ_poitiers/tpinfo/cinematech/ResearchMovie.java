package fr.univ_poitiers.tpinfo.cinematech;

import android.widget.ImageView;

public class ResearchMovie {
    private String urlImage, name;
    private String id;

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

    public String getUrl(){
        return this.urlImage;
    }

    public String getName(){ return this.name; }
}
