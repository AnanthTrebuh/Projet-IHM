package fr.univ_poitiers.tpinfo.cinematech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapterDvd extends BaseAdapter {
    private ArrayList<Dvd> listDataDvd;
    private LayoutInflater layoutInflater;
    private Context context;


    public CustomListAdapterDvd(Context aContext, ArrayList<Dvd> listData) {
        this.context = aContext;
        this.listDataDvd = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listDataDvd.size();
    }

    @Override
    public Object getItem(int i) {
        return listDataDvd.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_dvd_layout, null);
            holder = new ViewHolder();
            holder.afficheView = (ImageView) convertView.findViewById(R.id.imageView_affiche);
            holder.titleView = (TextView) convertView.findViewById(R.id.textView_title);
            holder.directorView = (TextView) convertView.findViewById(R.id.textView_director);
            holder.imageViewBluray = (ImageView) convertView.findViewById(R.id.imageViewBluray);
            //holder.checkBoxView = convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Dvd dvd = this.listDataDvd.get(position);
        holder.titleView.setText(dvd.getTitle());
        String realisateur = context.getString(R.string.director) +" : " + dvd.getRealisateur();
        holder.directorView.setText(realisateur);
        if(dvd.isBluray()){
            holder.imageViewBluray.setImageDrawable(context.getResources().getDrawable(R.drawable.blu_ray_icon));
        }else{
            holder.imageViewBluray.setImageDrawable(context.getResources().getDrawable(R.drawable.dvd_icon));
        }
        LoadImage loadImage = new LoadImage(holder.afficheView);
        loadImage.execute(dvd.getUrl());

        return convertView;
    }
    static class ViewHolder {
        ImageView afficheView;
        TextView titleView;
        TextView directorView;
        ImageView imageViewBluray;
    }
}
