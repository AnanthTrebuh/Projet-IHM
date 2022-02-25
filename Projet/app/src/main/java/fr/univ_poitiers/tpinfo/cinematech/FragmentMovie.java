package fr.univ_poitiers.tpinfo.cinematech;

import static fr.univ_poitiers.tpinfo.cinematech.Utils.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class FragmentMovie extends Fragment {
    private ListView listview;
    private RequestQueue queue;
    private ArrayList<Movies> movies;
    Set<String> movieTestList;

    //static String title, director;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(getContext());
        movies = new ArrayList<Movies>();



        CustomListAdapter Adapter = new CustomListAdapter(listview.getContext(), new ArrayList<Movies>());
        listview.setAdapter(Adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movies current = (Movies) listview.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("movie", current.getId());
                intent.putExtra("list", "seen");
                startActivity(intent);
            }
        });
        return view;
    }

    public void initList(){
        movies.clear();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+"_movie_seen", new HashSet<String>()));
        if(!movieList.equals(movieTestList)){
            movieTestList = new HashSet<>(movieList);
            for(String id : movieList ){
                try {
                    Thread.sleep(100);
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
                for (int i = 0; i < movies.size() && !done; i++){
                    if(movies.get(i).getTitle().equals("")){
                        if( name != "" || name != null){
                            movies.get(i).setTitle(name);
                        }
                        else{
                            movies.get(i).setTitle("error");
                        }
                        done = true;
                    }
                }
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
                for (int i = 0; i < movies.size() && !done; i++){
                    if(movies.get(i).getRealisateur().equals("")){
                        if( director != "" || director != null){
                            movies.get(i).setRealisateur(director);
                        }
                        else{
                            movies.get(i).setRealisateur("error");
                        }
                        done = true;
                    }
                }
                actualiseMovie();
            } catch (JSONException e) {
                e.printStackTrace();
                boolean done = false;
                Log.d(MoviesActivity.TAG, e.toString());
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);
    }
    public void addMovie(String id){
        movies.add(new Movies(id, "",""));
        getTitle(id, queue);
        getDirector(id, queue);
    }
    public void actualiseMovie(){
        CustomListAdapter Adapter = new CustomListAdapter(listview.getContext(), movies);
        listview.setAdapter(Adapter);
    }
}