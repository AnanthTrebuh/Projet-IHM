package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Movies> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  ArrayList<Movies> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {

        return listData.size();
    }

    @Override
    public Object getItem(int i) {

        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.afficheView = (ImageView) convertView.findViewById(R.id.imageView_affiche);
            holder.titleView = (TextView) convertView.findViewById(R.id.textView_title);
            holder.directorView = (TextView) convertView.findViewById(R.id.textView_director);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String realisateur;

        Movies movie = this.listData.get(position);
        holder.titleView.setText(movie.getTitle());
        realisateur =  context.getString(R.string.director) +" : " + movie.getRealisateur();
        LoadImage loadImage = new LoadImage(holder.afficheView);
        loadImage.execute(movie.getUrl());

        holder.directorView.setText(realisateur);

        return convertView;
    }
    static class ViewHolder {
        ImageView afficheView;
        TextView titleView;
        TextView directorView;
    }

    public ArrayList<Movies> getListData(){
        return listData;
    }
}
