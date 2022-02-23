package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentMovieToSee extends Fragment {
    public static String TAG = "CineTech";
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_to_see, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment
        ArrayList<Movies> movies = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};
        for (int i = 0; i < 25; i++) {
            Movies m = new Movies(String.valueOf(i), "MovieToSee" + i, "M", "17/02/2022", acteurs, 90);
            movies.add(m);
        }
        CustomListAdapter arrayAdapter = new CustomListAdapter(getActivity(), movies);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " + position);
                /*
                Movies current = (Movies) listview.getItemAtPosition(position);
                movies.remove(current);
                CustomListAdapter arrayAdapter = new CustomListAdapter(getActivity(), movies);
                listview.setAdapter(arrayAdapter);
                */
                Movies current = (Movies) listview.getItemAtPosition(position);

                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("movie", current.getId());
                intent.putExtra("list", "toSeen");
                startActivity(intent);
            }
        });
        return view;
    }
}