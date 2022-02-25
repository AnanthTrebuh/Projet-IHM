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


public class FragmentDvd extends Fragment {
    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dvd, container, false);
        listview = view.findViewById(R.id.listView);
        // Inflate the layout for this fragment
        ArrayList<Dvd> dvd = new ArrayList<>();
        boolean b = false;
        for (int i = 0; i < 50; i++) {
            Dvd m1 = new Dvd(String.valueOf(i),"DvdToCome" + i, "m", b);
            dvd.add(m1);
            b = !b;
        }
        CustomListAdapterDvd arrayAdapter = new CustomListAdapterDvd(getActivity(), dvd);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dvd current = (Dvd) listview.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("movie", current.getId());
                intent.putExtra("list", "get");
                startActivity(intent);
            }
        });

        return view;
    }
}