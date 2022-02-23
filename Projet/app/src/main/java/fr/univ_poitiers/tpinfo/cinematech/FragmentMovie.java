package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentMovie extends Fragment {
    ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment
        ArrayList<Movies> movies = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};

            Movies m1 = new Movies(String.valueOf(634649), "test" , "Michel", "17/02/2022", acteurs, 90);
            movies.add(m1);

        CustomListAdapter arrayAdapter = new CustomListAdapter(listview.getContext(), movies);
        listview.setAdapter(arrayAdapter);

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
}