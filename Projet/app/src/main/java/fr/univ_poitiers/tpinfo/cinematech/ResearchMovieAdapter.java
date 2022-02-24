package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

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
        ImageView movieImage = convertView.findViewById(R.id.researchImageView);
        TextView textImage = convertView.findViewById(R.id.researchTextView);

        LoadImage loadImage = new LoadImage(movieImage);
        loadImage.execute(items.get(index).getUrl());
        textImage.setText(items.get(index).getName());
        return convertView;
    }
}
