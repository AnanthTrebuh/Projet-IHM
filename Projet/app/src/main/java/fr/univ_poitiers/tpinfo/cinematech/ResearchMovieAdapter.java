package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//Adapter to fill the listView after a research is made by a user, it is here that we have access on
//The imageview and TextView
public class ResearchMovieAdapter extends ArrayAdapter<ResearchMovie> {
    private ArrayList<ResearchMovie> items;

    public ResearchMovieAdapter(@NonNull Context context, int ressource, ArrayList<ResearchMovie> items){
        super(context, ressource);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        int index = position;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.research_item, parent, false);
        }
        //find ImageView and TextView of movie
        ImageView movieImage = convertView.findViewById(R.id.researchImageView);
        TextView textImage = convertView.findViewById(R.id.researchTextView);

        //Load Image from url
        Log.d("RATE", "index = " + index + " url: " + items.get(index).getUrl());
        LoadImage loadImage = new LoadImage(movieImage);
        loadImage.execute(items.get(index).getUrl());

        //set text with the appropriate title
        textImage.setText(items.get(index).getName());
        textImage.setTextSize(18);
        return convertView;
    }
}
