package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class FragmentDvd extends Fragment {
    ListView listview;
    private RequestQueue queue;
    FillListView fillListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dvd, container, false);
        listview = view.findViewById(R.id.listView);
        // Inflate the layout for this fragment

        CustomListAdapterDvd arrayAdapter = new CustomListAdapterDvd(getActivity(), new ArrayList<Dvd>());
        listview.setAdapter(arrayAdapter);
        queue = Volley.newRequestQueue(getContext());
        fillListView = new FillListView(queue,listview, this.getContext(), "_dvd_buy");

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
    public void initList() {
        fillListView.fillList();
    }
}