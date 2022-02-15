package fr.univ_poitiers.tpinfo.cinematech;

import java.util.ArrayList;

public class People {
    private String name;
    private ArrayList<Movies> listmoviesToSee;
    private ArrayList<Movies> listmoviesSeen;
    private ArrayList<Movies> listmoviesToCome;
    private ArrayList<Dvd> listDvdToBuy;
    private ArrayList<Dvd> listDvd;
    private ArrayList<Dvd> listDvdToCome;
    private static final int DEFAULT_START_NB = 0;
    private int nbMovies;
    private int nbDvd;
    private int nbMoviesToSee;
    private int nbDvdToGet;

    People(String n){
        name = n;
        listmoviesToSee = new ArrayList<>();
        listmoviesSeen = new ArrayList<>();
        listmoviesToCome = new ArrayList<>();
        listDvdToBuy = new ArrayList<>();
        listDvd = new ArrayList<>();
        listDvdToCome = new ArrayList<>();
        nbMovies = DEFAULT_START_NB;
        nbDvd = DEFAULT_START_NB;
        nbMoviesToSee = DEFAULT_START_NB;
        nbDvdToGet = DEFAULT_START_NB;

    }

    public ArrayList<Movies> getMovieToSee(){return listmoviesToSee;}
    public void addMovieToSee(Movies m){
        nbMovies++;
        listmoviesToSee.add(m);
    }
    public void removeMovieToSee(Movies m){
        nbMovies--;
        listmoviesToSee.remove(m);
    }

    public ArrayList<Movies> getMovieSeen(){return listmoviesSeen;}
    public void addMovieSeen(Movies m){
        nbMoviesToSee++;
        listmoviesSeen.add(m);
    }
    public void removeMovieSeen(Movies m){
        nbMoviesToSee--;
        listmoviesSeen.remove(m);
    }

    public ArrayList<Movies> getMovieToCome(){return listmoviesToCome;}
    public void addMovieToCome(Movies m){
        nbMoviesToSee++;
        listmoviesToCome.add(m);}
    public void removeMovieToCome(Movies m){
        nbMoviesToSee--;
        listmoviesToCome.remove(m);
    }

    public ArrayList<Dvd> getDVDToBuy(){return listDvdToBuy;}
    public void addDVDToBuy(Dvd d){
        nbDvdToGet++;
        listDvdToBuy.add(d);
    }
    public void removeDvdToBuy(Dvd d){
        nbDvdToGet--;
        listDvdToBuy.remove(d);
    }

    public ArrayList<Dvd> getListDvd(){return listDvd;}
    public void addDvd(Dvd d){
        nbDvd++;
        listDvd.add(d);
    }
    public void removeDvd(Dvd d){
        nbDvd--;
        listDvd.remove(d);
    }

    public ArrayList<Dvd> getDvdToCome(){return listDvdToCome;}
    public void addDvdToCome(Dvd d){
        nbDvdToGet++;
        listDvdToCome.add(d);
    }
    public void removeDvdToCome(Dvd d){
        nbDvdToGet--;
        listDvdToCome.remove(d);
    }

}
