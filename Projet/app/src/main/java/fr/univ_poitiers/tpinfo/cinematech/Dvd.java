package fr.univ_poitiers.tpinfo.cinematech;

public class Dvd extends Movies{
    boolean bluray;
    Dvd(String id, String title, String real, boolean bluray){
        super(id, title, real);
        this.bluray = bluray;
    }

    @Override
    public String toString(){
        String bluray;
        if(this.bluray){
            bluray = "en bluray";
        }else{
            bluray = "en DVD";
        }
        return this.getTitle() + " " + bluray;
    }
    public boolean isBluray(){
        return bluray;
    }

}
