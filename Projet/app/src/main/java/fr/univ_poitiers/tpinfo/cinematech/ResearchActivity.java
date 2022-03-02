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
import java.util.Locale;

public class ResearchActivity extends AppCompatActivity {
    RequestQueue queue;
    Button buttonMovie;
    Button buttonDvd;
    Button buttonAccount;
    Button buttonSearch;
    SearchView searchBar;
    ListView listview;
    String precActivity;
    ArrayList<ResearchMovie> items = new ArrayList<>();
    ResearchMovieAdapter rmAdapter;
    String backdropSize, baseUrl;
    int cpt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        queue = Volley.newRequestQueue(this);
        searchBar = findViewById(R.id.searchView);
        listview = findViewById(R.id.listView);
        rmAdapter = new ResearchMovieAdapter(getApplicationContext(), R.layout.research_item, items);

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
        Log.d(MoviesActivity.TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("precActivity", "search");
        startActivity(intent);
        finish();
    }

    private void action_dvd_button() {
        Log.d(MoviesActivity.TAG, "action_search_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        intent.putExtra("precActivity", "search");
        startActivity(intent);
        finish();
    }

    private  void action_account_button(){
        Log.d(MoviesActivity.TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("precActivity", "search");
        startActivity(intent);
        finish();
    }

    private void action_research() throws InterruptedException {
        items.clear();
        rmAdapter.clear();
        cpt = 0;
        String s = this.searchBar.getQuery().toString();
        s = s.replaceAll("\\s+","+");
        fillAllIdName(s);
    }

    private void fillAllIdName(String research){
        String url = MoviesActivity.URL_TITLE_MOVIE + MoviesActivity.KEY + "&query=" + research + "&page=" + 1;
        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                JSONObject object = new JSONObject(string);
                JSONArray jsonArray = object.getJSONArray("results");
                if(jsonArray.length() > 0){
                    for(int j = 0; j < jsonArray.length(); j++){ //search through 20 first resulsts
                        JSONObject object1 = (JSONObject) jsonArray.get(j);
                        items.add(new ResearchMovie(object1.getString("title"), object1.getString("id")));
                    }
                    fillAllUrl();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);
    }

    private void fillAllUrl(){
        //base_url and file_size are in /configuration
        String url = "https://api.themoviedb.org/3/configuration" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, string -> {
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONObject object = (JSONObject) jsonObject.get("images");
                JSONArray jsonArray = object.getJSONArray("backdrop_sizes");
                backdropSize = jsonArray.get(0).toString();
                baseUrl = object.getString("base_url").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
        queue.add(request);

        for(int i = 0; i < items.size(); i++){
            //to get file_path of image
            String url2 = "https://api.themoviedb.org/3/movie/" + items.get(i).getId() + "/images" + MoviesActivity.KEY;
            StringRequest request2 = new StringRequest(Request.Method.GET, url2, string -> {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray jsonArray = jsonObject.getJSONArray("backdrops");
                    JSONObject object = (JSONObject) jsonArray.get(0);
                    String currentFilePath = object.getString("file_path");
                    items.get(cpt).setUrl(baseUrl + backdropSize + currentFilePath);
                    rmAdapter.add(items.get(cpt));
                    cpt++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, volleyError -> Log.d(MoviesActivity.TAG, "Error in request"));
            queue.add(request2);
        }
        Log.d(MoviesActivity.TAG, "fillAllUrl: TESTESTTEST");
        initListMovies();
    }

    //we create a List of movie that has an id, an name and an url for the image
    private void initListMovies(){
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
        Log.d(MoviesActivity.TAG, "onStart: searchActivity");
        super.onStart();
    }
    @Override
    public void onResume(){
        Log.d(MoviesActivity.TAG, "onResume: searchActivity");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.d(MoviesActivity.TAG, "onPause: searchActivity");
        super.onPause();
    }
    @Override
    public  void onStop(){
        Log.d(MoviesActivity.TAG, "onStop: searchActivity");
        super.onStop();
    }
    @Override
    public void onRestart(){
        Log.d(MoviesActivity.TAG, "onRestart: searchActivity");
        super.onRestart();
    }
    @Override
    public void onDestroy(){
        Log.d(MoviesActivity.TAG, "onDestroy: searchActivity");
        super.onDestroy();
    }
}