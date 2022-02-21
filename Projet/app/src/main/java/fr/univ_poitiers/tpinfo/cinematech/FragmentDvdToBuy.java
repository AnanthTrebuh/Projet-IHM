package fr.univ_poitiers.tpinfo.cinematech;

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

import java.util.ArrayList;


public class FragmentDvdToBuy extends Fragment {
    public static String TAG = "CineTech";

    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_to_come, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<Dvd> dvd = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};
        boolean b = false;
        for (int i = 0; i < 50; i++) {
            Dvd m1 = new Dvd("DvdToGet" + i, "M", "17/02/2022", acteurs, 90, b);
            dvd.add(m1);
            b = !b;
        }
        ArrayAdapter<Dvd> arrayAdapter = new ArrayAdapter<Dvd>(listview.getContext(), android.R.layout.simple_list_item_checked, dvd);
        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " + position);
                CheckedTextView v = (CheckedTextView) view;
                Dvd current = (Dvd) listview.getItemAtPosition(position);
                dvd.remove(current);
                ArrayAdapter<Dvd> arrayAdapter = new ArrayAdapter<Dvd>(listview.getContext(), android.R.layout.simple_list_item_checked, dvd);
                listview.setAdapter(arrayAdapter);
            }
        });
        return view;
    }
}