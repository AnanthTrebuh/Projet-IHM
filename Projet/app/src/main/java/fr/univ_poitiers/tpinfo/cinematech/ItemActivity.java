package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ItemActivity  extends AppCompatActivity {
    String base_url, backdrop_size, file_path, prec;
    String id;
    TextView synopsis;
    TextView realisation;
    TextView time;
    TextView actors;
    TextView dateExit;
    TextView title;
    TextView scenario;
    ImageView imageMovie;
    Button addToWatch;
    Button addDVD;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.id = getIntent().getStringExtra("movie");
        String list = getIntent().getStringExtra("list");
        prec = getIntent().getStringExtra("precActivity");
        setContentView(R.layout.item_activity);
        queue = Volley.newRequestQueue(this);

        imageMovie = findViewById(R.id.imageMovie);
        synopsis = findViewById(R.id.synopsisMovie);
        realisation = findViewById(R.id.realizationMovie);
        time = findViewById(R.id.timeMovie);
        actors = findViewById(R.id.actorsMovie);
        dateExit = findViewById(R.id.dateExitMovie);
        title = findViewById(R.id.titleMovie);
        scenario = findViewById(R.id.scenarioMovie);
        addToWatch = findViewById(R.id.buttonAddListMovie);
        addDVD = findViewById(R.id.addDvdbutton);
        switch(list){
            case Utils.MOVIE_SEEN:
            case Utils.DVD_BUY: addToWatch.setText(this.getString(R.string.remove_list));addDVD.setVisibility(View.INVISIBLE); break;
            case Utils.MOVIE : addDVD.setVisibility(View.INVISIBLE); addToWatch.setText(this.getString(R.string.add_to_seen));break;
            case Utils.DVD: addDVD.setVisibility(View.INVISIBLE);addToWatch.setText(this.getString(R.string.add_to_getlist));break;
            case Utils.ACT_SEARCH : addToWatch.setText(this.getString(R.string.ajouter_la_liste_voir));break;
            default: break;
        }


        addToWatch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        switch(list){
                            case  Utils.MOVIE_SEEN: action_remove_movie();
                                Toast.makeText(ItemActivity.this, getApplicationContext().getString(R.string.movie_remove), Toast.LENGTH_SHORT).show();
                                break;
                            case Utils.DVD_BUY : action_remove_dvd();
                                Toast.makeText(ItemActivity.this, getApplicationContext().getString(R.string.movie_remove), Toast.LENGTH_SHORT).show();
                                break;
                            case Utils.MOVIE: action_add_movie();
                                Toast.makeText(ItemActivity.this, getApplicationContext().getString(R.string.movie_add), Toast.LENGTH_SHORT).show();
                                break;
                            case Utils.DVD : action_add_dvd();
                                Toast.makeText(ItemActivity.this, getApplicationContext().getString(R.string.movie_add), Toast.LENGTH_SHORT).show();
                                break;
                            case Utils.ACT_SEARCH: action_add_movie_to_seen();
                                            Toast.makeText(ItemActivity.this, getApplicationContext().getString(R.string.movie_add), Toast.LENGTH_SHORT).show();
                                            break;
                            default: break;
                        }
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        switch(prec){
                            case Utils.ACT_MOVIE:
                                intent = new Intent(getApplicationContext(), MoviesActivity.class);
                                startActivity(intent);
                                break;
                            case Utils.ACT_DVD:
                                intent = new Intent(getApplicationContext(), DVDActivity.class);
                                            startActivity(intent);
                                            break;
                            default: break;
                        }
                        finish();
                    }
                }
        );
        addDVD.setOnClickListener(view -> {action_add_dvd_to_buy(); finish();});

        setUp(id);
        setUpDirReaChara(id);
        try {
            setUpUrl(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //Fill data for overView, title, releaseDate
    private void setUp(String idMovie){
        String url = MoviesActivity.URL_ID_MOVIE + idMovie + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                JSONObject object = new JSONObject(string);
                String runtime = object.getString("runtime").toString();
                if(runtime == null){
                    time.setText(R.string.Unknown);
                }
                else{
                    time.setText(object.getString("runtime").toString() + "min");
                }

                String overview = object.getString("overview").toString();
                if(overview == null){
                    synopsis.setText(R.string.Unknown);
                }
                else{
                    synopsis.setText(object.getString("overview").toString());
                }
                title.setText(object.getString("title").toString());
                dateExit.setText(object.getString("release_date").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);
    }

    //Fill data for director and characters
    private void setUpDirReaChara(String idMovie){
        String url = "https://api.themoviedb.org/3/movie/" + idMovie + "/credits" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                JSONObject object = new JSONObject(string);
                JSONArray jsonArray = object.getJSONArray("crew"); //get data from all crew in this movie
                //Loop on each crew to find the job: director, writter
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject cpy = (JSONObject) jsonArray.get(i);
                    if(cpy.get("job").toString().toLowerCase(Locale.ROOT).equals("director")){
                        realisation.setText(cpy.get("name").toString());
                    }
                    if(cpy.get("job").toString().toLowerCase(Locale.ROOT).equals("writer")){
                        scenario.setText(cpy.get("name").toString());
                    }
                }

                jsonArray = object.getJSONArray("cast"); //get data from all crew in this movie
                //Loop on each cast to fill characters
                int i = 0;
                String actorsString= "";
                while(i < jsonArray.length() && i < 3){
                    JSONObject cpy = (JSONObject) jsonArray.get(i);
                    if(i == 0){
                        actorsString += cpy.get("name").toString();
                    }
                    else{
                        actorsString += ", " + cpy.get("name").toString();
                    }
                    i++;
                }
                actors.setText(actorsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);
    }

    //get an image from the API: get the base_url, backdrop_size and file_path
    private void setUpUrl(String idMovie) throws JSONException {
        //base_url and file_size are in /configuration
        String url = "https://api.themoviedb.org/3/configuration" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONObject object = (JSONObject) jsonObject.get("images");
                JSONArray jsonArray = object.getJSONArray("backdrop_sizes");
                base_url = object.getString("base_url").toString();
                backdrop_size = jsonArray.get(0).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);

        //to get file_path of image
        String url2 = "https://api.themoviedb.org/3/movie/" + idMovie + "/images" + MoviesActivity.KEY;
        StringRequest request2 = new StringRequest(Request.Method.GET, url2, string -> {
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray = jsonObject.getJSONArray("backdrops");
                JSONObject object = (JSONObject) jsonArray.get(0);
                file_path = object.getString("file_path");
                LoadImage loadImage = new LoadImage(imageMovie);
                loadImage.execute(base_url+backdrop_size+file_path);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request2);
    }

    public void action_add_movie(){
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieListInit = new HashSet<>(sharedPreferences.getStringSet(name+Utils.MOVIE, new HashSet<String>()));
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+Utils.MOVIE_SEEN, new HashSet<String>()));
        movieList.add(this.id);
        movieListInit.remove(this.id);
        int timeToAdd = sharedPreferences.getInt(name+"_time", 0);
        timeToAdd += Integer.parseInt(time.getText().toString().replace("min",""));
        e.putInt(name+"_time", timeToAdd);
        e.putStringSet(name+Utils.MOVIE, movieListInit);
        e.putStringSet(name+Utils.MOVIE_SEEN, movieList);
        e.apply();
    }
    public void action_add_movie_to_seen(){
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+Utils.MOVIE, new HashSet<String>()));
        movieList.add(this.id);
        e.putStringSet(name+Utils.MOVIE, movieList);
        e.apply();
    }
    public void action_add_dvd_to_buy(){
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+Utils.DVD, new HashSet<String>()));
        movieList.add(this.id);
        e.putStringSet(name+Utils.DVD, movieList);
        e.apply();
    }
    public void action_add_dvd(){
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieListInit = new HashSet<>(sharedPreferences.getStringSet(name+Utils.DVD, new HashSet<String>()));
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+Utils.DVD_BUY, new HashSet<String>()));
        movieList.add(this.id);
        movieListInit.remove(this.id);
        e.putStringSet(name+Utils.DVD, movieListInit);
        e.putStringSet(name+Utils.DVD_BUY, movieList);
        e.apply();
    }
    private void action_remove_movie() {
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+Utils.MOVIE, new HashSet<String>()));
        Set<String> movieListInit = new HashSet<>(sharedPreferences.getStringSet(name+Utils.MOVIE_SEEN, new HashSet<String>()));
        movieList.add(this.id);
        movieListInit.remove(this.id);
        int timeToAdd = sharedPreferences.getInt(name+"_time", 0);
        timeToAdd -= Integer.parseInt(time.getText().toString().replace("min",""));
        e.putInt(name+"_time", timeToAdd);
        e.putStringSet(name+Utils.MOVIE, movieList);
        e.putStringSet(name+Utils.MOVIE_SEEN, movieListInit);
        e.apply();
    }

    private void action_remove_dvd(){
        SharedPreferences sharedPreferences = getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+Utils.DVD, new HashSet<String>()));
        Set<String> movieListInit = new HashSet<>(sharedPreferences.getStringSet(name+Utils.DVD_BUY, new HashSet<String>()));
        movieList.add(this.id);
        movieListInit.remove(this.id);
        e.putStringSet(name+Utils.DVD, movieList);
        e.putStringSet(name+Utils.DVD_BUY, movieListInit);
        e.apply();
    }


    @Override
    @MainThread
    public void onBackPressed(){
        if(prec != null){
            switch (prec){
                case Utils.DVD_BUY:
                case Utils.DVD:   back_dvd();break;
                case Utils.MOVIE_SEEN:
                case Utils.MOVIE: back_movie();break;
                default: finish();break;
            }
        }else{
            finish();
        }
    }
    private void back_dvd(){
        Intent intent = new Intent(this, DVDActivity.class);
        startActivity(intent);
        finish();
    }
    private void back_movie(){
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
        finish();
    }

}
