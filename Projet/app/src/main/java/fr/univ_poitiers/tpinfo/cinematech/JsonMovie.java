package fr.univ_poitiers.tpinfo.cinematech;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JsonMovie {
    JSONObject jsonObject;
    JSONArray jsonArray;
    String id, director, writer, base_url, backdrop_size, file_path;
    String overview, runtime, releaseDate, title;
    ArrayList<String> characters;
    private String TAG = "CineTech";
    ImageView movieImage;

    JsonMovie(String data, RequestQueue queue, boolean Title)
    {
        if(Title)
            findJsonByTitle(data, queue);
        else
            findJsonById(data, queue);
        init(queue);
    }

    private void findJsonByTitle(String nameMovie, RequestQueue queue){
        String url = MoviesActivity.URL_TITLE_MOVIE + MoviesActivity.KEY + "&query=" + nameMovie;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    id = object.getString("id").toString();
                    if(object.length() > 0)
                        findJsonById(object.getString("id"), queue);
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

    private void findJsonById(String idMovie, RequestQueue queue){
        String url = MoviesActivity.URL_ID_MOVIE + idMovie + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    overview = object.getString("overview").toString();
                    runtime = object.getString("runtime").toString();
                    releaseDate = object.getString("release_date").toString();
                    title = object.getString("title").toString();
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

    //Fill data for director and characters
    private void init(RequestQueue queue)
    {
        String url = "https://api.themoviedb.org/3/movie/" + this.id + "/credits" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    JSONArray jsonArray = object.getJSONArray("crew"); //get data from all crew in this movie
                    //Loop on each crew to find the job: director, writter and fill characters
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject cpy = (JSONObject) jsonArray.get(i);
                        if(cpy.get("job").toString().toLowerCase(Locale.ROOT).equals("director")){
                            director = cpy.get("name").toString();
                        }
                        if(cpy.get("job").toString().toLowerCase(Locale.ROOT).equals("writer")){
                            writer = cpy.get("name").toString();
                        }
                        if(cpy.get("character") != null){
                            characters.add(cpy.get("name").toString());
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

    //get an image from the API: get the base_url, backdrop_size and file_path
    private void getUrl(RequestQueue queue) throws JSONException {
        //base_url and file_size are in /configuration
        String url = "https://api.themoviedb.org/3/configuration" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject object = (JSONObject) jsonObject.get("images");
                    JSONArray jsonArray = object.getJSONArray("backdrop_sizes");
                    base_url = object.getString("base_url").toString();
                    backdrop_size = jsonArray.get(0).toString();
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
                    file_path = object.getString("file_path");
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
    }

    private void changeImage(ImageView img) throws JSONException {
        LoadImage loadImage = new LoadImage(img);
        loadImage.execute(base_url+backdrop_size+file_path);
    }

    public String getTitle() throws JSONException {
        return this.title;
    }

    public String getReleaseDate() throws JSONException {
        return this.releaseDate;
    }

    public String getRunTime() throws JSONException {
        return this.runtime;
    }

    public String getOverview() throws JSONException {
        return overview;
    }

    public boolean upcoming() throws JSONException, ParseException {
        Date currentDate = new Date(System.currentTimeMillis());
        Date movieDate = new SimpleDateFormat().parse(jsonObject.getString("release_date"));
        return (currentDate.getTime() - movieDate.getTime()) > 0;
    }

    //Director
    public String getRealisator() throws JSONException {
        return this.director;
    }

    //scriptWriter
    public String getWriter(){
        return this.writer;
    }

    //Characters
    public ArrayList<String> getCharacters(){
        return this.characters;
    }
}
