package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

public class Utils {

    static String title, director;

    public static String getTitle(String id, RequestQueue queue){
        String url = MoviesActivity.URL_ID_MOVIE + id + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    title = (object.getString("title").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(MoviesActivity.TAG, "Error in request");

            }
        });
        queue.add(request);
        return title;
    }
    public static String getDirector(String id, RequestQueue queue){
        String url = "https://api.themoviedb.org/3/movie/" + id + "/credits" + MoviesActivity.KEY;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject object = new JSONObject(string);
                    JSONArray jsonArray = object.getJSONArray("crew"); //get data from all crew in this movie
                    //Loop on each crew to find the job: director, writter
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject cpy = (JSONObject) jsonArray.get(i);
                        if(cpy.get("job").toString().toLowerCase(Locale.ROOT).equals("director")){
                            director = (cpy.get("name").toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(MoviesActivity.TAG, e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(MoviesActivity.TAG, "Error in request");
            }
        });
        queue.add(request);
        return director;
    }
}
