package fr.univ_poitiers.tpinfo.cinematech;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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

public class ResearchActivity extends AppCompatActivity {

    public static String TAG = "CineTech";
    RequestQueue queue;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    SearchView searchBar;
    ListView listview;
    String precActivity;
    ArrayList<ResearchMovie> items = new ArrayList<>();
    String currentBaseUrl, currentBackdropSize, currentFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        queue = Volley.newRequestQueue(this);
        searchBar = findViewById(R.id.searchView);
        listview = findViewById(R.id.listView);

        buttonDvd = findViewById(R.id.buttonDvd);
        buttonDvd.setOnClickListener(view -> action_dvd_button());

        buttonMovie = findViewById(R.id.buttonMovie);
        buttonMovie.setOnClickListener(view -> action_movies_button());

        buttonSearch = findViewById(R.id.buttonSearch);

        buttonAccount = findViewById(R.id.buttonAccount);
        buttonAccount.setOnClickListener(view -> action_account_button());

        precActivity = getIntent().getStringExtra("precActivity");

        buttonSearch.setEnabled(false);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    action_research();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    @MainThread
    public void onBackPressed(){
        if(precActivity != null){
            switch (precActivity){
                case "movie" :  back_movie();break;
                case "account" : back_account();break;
                case "dvd" : back_dvd(); break;
                default: finish();break;
            }
        }else{
            finish();
        }
    }

    private void back_dvd() {
        Intent intent = new Intent(this, DVDActivity.class);
        startActivity(intent);
        finish();
    }

    private void back_movie(){
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
        finish();
    }
    private void back_account(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    private void action_movies_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("precActivity", "search");
        startActivity(intent);
        finish();
    }

    private void action_dvd_button() {
        Log.d(TAG, "action_search_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        intent.putExtra("precActivity", "search");
        startActivity(intent);
        finish();
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("precActivity", "search");
        startActivity(intent);
        finish();
    }

    private void action_research() throws InterruptedException {
        String s = this.searchBar.getQuery().toString();
        s = s.replaceAll("\\s+","+");
        initListMovies(s, queue);
    }

    private void fillAllIdName(String research, final VolleyCallBack callBack){
        String url = MoviesActivity.URL_TITLE_MOVIE + MoviesActivity.KEY + "&query=" + research + "&page=" + 1;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    JSONArray jsonArray = object.getJSONArray("results");
                    if(jsonArray.length() > 0){
                        for(int j = 0; j < jsonArray.length(); j++){ //search through 20 first resulsts
                            JSONObject object1 = (JSONObject) jsonArray.get(j);
                            items.add(new ResearchMovie(object1.getString("title"), object1.getString("id")));
                            fillAllUrl(items.get(j).getId(), callBack);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Error in request");
            }
        });
        queue.add(request);
    }

    private void fillAllUrl(String id, final VolleyCallBack callBack){
        //base_url and file_size are in /configuration
        String url = "https://api.themoviedb.org/3/configuration" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject object = (JSONObject) jsonObject.get("images");
                    JSONArray jsonArray = object.getJSONArray("backdrop_sizes");
                    currentBackdropSize = jsonArray.get(0).toString();
                    currentBaseUrl = object.getString("base_url").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Error in volley response");
            }
        });
        queue.add(request);

        //to get file_path of image
        String url2 = "https://api.themoviedb.org/3/movie/" + id + "/images" + MoviesActivity.KEY;
        StringRequest request2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray jsonArray = jsonObject.getJSONArray("backdrops");
                    JSONObject object = (JSONObject) jsonArray.get(0);
                    currentFilePath = object.getString("file_path");
                    items.get(items.size()-1).setUrl(currentBaseUrl + currentBackdropSize + currentFilePath);
                    callBack.onSuccess(items.get(items.size()-1).getUrl());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Error in Volley response");
            }
        });
        queue.add(request2);
    }

    //we create a List of movie that has an id, an name and an url for the image
    private void initListMovies(String research, RequestQueue queue) throws InterruptedException {
        ResearchMovieAdapter rmAdapter = new ResearchMovieAdapter(getApplicationContext(), R.layout.research_item, items);
        fillAllIdName(research, new VolleyCallBack() {
            @Override
            public void onSuccess(String URL) {
                // this is where you will call the geofire, here you have the response from the volley.
                rmAdapter.add(items.get(items.size()-1), URL);
            }
        });

        listview.setAdapter(rmAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView< ? > adapter, View view, int position, long arg){
                    Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                    intent.putExtra("movie", items.get(position).getId());
                    intent.putExtra("list", "search");
                    intent.putExtra("precActivity", "search");
                    startActivity(intent);
                }
            }
        );
    }

    @Override
    public void onStart(){
        Log.d(TAG, "onStart: searchActivity");
        super.onStart();
    }
    @Override
    public void onResume(){
        Log.d(TAG, "onResume: searchActivity");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.d(TAG, "onPause: searchActivity");
        super.onPause();
    }
    @Override
    public  void onStop(){
        Log.d(TAG, "onStop: searchActivity");
        super.onStop();
    }
    @Override
    public void onRestart(){
        Log.d(TAG, "onRestart: searchActivity");
        super.onRestart();
    }
    @Override
    public void onDestroy(){
        Log.d(TAG, "onDestroy: searchActivity");
        super.onDestroy();
    }
}

interface VolleyCallBack {
    void onSuccess(String URL);
}