package fr.univ_poitiers.tpinfo.cinematech;

public class Dvd extends Movies{
    boolean bluray;
    Dvd(String title, String real, String date, String[] acteurs, int duree, boolean bluray){
        super(title, real, date, acteurs, duree);
        this.bluray = bluray;
    }


}
