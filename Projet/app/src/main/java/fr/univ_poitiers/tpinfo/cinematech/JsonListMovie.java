package fr.univ_poitiers.tpinfo.cinematech;

public class JsonListMovie {
    String url, name;
    int id;
    
    JsonListMovie(String name, int id, String url){
        this.url = url;
        this.id = id;
        this.name = name;
    }
}
