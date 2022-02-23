package fr.univ_poitiers.tpinfo.cinematech;

public class JsonListMovie {
    private String base_url, backdrop_size, full_path, name;
    private String id;
    
    JsonListMovie(String name, String id, String base_url, String backdrop_size, String full_path){
        this.base_url = base_url;
        this.backdrop_size = backdrop_size;
        this.full_path = full_path;
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public void changeBaseUrl(String base_url){
        this.base_url = base_url;
    }

    public void changeBackdropSize(String backdrop_size){
        this.backdrop_size = backdrop_size;
    }

    public void changeFullPath(String full_path){
        this.full_path = full_path;
    }

    public String getUrl(){
        return base_url + backdrop_size + full_path;
    }
}
