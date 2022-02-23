package fr.univ_poitiers.tpinfo.cinematech;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JsonMovie {
    JSONObject jsonObject;
    JSONArray jsonArray;
    String id, director;
    ArrayList<String> characters;
    private String TAG = "CineTech";

    JsonMovie(String data, RequestQueue queue, boolean Title)
    {
        if(Title)
            findJsonByTitle(data, queue);
        else
            findJsonById(data, queue);
        init();
        Log.d(TAG, "JsonMovie: " + MoviesActivity.KEY);
    }

    private void findJsonByTitle(String nameMovie, RequestQueue queue){
        String url = MoviesActivity.URL_TITLE_MOVIE + MoviesActivity.KEY + "&query=" + nameMovie;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
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
                    if(object.length() > 0)
                        fillDataMovie(object);
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

    //Fill data for
    private void init(RequestQueue queue)
    {
        String url = "https://api.themoviedb.org/3/movie/" + this.id + "/credits" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    JSONArray jsonArray = object.getJSONArray("crew"); //get data from all crew in this movie
                    //Loop on each crew to find the job: director
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject cpy = (JSONObject) jsonArray.get(i);
                        if(cpy.get("job").toString().toLowerCase(Locale.ROOT).equals("director")){
                            director = cpy.get("name").toString();
                            break;
                        }
                    }

                    //Loop to fill the characters
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject cpy = (JSONObject) jsonArray.get(i);
                        characters.add(cpy.getString("character").toString());
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

    private void fillDataMovie(JSONObject object) {
        this.jsonObject = object;
    }

    private String getImageFromMovie() throws JSONException {
        //TODO, voir movie/movie_id/IMAGES
        return jsonObject.getString("title");
    }

    private String getTitle() throws JSONException {
        return jsonObject.getString("title");
    }

    private String getReleaseDate() throws JSONException {
        return jsonObject.getString("release_date");
    }

    private String getRunTime() throws JSONException {
        return jsonObject.getString("runtime");
    }

    private String getOverview() throws JSONException {
        return jsonObject.getString("overview");
    }

    private boolean upcoming() throws JSONException, ParseException {
        Date currentDate = new Date(System.currentTimeMillis());
        Date movieDate = new SimpleDateFormat().parse(jsonObject.getString("release_date"));
        return (currentDate.getTime() - movieDate.getTime()) > 0;
    }

    //Director
    private String getRealisator(RequestQueue queue) throws JSONException {
        return this.director;
    }

    //Characters
    private ArrayList<String> getCharacters(Request queue){
        return this.characters;
    }
}
