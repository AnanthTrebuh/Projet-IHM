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

import java.util.ArrayList;

public class FragmentDvdToBuy extends Fragment {
    public static String TAG = "CineTech";

    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dvd_to_buy, container, false);
        listview = view.findViewById(R.id.listView);
        // Inflate the layout for this fragment
        ArrayList<Dvd> dvd = new ArrayList<>();
        String[] acteurs = {"jean bon", "jean michel"};
        boolean b = false;
        for (int i = 0; i < 50; i++) {
            Dvd m1 = new Dvd(String.valueOf(i),"DvdToGet" + i, "M", b);
            dvd.add(m1);
            b = !b;
        }
        Log.d(TAG, "onCreateView: after creationg dvd list");
        CustomListAdapterDvd arrayAdapter = new CustomListAdapterDvd(getActivity(), dvd);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dvd current = (Dvd) listview.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("movie", current.getId());
                intent.putExtra("list", "toGet");
                startActivity(intent);
            }
        });
        return view;
    }
}