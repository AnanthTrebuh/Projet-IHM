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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class FragmentDvdToBuy extends Fragment {
    public static String TAG = "CineTech";

    ListView listview;
    private RequestQueue queue;
    FillListView fillListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dvd_to_buy, container, false);
        listview = view.findViewById(R.id.listView);
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(this.getContext());
        fillListView = new FillListView(queue,listview, this.getContext(), "_dvd");

        ArrayList<Dvd> dvd = new ArrayList<>();
        CustomListAdapterDvd arrayAdapter = new CustomListAdapterDvd(getActivity(), dvd);
        listview.setAdapter(arrayAdapter);
        this.initList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dvd current = (Dvd) listview.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("movie", current.getId());
                intent.putExtra("list", "toGet");
                intent.putExtra("precActivity", "dvd");
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