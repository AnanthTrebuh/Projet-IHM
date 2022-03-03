package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;


public class FragmentMovie extends Fragment {
    private ListView listview;
    private RequestQueue queue;
    FillListView fillListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        listview = view.findViewById(R.id.listviewMovie);
        // Inflate the layout for this fragment
        queue = Volley.newRequestQueue(getContext());

        CustomListAdapter Adapter = new CustomListAdapter(listview.getContext(), new ArrayList<>());
        listview.setAdapter(Adapter);

        fillListView = new FillListView(queue,listview, this.getContext(), Utils.MOVIE_SEEN);
        fillListView.fillList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movies current = (Movies) listview.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("movie", current.getId());
                intent.putExtra("list", Utils.MOVIE_SEEN);
                intent.putExtra("precActivity", Utils.ACT_MOVIE);
                startActivity(intent);
            }
        });

        return view;
    }

    public void initList() {
        fillListView.fillList();
    }


}