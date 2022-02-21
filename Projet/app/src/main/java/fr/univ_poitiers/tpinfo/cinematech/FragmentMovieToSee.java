package fr.univ_poitiers.tpinfo.cinematech;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentMovieToSee extends Fragment {
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_to_come, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment
        ArrayList<Movies> movies = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};
        for (int i = 0; i < 50; i++) {
            Movies m1 = new Movies("testToSee" + i, "M", "17/02/2022", acteurs, 90);
            movies.add(m1);
        }
        ArrayAdapter<Movies> arrayAdapter = new ArrayAdapter<Movies>(listview.getContext(), android.R.layout.simple_list_item_1, movies);
        listview.setAdapter(arrayAdapter);

        return view;
    }
}