package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class FillListView {
    private ListView listView;
    private String spec;
    private RequestQueue queue;
    private ArrayList<Movies> movies;
    private ArrayList<Dvd> dvd;
    private Set<String> movieTestList;
    private Context context;
    private boolean b;

    public FillListView(RequestQueue queue, ListView listView, Context context, String spec){
        this.queue = queue;
        this.listView = listView;
        this.context = context;
        this.spec = spec;
        this.movies = new ArrayList<Movies>();
        this.dvd = new ArrayList<Dvd>();
        b = spec.contains("dvd");
    }

    public void fillList() {
        Log.d(MoviesActivity.TAG, "fillList: ");
        movies.clear();
        dvd.clear();
        SharedPreferences sharedPreferences = context.getSharedPreferences("CinemaTech", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile", "default");
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name + spec, new HashSet<String>()));
        if (!movieList.equals(movieTestList)) {
            movieTestList = new HashSet<>(movieList);
            for (String id : movieList) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                addMovie(id);
            }
        }
    }

    public void getTitle(String id, RequestQueue queue){
        String url = MoviesActivity.URL_ID_MOVIE + id + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, string->{
            try {
                JSONObject object = new JSONObject(string);
                String name = object.getString("title").toString();
                boolean done = false;
                if(b){
                    for (int i = 0; i < dvd.size() && !done; i++) {
                        if (dvd.get(i).getTitle().equals("")) {
                            if (name != "" || name != null) {
                                dvd.get(i).setTitle(name);
                            } else {
                                dvd.get(i).setTitle("error");
                            }
                            done = true;
                        }
                    }
                }else {
                    for (int i = 0; i < movies.size() && !done; i++) {
                        if (movies.get(i).getTitle().equals("")) {
                            if (name != "" || name != null) {
                                movies.get(i).setTitle(name);
                            } else {
                                movies.get(i).setTitle("error");
                            }
                            done = true;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);
    }

    public void getImage(String id){
        //base_url and file_size are in /configuration
        String url = "https://api.themoviedb.org/3/configuration" + MoviesActivity.KEY;

        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONObject object = (JSONObject) jsonObject.get("images");
                JSONArray jsonArray = object.getJSONArray("backdrop_sizes");
                String currentBackdropSize = jsonArray.get(0).toString();
                String currentBaseUrl = object.getString("base_url").toString();
                boolean done = false;
                if(b){
                    for (int i = 0; i < dvd.size() && !done; i++) {
                        if (dvd.get(i).getbackdropSize() == null) {
                            if (currentBackdropSize != "" || currentBackdropSize != null || currentBaseUrl != "" || currentBaseUrl != null) {
                                dvd.get(i).setbackdropSize(currentBackdropSize);
                                dvd.get(i).setbaseUrl(currentBaseUrl);
                            } else {
                                dvd.get(i).setbackdropSize("error");
                                dvd.get(i).setbaseUrl("error");
                            }
                            done = true;
                        }
                    }
                }else {
                    for (int i = 0; i < movies.size() && !done; i++) {
                        if (movies.get(i).getbackdropSize() == null) {
                            if (currentBackdropSize != "" || currentBackdropSize != null || currentBaseUrl != "" || currentBaseUrl != null) {
                                movies.get(i).setbackdropSize(currentBackdropSize);
                                movies.get(i).setbaseUrl(currentBaseUrl);
                            } else {
                                movies.get(i).setbackdropSize("error");
                                movies.get(i).setbaseUrl("error");
                            }
                            done = true;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));

        queue.add(request);
        getImageBis(id);
    }

    public void getImageBis(String id){
        //to get file_path of image
        String url = "https://api.themoviedb.org/3/movie/" + id + "/images" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray = jsonObject.getJSONArray("backdrops");
                JSONObject object = (JSONObject) jsonArray.get(0);
                String currentFilePath = object.getString("file_path");
                //fill images movies
                boolean done = false;
                if(b){
                    for (int i = 0; i < dvd.size() && !done; i++) {
                        if (dvd.get(i).getUrl() == null) {
                            if (currentFilePath != "" || currentFilePath != null) {
                                dvd.get(i).setUrl(currentFilePath);
                            } else {
                                dvd.get(i).setUrl("error");
                            }
                            done = true;
                        }
                    }
                }else {
                    for (int i = 0; i < movies.size() && !done; i++) {
                        if (movies.get(i).getUrl() == null) {
                            if (currentFilePath != "" || currentFilePath != null) {
                                movies.get(i).setUrl(currentFilePath);
                            } else {
                                movies.get(i).setUrl("error");
                            }
                            done = true;
                        }
                    }
                }
                actualiseMovie();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);
    }

    public void getDirector(String id, RequestQueue queue){
        String url = "https://api.themoviedb.org/3/movie/" + id + "/credits" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                String director ="";
                JSONObject object = new JSONObject(string);
                JSONArray jsonArray = object.getJSONArray("crew"); //get data from all crew in this movie
                //Loop on each crew to find the job: director, writter
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject cpy = (JSONObject) jsonArray.get(i);
                    if(cpy.get("job").toString().toLowerCase(Locale.ROOT).equals("director")){
                        director = (cpy.get("name").toString());
                    }
                }
                boolean done = false;
                if(b){
                    for (int i = 0; i < dvd.size() && !done; i++) {
                        if (dvd.get(i).getRealisateur().equals("")) {
                            if (director != "" || director != null) {
                                dvd.get(i).setRealisateur(director);
                            } else {
                                dvd.get(i).setRealisateur("error");
                            }
                            done = true;
                        }
                    }
                }else {
                    for (int i = 0; i < movies.size() && !done; i++) {
                        if (movies.get(i).getRealisateur().equals("")) {
                            if (director != "" || director != null) {
                                movies.get(i).setRealisateur(director);
                            } else {
                                movies.get(i).setRealisateur("error");
                            }
                            done = true;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                boolean done = false;
                Log.d(MoviesActivity.TAG, e.toString());
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);
    }

    public void addMovie(String id){
        if(b){
            Log.d(MoviesActivity.TAG, "addMovie: addMovie DVD");
            boolean bluray = false;
            if(Integer.parseInt(id)%2 == 0) bluray = true;
            dvd.add(new Dvd(id, "", "", bluray));
        }else {
            movies.add(new Movies(id, "", ""));
        }
        getTitle(id, queue);
        getDirector(id, queue);

        try {
            Thread.sleep(10);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        getImage(id);
    }
    public void actualiseMovie(){
        if(b){
            CustomListAdapterDvd Adapter = new CustomListAdapterDvd(listView.getContext(), dvd);
            listView.setAdapter(Adapter);
        }else {
            CustomListAdapter Adapter = new CustomListAdapter(listView.getContext(), movies);
            listView.setAdapter(Adapter);
        }
    }
}