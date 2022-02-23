package fr.univ_poitiers.tpinfo.cinematech;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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
    ArrayList<JsonListMovie> items;
    int cpt = 0;

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
        Log.d(TAG, "onCreate: precActivity : " + precActivity);

        buttonSearch.setEnabled(false);
        /*
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if( hasFocus ){
                    buttonAccount.setVisibility(View.INVISIBLE);
                    buttonDvd.setVisibility(View.INVISIBLE);
                    buttonMovie.setVisibility(View.INVISIBLE);
                    buttonSearch.setVisibility(View.INVISIBLE);
                }
                else{
                    if (buttonMovie.getVisibility() == View.INVISIBLE){
                        buttonAccount.setVisibility(View.VISIBLE);
                        buttonMovie.setVisibility(View.VISIBLE);
                        buttonDvd.setVisibility(View.VISIBLE);
                        buttonSearch.setVisibility(View.VISIBLE);
                    }
                }
            }
        } );
        */
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                action_research();
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
        finish();
        startActivity(intent);
    }

    private void back_movie(){
        Intent intent = new Intent(this, MoviesActivity.class);
        finish();
        startActivity(intent);
    }
    private void back_account(){
        Intent intent = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(intent);
    }
    private void action_movies_button(){
        Log.d(TAG, "action_dvd_button: ");
        Intent intent = new Intent(this, MoviesActivity.class);
        intent.putExtra("precActivity", "search");
        finish();
        startActivity(intent);
    }

    private void action_dvd_button() {
        Log.d(TAG, "action_search_button: ");
        Intent intent = new Intent(this, DVDActivity.class);
        intent.putExtra("precActivity", "search");
        finish();
        startActivity(intent);
    }

    private  void action_account_button(){
        Log.d(TAG, "action_account_button: ");
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("precActivity", "search");
        finish();
        startActivity(intent);
    }

    private void action_research(){
        String s = this.searchBar.getQuery().toString();
        s = s.replaceAll("\\s+","+");
        Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        toast.show();
        initListMovies(s, queue);
    }

    private void fillAllIdName(String research, RequestQueue queue){
        String url = MoviesActivity.URL_TITLE_MOVIE + MoviesActivity.KEY + "&query=" + research + "&page=100";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    JSONArray jsonArray = object.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object1 = (JSONObject) jsonArray.get(i);
                        items.add(new JsonListMovie(object1.getString("title"), object1.getString("id"), null,null,null));
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

    private void fillAllUrl(RequestQueue queue){
        for(int i = 0; i < items.size(); i++){
            String id = items.get(i).getId();

            //base_url and file_size are in /configuration
            String url = "https://api.themoviedb.org/3/configuration" + MoviesActivity.KEY;
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String string) {
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        JSONObject object = (JSONObject) jsonObject.get("images");
                        JSONArray jsonArray = object.getJSONArray("backdrop_sizes");
                        items.get(cpt).changeBaseUrl(object.getString("base_url").toString());
                        items.get(cpt).changeBackdropSize(jsonArray.get(0).toString());
                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: ");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("HA", "YA ERREUR CHEF ");
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
                        items.get(cpt).changeFullPath(object.getString("file_path"));
                    } catch (JSONException e) {
                        Log.d(TAG, "onResponse: ");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("HA", "YA ERREUR CHEF ");
                }
            });
            queue.add(request2);
            cpt++;
        }
    }
    
    //we create a List of movie that has an id, an name and an url for the image
    private void initListMovies(String research, RequestQueue queue){
        fillAllIdName(research, queue);
        fillAllUrl(queue);
        ArrayAdapter<JsonListMovie> itemsAdaptater = new ArrayAdapter<JsonListMovie>(this, android.R.layout.simple_list_item_1, items);
        listview.setAdapter(itemsAdaptater);
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