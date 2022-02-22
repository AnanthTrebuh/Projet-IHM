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
import java.util.Date;

public class JsonMovie {
    JSONObject jsonObject;
    JSONArray jsonArray;
    private String id;
    private String TAG = "CineTech";

    JsonMovie(String data, RequestQueue queue, boolean Title)
    {
        if(Title)
            findJsonByTitle(data, queue);
        else
            findJsonById(data, queue);
        Log.d(TAG, "JsonMovie: " + MainActivity.KEY);
    }

    private static void findJsonByTitle(String nameMovie, RequestQueue queue){
        String url = MainActivity.URL_TITLE_MOVIE + MainActivity.KEY + "&query=" + nameMovie;
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
                Log.d("HA", "YA ERREUR CHEF ");
            }
        });
        queue.add(request);
    }

    private void findJsonById(String idMovie, RequestQueue queue){
        String url = MainActivity.URL_ID_MOVIE + idMovie + MainActivity.KEY;
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
                Log.d("HA", "YA ERREUR CHEF ");
            }
        });
        queue.add(request);
    }

    private void fillDataMovie(JSONObject object)
    {
        this.jsonObject = object;
        // TODO
        //A VOIR avec nathan ou je stocke les données....

            /*
            JSONArray fruitsArray = object.getJSONArray("genres");

            Integer id = fruitsArray.getInt(0);
            String name = fruitsArray.getString(1);

            String bg = object.getString("budget");
            Log.d("HA", "BUDGET: " + bg);
            Log.d("HA", "ID: " + id);
            Log.d("HA", "NAME: " + name);*/

    }

    private String getImagFromMovie() throws JSONException {
        //TODO, voir movie/movie_id/IMAGES
        return jsonObject.getString("title");
    }
    this.title = title;
        this.realisateur = real;
        this.date = date;
        this.acteurs = acteurs;
        this.duree = duree;

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

    private String getRealisator() throws JSONException {
        //TODO
        // movie/movie_id/credits
        //filter by job where job = Director ? Casting ? Producer ?
        // UL + movieID + /credits + KEY
        // JsonArray on crew to get all of them
        // if json.getString("job") = ????
        // https://api.themoviedb.org/3/movie/634649/credits?api_key=38cf06282c993b63af90dd9de695152f
        return jsonObject.getString("job");
    }
}
