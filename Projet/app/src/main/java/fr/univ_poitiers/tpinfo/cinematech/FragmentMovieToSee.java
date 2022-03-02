package fr.univ_poitiers.tpinfo.cinematech;

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
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FragmentMovieToSee extends Fragment {
    public static String TAG = "CineTech";
    ListView listview;
    private RequestQueue queue;
    FillListView fillListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_movie_to_see, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(this.getContext());
        fillListView = new FillListView(queue,listview, this.getContext(), Utils.MOVIE);

        ArrayList<Movies> movies = new ArrayList<>();
        CustomListAdapter arrayAdapter = new CustomListAdapter(getActivity(), movies);
        listview.setAdapter(arrayAdapter);
        this.initList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " + position);
                Movies current = (Movies) listview.getItemAtPosition(position);

                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("movie", current.getId());
                intent.putExtra("list", Utils.MOVIE);
                intent.putExtra("precActivity", Utils.MOVIE);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
    public void initList() {
        fillListView.fillList();
    }

}