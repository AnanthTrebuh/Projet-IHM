package fr.univ_poitiers.tpinfo.cinematech;

import static fr.univ_poitiers.tpinfo.cinematech.Utils.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class FragmentMovie extends Fragment {
    ListView listview;
    RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(getContext());

        CustomListAdapter Adapter = new CustomListAdapter(listview.getContext(), initList());
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

    public ArrayList<Movies> initList(){
        ArrayList<Movies> movies = new ArrayList<>();

        Movies m1 = new Movies(String.valueOf(634649), getTitle("634649", queue) , getDirector("634649", queue));
        movies.add(m1);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("CinemaTech", Context.MODE_PRIVATE );
        SharedPreferences.Editor e = sharedPreferences.edit();
        String name = sharedPreferences.getString("Active_Profile","default");
        Set<String> movieList = new HashSet<>(sharedPreferences.getStringSet(name+"_movie_seen", new HashSet<String>()));
        String[] listMovie = movieList.toArray(new String[0]);
        String id;
        for(int i = 0; i < listMovie.length; i++){
            id = listMovie[i];
            Movies m = new Movies(id, getTitle(id, queue), getDirector(id, queue));
            movies.add(m);
        }
        return movies;
    }


}